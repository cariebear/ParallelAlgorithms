import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ReduceSum
{
        public static int[] main(int[] args) {

            int[] data = args;
            ForkJoinPool forkJoinPool = new ForkJoinPool();

            int[] intermediateResults = new int[data.length];
            AtomicInteger index = new AtomicInteger(0);

            //Run Parallel Method
            int sum = forkJoinPool.invoke(new SumTask(data, 0, data.length, intermediateResults, index));

            System.out.println("Sum: " + sum);
            int[] finalResults = new int[data.length-1];

            System.arraycopy(intermediateResults, 1, finalResults, 0, intermediateResults.length - 1);
            return finalResults;
        }

        static class SumTask extends RecursiveTask<Integer> {
            private static final int THRESHOLD = 2;
            private final int[] data;
            private final int start;
            private final int end;
            private final int[] intermediateResults;
            private final AtomicInteger index;

            SumTask(int[] data, int start, int end, int[] intermediateResults, AtomicInteger index) {
                this.data = data;
                this.start = start;
                this.end = end;
                this.intermediateResults = intermediateResults;
                this.index = index;
            }

            @Override
            //LLP Predicate Comparator
            protected Integer compute() {
                int length = end - start;
                if (length <= THRESHOLD) {
                    int sum = 0;
                    for (int i = start; i < end; i++) {
                        sum += data[i];
                    }
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
                    intermediateResults[diff-index.getAndIncrement()] = sum;
                    return sum;
                }
            }
        }

    }

