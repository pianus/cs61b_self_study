import static org.junit.Assert.*;
import org.junit.Test;

public class ArrayDequeTest {
    @Test
    public static void testResizeArray() {

    }

    @Test
    public static void createArray(ArrayDeque ad) {

    }

    public static ArrayDeque<Integer> makeList(int[] input) {
        ArrayDeque<Integer> ad = new ArrayDeque();
        for (int i: input) {
            ad.addFirst(i);
        }
        return ad;
    }
}
