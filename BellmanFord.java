import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class BellmanFord {
    public static int[] main(int[][] args) {
        int[][] graph = args;
//        {
//                {0, 1, 2, 0},
//                {0, 0, 0, 3},
//                {0, 0, 0, 4},
//                {0, 0, 0, 0}
//        };
        int[] finalArray;
        finalArray = parallelLLPBellmanFord(graph);
        return finalArray;
    }

    static int[] parallelLLPBellmanFord(int[][] graph) {
        int n = graph.length;
        int[] d = new int[n];
        Arrays.fill(d, Integer.MAX_VALUE);
        d[0] = 0; // Source node

        ForkJoinPool pool = new ForkJoinPool();

        for (int k = 0; k < n; k++) {
            BellmanFordTask task = new BellmanFordTask(d, graph, n, k);
            pool.invoke(task);
        }

        // Output the minimum distance vector

        for (int i = 0; i < n; i++) {
            System.out.println("Shortest distance from source to node " + i + ": " + d[i]);
        }
        return d;
    }

    static class BellmanFordTask extends RecursiveTask<Void> {
        private final int[] d;
        private final int[][] graph;
        private final int n;
        private final int k;

        BellmanFordTask(int[] d, int[][] graph, int n, int k) {
            this.d = d;
            this.graph = graph;
            this.n = n;
            this.k = k;
        }

        @Override
        protected Void compute() {
            for (int i = 0; i < n; i++) {
                if (graph[k][i] != 0 && d[i] > d[k] + graph[k][i]) {
                    d[i] = d[k] + graph[k][i];
                }
            }
            return null;
        }
    }
}
