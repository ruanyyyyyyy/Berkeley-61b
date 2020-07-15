import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;
public class ArrayDequeTest {

    @Test
    public void testGet() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        ad1.addFirst(0);
        ad1.addFirst(1);
        ad1.addFirst(2);
        int gotItem = ad1.get(0);
        assertEquals(2, gotItem);
    }

    @Test
    public void testRemoveFirst() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        ad1.addFirst(0);
        ad1.addFirst(1);
        ad1.addFirst(2);
        int first = ad1.removeFirst();
        assertEquals(2, first);

    }
    


}
