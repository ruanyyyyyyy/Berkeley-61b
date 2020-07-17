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
    public void testIsPalindrome() {
        assertFalse(palindrome.isPalindrome("cat"));
        assertFalse((palindrome.isPalindrome("horse")));

        assertTrue(palindrome.isPalindrome("w"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("werew"));
        assertTrue(palindrome.isPalindrome("weew"));
        assertTrue(palindrome.isPalindrome("aaaa"));


        CharacterComparator cc = new OffByOne();
        assertFalse(palindrome.isPalindrome("cat", cc));
        assertFalse((palindrome.isPalindrome("horse", cc)));

        assertTrue(palindrome.isPalindrome("w", cc));
        assertTrue(palindrome.isPalindrome("", cc));
        assertFalse(palindrome.isPalindrome("werew", cc));
        assertFalse(palindrome.isPalindrome("weew", cc));
        assertFalse(palindrome.isPalindrome("aaaa", cc));

        assertTrue(palindrome.isPalindrome("flake", cc));


    }

}
