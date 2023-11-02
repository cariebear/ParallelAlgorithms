import java.util.Arrays;

class SharedData {
    private final int[] resultArray;
    private final boolean[] ready;

    public SharedData(int[] resultArray) {
        this.resultArray = resultArray;
        Arrays.fill(resultArray, (int) Double.NEGATIVE_INFINITY);
        this.ready = new boolean[resultArray.length];
    }
//Synchronized added to wait for data to write to output array prior to read
    public synchronized int read(int index) {
        while (!ready[index]) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return resultArray[index];
    }

    public synchronized void write(int index, int value) {
        resultArray[index] = value;
        ready[index] = true;
        notifyAll();
    }
}

//Running threads that will be ID specific
class DownSweep implements Runnable {
    private final int[] array1;
    private final int[] array2;
    private final int index;
    private final int endIndex;
    private final boolean OddEven;

    private final SharedData sharedData;

    public DownSweep(int[] array1, int[] array2, SharedData sharedData, int index, int endIndex, boolean OddEven) {
        this.array1 = array1;
        this.array2 = array2;
        this.index = index;
        this.sharedData = sharedData;
        this.endIndex = endIndex;
        this.OddEven = OddEven;
    }

    @Override
    public void run() {
        synchronized (sharedData) {
            if (index == 0) {
                sharedData.write(index, 0);
            } else if (OddEven) {
                int value = sharedData.read(endIndex / 2 - 1);
                sharedData.write(index, value);
            } else {
                if (index < array1.length) {
                    int value = array2[index - 1] + sharedData.read(endIndex / 2 - 1);
                    sharedData.write(index, value);
                } else {
                    int value = array1[index - array1.length] + sharedData.read(endIndex / 2 - 1);
                    sharedData.write(index, value);
                }
            }
        }
    }
}

public class PreFix {
    public static int[] main(int[] args) {
        if (args.length == 0) throw new RuntimeException("Empty array");
        int[] array1 = args;
        int[] array2 = ReduceSum.main(array1);

        int arraySize = array1.length;
        int numThreads = arraySize * 2;
        int[] resultArray = new int[numThreads];

        Thread[] threads = new Thread[numThreads];

        SharedData sharedData = new SharedData(resultArray);

        for (int i = 0; i < numThreads; i++) {
            int index = i;
            int endIndex = i + 1;
            boolean OddEven = (endIndex % 2 == 0);

            threads[i] = new Thread(new DownSweep(array1, array2, sharedData, index, endIndex, OddEven));
            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Filter out everything but the solutions section of the result array
        int[] finalArray = new int[arraySize];
        if (resultArray.length - 1 - arraySize >= 0)
            System.arraycopy(resultArray, arraySize, finalArray, 0 + 1, resultArray.length - 1 - arraySize);

        System.out.println("Exclusive Prefix Sum Array:");
        for (int value : finalArray) {
            System.out.print(value + " ");
        }
        System.out.println("\n");
        return finalArray;
    }
}
