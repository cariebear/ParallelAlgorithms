import java.util.Arrays;

class SharedData {
    private int[] resultArray;

    public SharedData(int[] resultArray) {
        this.resultArray = resultArray;
        //Arrays.fill(resultArray, (int) Double.NEGATIVE_INFINITY);
    }

    public synchronized int read(int index) {
        return resultArray[index];
    }

    public synchronized void write(int index, int value) {
        resultArray[index] = value;
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

        if (index == 0){
            sharedData.write(index,0);
        }
        else if (OddEven){
            int value = sharedData.read(endIndex/2-1);
            sharedData.write(index,value);
        }
        else{
            if (index < array1.length){
                int value = array2[index-1] + sharedData.read((endIndex/2-1));
                sharedData.write(index,value);
            }
            else{
                int value = array1[index-array1.length] + sharedData.read((endIndex/2-1));
                sharedData.write(index,value);
            }

        }
    }
}

public class PreFix2{
    public static void main(String[] args) {


        int[] array1 = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] array2 = {36, 10, 26, 3, 7, 11, 15};

        int arraySize = array1.length; // Size of the arrays
        int numThreads = arraySize*2; // Number of threads
        int[] resultArray = new int[numThreads-1];
        //resultArray[0] = 0;

        Thread[] threads = new Thread[numThreads];

        SharedData sharedData = new SharedData(resultArray);

        for (int i = 0; i < numThreads; i++) {
            int index = i;
            int endIndex = i+1;
            boolean OddEven = (endIndex % 2 == 0); // Even threads search and store

            threads[i] = new Thread(new DownSweep(array1, array2, sharedData,index, endIndex, OddEven));
            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Result Array:");
        for (int value : resultArray) {
            System.out.print(value + " ");
        }
    }
}
