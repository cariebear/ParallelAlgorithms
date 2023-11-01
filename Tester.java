public class Tester {
    public static void main(String[] args)
    {
        int arr[] = {1, 2, 3, 4, 5, 6, 7, 8};
        int arr2[][] =  {
                {0,6,5,7},
                {6,0,0,4},
                {5,0,0,8},
                {7,4,8,0}
        };
        int arr3[] = {25,20,5,20,30};
        int arr4[][] = {
                {0, 1, 2, 0},
                {0, 0, 0, 3},
                {0, 0, 0, 4},
                {0, 0, 0, 0}
        };

        BellmanFord.main(arr4);
        OptimalBST.main(arr3);
        PreFix.main(arr);
        Prim.main(arr2);
    }
}