import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChars() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('c', 'b'));
        assertTrue(offByOne.equalChars('%', '&'));
        assertTrue(offByOne.equalChars('C', 'B'));
        assertFalse(offByOne.equalChars('c', 'a'));
        assertFalse(offByOne.equalChars('c', 'c'));
        assertFalse(offByOne.equalChars('A', 'F'));

    }
}
