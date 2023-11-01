import static org.junit.Assert.*;
import java.util.TreeSet;
import java.util.Set;

import org.junit.Test;

public class Tester {
    @Test public void testPreFix1(){
        PreFix l = new PreFix();
        int arr[] = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int exp[] = new int[]{0, 1,3,6,10,15,21,28};
        int[] G = l.main(arr);
        assertArrayEquals(exp, G);
    }
    @Test public void testBellFord1(){
        BellmanFord l = new BellmanFord();
        int arr4[][] = {
                {0, 1, 2, 0},
                {0, 0, 0, 3},
                {0, 0, 0, 4},
                {0, 0, 0, 0}
        };
        int exp[] = new int[]{0,1,2,4};
        int[] G = l.main(arr4);
        assertArrayEquals(exp, G);
    }
    @Test public void testObst1(){
        OptimalBST l = new OptimalBST();
        int arr[] = {25,20,5,20,30};
        int exp = 210;
        int G = l.main(arr);
        assertEquals(exp, G);
    }
    @Test public void testPrim1(){
        Prim l = new Prim();
        int arr[][] = {
                {0,6,5,7},
                {6,0,0,4},
                {5,0,0,8},
                {7,4,8,0}
        };
        int exp[] = new int[]{5,6,5,4};
        int[] G = l.main(arr);
        assertArrayEquals(exp, G);
    }

    public static void main(String[] args)
    {
        int arr[] = {1, 2, 3, 4, 5, 6, 7, 8};
        int arr2[][] =  {
                {0,1,2,0},
                {1,0,0,3},
                {2,0,0,4},
                {0,3,4,0}
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