import java.util.*;
import java.util.concurrent.*;

public class Prim {
    public static void main(int[][] args) {
        int[][] graph = args;

//        {
//                {0, 3, 0, 0, 4},
//                {3, 0, 5, 9, 0},
//                {0, 5, 0, 2, 6},
//                {0, 9, 2, 0, 7},
//                {4, 0, 6, 7, 0}
//        };

        int numVertices = graph.length;
        int[] d = new int[numVertices];
        int[] G = new int[numVertices];
        boolean[] fixed = new boolean[numVertices];
        Set<Integer> Q = new HashSet<>();
        Set<Integer> R = new HashSet<>();
        Set<Edge> T = new HashSet<>();
        Set<Edge> MWE = new HashSet<>();
        BinaryHeap heap = new BinaryHeap();

        //initializing arrays
        for (int i = 0; i < numVertices; i++) {
            d[i] = Integer.MAX_VALUE;
            G[i] = -1;
            fixed[i] = false;
        }
        //going line by line from the parallel-prim algo from the book
        d[0] = 0;
        heap.insert(new Node(0, d[0]));


        while (!heap.isEmpty()) {
            Node node = heap.removeMin();
            int j = node.vertex;

            if (!fixed[j]) {
                R.add(j);
                fixed[j] = true;
                if (G[j] != -1) {
                    T.add(new Edge(j, G[j]));
                }
            }

            while (!R.isEmpty()) {
                int z = R.iterator().next();
                R.remove(z);

                Set<Integer> nonFixedNeighbors = new HashSet<>();
                for (int k = 0; k < numVertices; k++) {
                    if (k != z && !fixed[k] && graph[z][k] > 0 ) {
                        nonFixedNeighbors.add(k);
                    }
                }

                nonFixedNeighbors.parallelStream().forEach(k -> processEdge1(z, k, G, T, MWE, d, Q, R, graph, fixed));
            }

            for (int z : Q) {
                if (!fixed[z]) {
                    heap.insert(new Node(z, d[z]));
                }
            }
            Q.clear();
        }

        if (T.size() == numVertices - 1) {
            System.out.println("Minimum Spanning Tree Edges: " + T);
            for (int value : d) {
                System.out.print(value + " ");
            }
        } else {
            System.out.println("No spanning tree exists.");
        }
    }

    private static void processEdge1(int z, int k, int[] G, Set<Edge> T, Set<Edge> MWE, int[] d, Set<Integer> Q,Set<Integer> R, int[][] graph, boolean fixed[]) {
        if (MWE.contains(new Edge(z, k))) {
            fixed[k] = true;
            T.add(new Edge(k, z));
            R.add(k);

        } else if (d[k] > graph[z][k]) {
            d[k] = graph[z][k];
            System.out.println(d[k]);
            G[k] = z;
            if(!Q.contains(k)){
                Q.add(k);
            }
        }
    }
}

class Edge {
    int from, to;

    public Edge(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return (from == edge.from && to == edge.to) || (from == edge.to && to == edge.from);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to) + Objects.hash(to, from);
    }

    @Override
    public String toString() {
        return "(" + from + ", " + to + ")";
    }
//    @Override
//    public int[][] toArray() {
//        return "(" + from + ", " + to + ")";
//    }

}

class Node {
    int vertex, dist;

    public Node(int vertex, int dist) {
        this.vertex = vertex;
        this.dist = dist;
    }
}

class BinaryHeap {
    private PriorityQueue<Node> heap;

    public BinaryHeap() {
        heap = new PriorityQueue<>(Comparator.comparingInt(node -> node.dist));
    }

    public void insert(Node node) {
        heap.offer(node);
    }

    public Node removeMin() {
        return heap.poll();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }
}
