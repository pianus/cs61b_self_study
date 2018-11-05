import static org.junit.Assert.*;
import org.junit.Test;

public class ArrayDequeTest {
    @Test
    public void testResizeArray() {
        //array increase size
        int[] lst = new int[] {1, 2, 3, 4, 5, 6, 7, 8};
        ArrayDeque<Integer> ad = makeList(lst);
        ad.addFirst(100);
        Integer e100 = 100;
        Integer e0 = 0;
        Integer e1 = 1;
        Integer e2 = 2;
        Integer e3 = 3;
        Integer e4 = 4;
        Integer e5 = 5;
        Integer e6 = 6;
        Integer e7 = 7;
        Integer e8 = 8;

        assertEquals(e100, ad.get(0));
        assertEquals(e5, ad.get(5));
        assertEquals(e2, ad.get(2));

        int[] lst2 = new int[] {1, 2, 3, 4, 5, 6, 7};
        ArrayDeque<Integer> ad2 = makeList(lst2);
        ad2.addFirst(0);
        ad2.addFirst(100);
        assertEquals(e100, ad2.get(0));
        assertEquals(e0, ad2.get(1));
        assertEquals(e1, ad2.get(2));

        //array decrease size
        assertEquals(e8, ad.removeLast());
        assertEquals(e7, ad.removeLast());
        assertEquals(e6, ad.removeLast());
        assertEquals(e5, ad.removeLast());
        assertEquals(e4, ad.removeLast());
        assertEquals(e3, ad.removeLast());

        //add a lot of numbers in list
        for (int i = 0; i < 129; i++) {
            ad2.addFirst(0);
        }

    }

    @Test
    public void testAddLastRemoveFirst() {
        //array increase size
        int[] lst = new int[]{1, 2, 3, 4, 5, 6};
        ArrayDeque<Integer> ad = makeList(lst);
        for (int i = 0; i < 100; i++) {
            ad.addLast(1);
            ad.addFirst(2);
        }
        for (int i = 0; i < 100; i++) {
            ad.removeFirst();
            ad.removeFirst();
        }
        assertEquals(6,ad.size());
    }

    public static ArrayDeque<Integer> makeList(int[] input) {
        ArrayDeque<Integer> ad = new ArrayDeque();
        for (int i: input) {
            ad.addLast(i);
        }
        return ad;
    }
}
