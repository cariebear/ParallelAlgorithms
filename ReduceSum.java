// def parallel_prefix_sum(A):
//     n = len(A)
//     # Up-Sweep Phase
//     for d in range(log2(n)):
//         stride = 2^(d+1)
//         for k in range(stride/2, n, stride):
//             A[k] = A[k] + A[k - stride/2]
    
//     A[n-1] = 0  # Set the last element to zero
    
//     # Down-Sweep Phase
//     for d in range(log2(n)-1, -1, -1):
//         stride = 2^(d+1)
//         for k in range(stride/2, n, stride):
//             temp = A[k]
//             A[k] = A[k] + A[k - stride/2]
//             A[k - stride/2] = temp
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.log;

public class ReduceSum
{
        public static int[] main(int[] args) {
            int[] data = args;
            ForkJoinPool forkJoinPool = new ForkJoinPool();

            int[] intermediateResults = new int[data.length];
            AtomicInteger index = new AtomicInteger(0);

            int sum = forkJoinPool.invoke(new SumTask(data, 0, data.length, intermediateResults, index));

            System.out.println("Sum: " + sum);
            int[] finalResults = new int[data.length-1];

            //System.out.println("Intermediate Results: " + java.util.Arrays.toString(intermediateResults));
            for(int i=1; i<intermediateResults.length; i++){
                finalResults[i-1] = intermediateResults[i];
                //System.out.println(intermediateResults[i]);
            }
            //System.out.println("Final Results: " + java.util.Arrays.toString(finalResults) + "/n");
            return finalResults;
        }

        static class SumTask extends RecursiveTask<Integer> {
            private static final int THRESHOLD = 2;
            private int[] data;
            private int start;
            private int end;
            private int[] intermediateResults;
            private AtomicInteger index;

            SumTask(int[] data, int start, int end, int[] intermediateResults, AtomicInteger index) {
                this.data = data;
                this.start = start;
                this.end = end;
                this.intermediateResults = intermediateResults;
                this.index = index;
            }

            @Override
            protected Integer compute() {
                int length = end - start;
                if (length <= THRESHOLD) {
                    int sum = 0;
                    for (int i = start; i < end; i++) {
                        sum += data[i];
                    }
                    //System.out.println(start/2+4);
                    intermediateResults[start/2+ data.length/2] = sum;
                    return sum;
                } else {
                    int mid = start + length / 2;
                    SumTask leftTask = new SumTask(data, start, mid, intermediateResults, index);
                    SumTask rightTask = new SumTask(data, mid, end, intermediateResults, index);

                    leftTask.fork();
                    int rightResult = rightTask.compute();
                    int leftResult = leftTask.join();

                    int sum = leftResult + rightResult;

                    int diff = data.length/2-1;
                    //System.out.println(treeH);
                    intermediateResults[diff-index.getAndIncrement()] = sum;
                    return sum;
                }
            }
        }

    }

