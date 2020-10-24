import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;

public class RadixSortTester {

    @Test
    public void testRadixSort() {
        String[] testArr1 = {"alatn",  "hello", "succe", "donld", "hcdlo","heleh", "12321", "!*^&!"};
        String[] result1 = RadixSort.sort(testArr1);

        System.out.println(Arrays.toString(result1));

        String[] expected1 = {"alatn",  "hello", "succe", "donld", "hcdlo","heleh", "12321", "!*^&!"};
        assertArrayEquals(testArr1, expected1);

    }
}
