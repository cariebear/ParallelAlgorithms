// prim(graph):
//     mst = empty set
//     startVertex = first vertex in graph
//     mst.add(startVertex)
//     edges = edges connected to startVertex
//     while mst has fewer vertices than graph:
//         minEdge, minWeight = findMinEdge(edges)
//         mst.add(minEdge)

//         for edge in edges connected to minEdge:
//             if edge is not in mst:
//                 edges.add(edge)
//         edges.remove(minEdge)
//     return mst as an array

public class Prim
{
    public static void main(int[] arr)
    {
        System.out.println("Prim.main()");
    }
}