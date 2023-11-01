import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class OptimalBST {
    static int optCost(int freq[], int i, int j) {
        if (j < i) {
            return 0;
        }
        if (j == i) {
            return freq[i];
        }

        int fsum = sum(freq, i, j);
        int min = Integer.MAX_VALUE;

        for (int k = i; k <= j; ++k) {
            int cost = optCost(freq, i, k - 1) + optCost(freq, k + 1, j);
            if (cost < min) {
                min = cost;
            }
        }

        return min + fsum;
    }

    static int optimalSearchTree(int keys[], int freq[], int n) {
        return optCost(freq, 0, n - 1);
    }

    static int sum(int freq[], int i, int j) {
        int s = 0;
        for (int k = i; k <= j; k++) {
            s += freq[k];
        }
        return s;
    }

    static class OptimalSearchTreeTask extends RecursiveTask<Integer> {
        int freq[];
        int i, j;

        OptimalSearchTreeTask(int freq[], int i, int j) {
            this.freq = freq;
            this.i = i;
            this.j = j;
        }

        @Override
        protected Integer compute() {
            if (j < i) {
                return 0;
            }
            if (j == i) {
                return freq[i];
            }

            int fsum = sum(freq, i, j);
            int min = Integer.MAX_VALUE;

            for (int k = i; k <= j; ++k) {
                OptimalSearchTreeTask leftTask = new OptimalSearchTreeTask(freq, i, k - 1);
                OptimalSearchTreeTask rightTask = new OptimalSearchTreeTask(freq, k + 1, j);

                leftTask.fork();
                int cost = rightTask.invoke() + leftTask.join();

                if (cost < min) {
                    min = cost;
                }
            }

            return min + fsum;
        }
    }

    public static int main(int[] args) {
        int keys[] = {10};
        int freq[] = args; //{25,20,5,20,30};
        int n = freq.length;

        ForkJoinPool pool = new ForkJoinPool();
        int result = pool.invoke(new OptimalSearchTreeTask(freq, 0, n - 1));

        System.out.println("Cost of Optimal BST is " + result);
        return result;
    }
}
