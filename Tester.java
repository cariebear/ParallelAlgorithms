import static org.junit.Assert.*;

import org.junit.Test;

public class Tester {
    //-------------------------------------------------------------//
    //- Prefix Sum ------------------------------------------------//
    //-------------------------------------------------------------//

    // Array of values
    @Test
    public void testPreFix1() {
        PreFix l = new PreFix();
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int[] exp = new int[]{0, 1, 3, 6, 10, 15, 21, 28};
        int[] G = PreFix.main(arr);
        assertArrayEquals(exp, G);
    }

    // Single value array
    @Test
    public void testPreFix2() {
        PreFix l = new PreFix();
        int[] arr = new int[]{1};
        int[] exp = new int[]{0};
        int[] G = PreFix.main(arr);
        assertArrayEquals(exp, G);
    }

    // Array containing negative values
    @Test
    public void testPreFix3() {
        PreFix l = new PreFix();
        int[] arr = new int[]{1, 3, 5, 6, 7, 8, -1, 22};
        int[] exp = new int[]{0, 1, 4, 9, 15, 22, 30, 29};
        int[] G = PreFix.main(arr);
        assertArrayEquals(exp, G);
    }

    // Empty array throws exception
    @Test(expected = RuntimeException.class)
    public void testPreFix4() {
        PreFix l = new PreFix();
        int[] arr = new int[]{};
        int[] exp = new int[]{};
        int[] G = PreFix.main(arr);
        assertArrayEquals(exp, G);
    }

    //-------------------------------------------------------------//
    //- Bellman Ford ----------------------------------------------//
    //-------------------------------------------------------------//

    // acyclic directional graph
    @Test
    public void testBellFord1() {
        BellmanFord l = new BellmanFord();
        int[][] arr4 = {
                {0, 1, 2, 0},
                {0, 0, 0, 3},
                {0, 0, 0, 4},
                {0, 0, 0, 0}
        };
        int[] exp = new int[]{0, 1, 2, 4};
        int[] G = BellmanFord.main(arr4);
        assertArrayEquals(exp, G);
    }

    // acyclic directional graph with negative weights
    @Test
    public void testBellFord2() {
        BellmanFord l = new BellmanFord();
        int[][] arr4 = {
                {0, 1, 2, 0},
                {0, 0, 0, -3},
                {0, 0, 0, -4},
                {0, 0, 0, 0}
        };
        int[] exp = new int[]{0, 1, 2, -2};
        int[] G = BellmanFord.main(arr4);
        assertArrayEquals(exp, G);
    }

    // Cyclical graph
    @Test
    public void testBellFord3() {
        BellmanFord l = new BellmanFord();
        int[][] arr4 = {
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1}
        };
        int[] exp = new int[]{0, 1, 1, 1};
        int[] G = BellmanFord.main(arr4);
        assertArrayEquals(exp, G);
    }

    // Negative cyclical graph
    @Test(expected = RuntimeException.class)
    public void testBellFord4() {
        BellmanFord l = new BellmanFord();
        int[][] arr4 = {
                {-1, -1, -1, -1},
                {-1, -1, -1, -1},
                {-1, -1, -1, -1},
                {-1, -1, -1, -1}
        };
        int[] exp = new int[]{};
        int[] G = BellmanFord.main(arr4);
        assertArrayEquals(exp, G);
    }

    //-------------------------------------------------------------//
    //- Optimal BST -----------------------------------------------//
    //-------------------------------------------------------------//

    // Array of positive numbers
    @Test
    public void testObst1() {
        OptimalBST l = new OptimalBST();
        int[] arr = {25, 20, 5, 20, 30};
        int exp = 210;
        int G = OptimalBST.main(arr);
        assertEquals(exp, G);
    }

    // Empty array is 0
    @Test
    public void testObst2() {
        OptimalBST l = new OptimalBST();
        int[] arr = {};
        int exp = 0;
        int G = OptimalBST.main(arr);
        assertEquals(exp, G);
    }

    // Array with negative value
    @Test
    public void testObst3() {
        OptimalBST l = new OptimalBST();
        int[] arr = {25, 20, -5, 21, 30};
        int exp = 171;
        int G = OptimalBST.main(arr);
        assertEquals(exp, G);
    }

    //-------------------------------------------------------------//
    //- Prims -----------------------------------------------------//
    //-------------------------------------------------------------//

    //unidirectional graph
    @Test
    public void testPrim1() {
        Prim l = new Prim();
        int[][] arr = {
                {0, 6, 5, 7},
                {6, 0, 0, 4},
                {5, 0, 0, 8},
                {7, 4, 8, 0}
        };
        int[] exp = new int[]{5, 6, 5, 4};
        int[] G = Prim.main(arr);
        assertArrayEquals(exp, G);
    }

    // unidirectional graph with negative weight
    @Test
    public void testPrim2() {
        Prim l = new Prim();
        int[][] arr = {
                {0, 6, 5, 7},
                {6, 0, 0, 4},
                {5, 0, 0, -8},
                {7, 4, -8, 0}
        };
        int[] exp = new int[]{5, 6, 5, 4};
        int[] G = Prim.main(arr);
        assertArrayEquals(exp, G);
    }

    // empty array input
    @Test (expected = RuntimeException.class)
    public void testPrim3() {
        Prim l = new Prim();
        int[][] arr = {};
        int[] exp = new int[]{};
        int[] G = Prim.main(arr);
        assertArrayEquals(exp, G);
    }
}