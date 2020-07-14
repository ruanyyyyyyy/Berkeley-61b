import static org.junit.Assert.*;
import org.junit.Test;

public class ArrayDequeTest {

    @Test
    public void testGet() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        ad1.addFirst(0);
        ad1.addFirst(1);
        ad1.addFirst(2);
        int gotItem = ad1.get(0);
        assertEquals(0, gotItem);
    }


}
