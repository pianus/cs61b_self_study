import static org.junit.Assert.*;
import org.junit.Test;

public class ArrayDequeTest {
    @Test
    public static void testResizeArray() {

    }


    public static ArrayDeque<Integer> makeList(int[] input) {
        ArrayDeque<Integer> ad = new ArrayDeque();
        for (int i: input) {
            ad.addFirst(i);
        }
        return ad;
    }
    public static void main(String[] args) {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
    }
}
