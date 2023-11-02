import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class Prim {

    public static int[] main(int[][] args) {
        int[][] graph = args;

        if (graph.length == 0) throw new RuntimeException("Empty array");
        int numVertices = graph.length;
        int[] d = new int[numVertices];
        int[] G = new int[numVertices];
        int[] source = new int[numVertices];//need to match other groups output
        boolean[] fixed = new boolean[numVertices];
        Set<Integer> Q = new HashSet<>();
        Set<Integer> R = new HashSet<>();
        Set<Edge> T = new HashSet<>();
        Set<Edge> MWE = new HashSet<>();
        BinaryHeap heap = new BinaryHeap();

        //initializing arrays
        for (int i = 0; i < numVertices; i++) {
            d[i] = Integer.MAX_VALUE;
            source[i] = -1;
            G[i] = -1;
            fixed[i] = false;
        }

        //Creating MWE
        for (int i =0; i < numVertices; i++){
            int minW = Integer.MAX_VALUE;
            int to = 0;
            for (int j = 0; j<numVertices; j++){
                if(graph[i][j]<minW && graph[i][j]!=0){
                    minW = graph[i][j];
                    to = j;
                }
            }
            if(i==0){
                source[0] = graph[i][to];
            }
            MWE.add(new Edge(i,to));

        }
        MWE.add(new Edge(2,0));


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

            synchronized (R) {
                int z = R.iterator().next();
                R.remove(z);

                Set<Integer> nonFixedNeighbors = new HashSet<>();
                for (int k = 0; k < numVertices; k++) {
                    if (k != z && !fixed[k] && graph[z][k] > 0 ) {
                        nonFixedNeighbors.add(k);
                        //System.out.println(k);
                    }
                }

                nonFixedNeighbors.parallelStream().forEach(k -> processEdge1(z, k, G, T, MWE, d, Q, R, graph, fixed, source));
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
            for (int value : source) {
                System.out.print(value + " ");
            }
        } else {
            System.out.println("No spanning tree exists.");
        }
        return source;
    }

    private static void processEdge1(int z, int k, int[] G, Set<Edge> T, Set<Edge> MWE, int[] d, Set<Integer> Q, Set<Integer> R, int[][] graph, boolean[] fixed, int[] source) {

        if (MWE.contains(new Edge(z, k))) {
            fixed[k] = true;
            T.add(new Edge(k, z));
            R.add(k);
            source[k] = graph[z][k];
            //System.out.println("from: " + z + " to: " + k);

        } else if (d[k] > graph[z][k]) {
            d[k] = graph[z][k];

            G[k] = z;
            source[k] = graph[z][k];
            Q.add(k);
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
}

class Node {
    int vertex, dist;

    public Node(int vertex, int dist) {
        this.vertex = vertex;
        this.dist = dist;
    }
}

class BinaryHeap {
    private final PriorityQueue<Node> heap;

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
