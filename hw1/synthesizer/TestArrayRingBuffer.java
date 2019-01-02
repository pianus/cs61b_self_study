package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        AbstractBoundedQueue<Integer> b2 = new ArrayRingBuffer<Integer>(100);
        /*for (double x : arb) {
            for (double y: arb) {
                System.out.println("x: " + x +  ", y:" + y);
            }
        }*/
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        //jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        AbstractBoundedQueue<Integer> b2 = new ArrayRingBuffer<Integer>(100);
        int[] someInts = new int[]{1, 2, 3};
        for (double x : arb) {
            for (double y: arb) {
                System.out.println("x: " + x +  ", y:" + y);
            }
        }
    }
} 
