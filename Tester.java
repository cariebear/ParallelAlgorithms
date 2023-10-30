public class Tester {
    public static void main(String[] args)
    {
        int arr[] = {1, 2, 3, 4, 5, 6, 7, 8};
        int arr2[][] =  {
                {0, 3, 0, 0, 4},
                {3, 0, 5, 9, 0},
                {0, 5, 0, 2, 6},
                {0, 9, 2, 0, 7},
                {4, 0, 6, 7, 0}
        };
        int arr3[] = {25,20,5,20,30};

        BellmanFord.main(arr);
        OptimalBST.main(arr3);
        PreFix.main(arr);
        Prim.main(arr2);
    }
}