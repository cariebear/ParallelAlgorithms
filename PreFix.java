import java.util.Arrays;

class SharedData {
    private int[] resultArray;
    private boolean[] ready;

    public SharedData(int[] resultArray) {
        this.resultArray = resultArray;
        Arrays.fill(resultArray, (int) Double.NEGATIVE_INFINITY);
        this.ready = new boolean[resultArray.length];
    }

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

class DownSweep implements Runnable {
    private int[] array1;
    private int[] array2;
    private int[] resultArray;
    private int index;
    private int endIndex;
    private boolean OddEven;

    private SharedData sharedData;

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
    public static void main(int[] args) {
        int[] array1 = args; // {1, 2, 3, 4, 5, 6, 7, 8};
        int[] array2 = ReduceSum.main(array1);  //{36, 10, 26, 3, 7, 11, 15};

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

        int[] finalArray = new int[arraySize];
        for (int i = arraySize; i < resultArray.length - 1; i++) {
            finalArray[i - arraySize + 1] = resultArray[i];
        }

//        System.out.println("Result Array:");
//        for (int value : resultArray) {
//            System.out.print(value + " ");
//        }
        System.out.println("Exclusive Prefix Sum Array:");
        for (int value : finalArray) {
            System.out.print(value + " ");
        }
        System.out.println("\n");
    }
}
