import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testPalindrome() {
        assertTrue(palindrome.isPalindrome("abcba"));
        assertTrue(palindrome.isPalindrome("abccba"));
        assertTrue(palindrome.isPalindrome("asffffsa"));
        assertTrue(palindrome.isPalindrome("moom"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));
        assertFalse(palindrome.isPalindrome("cad"));
        assertFalse(palindrome.isPalindrome("print"));

    }

    @Test
    public void testPalindromeOff() {
        OffByOne obo = new OffByOne();
        assertTrue(palindrome.isPalindrome("a",obo));
        assertTrue(palindrome.isPalindrome("",obo));
        assertTrue(palindrome.isPalindrome("ab",obo));
        assertTrue(palindrome.isPalindrome("abccb",obo));
        assertTrue(palindrome.isPalindrome("gnpomh",obo));
        assertFalse(palindrome.isPalindrome("aaa",obo));
        assertFalse(palindrome.isPalindrome("acppdb",obo));
        assertFalse(palindrome.isPalindrome("asffffsa",obo));
    }
}