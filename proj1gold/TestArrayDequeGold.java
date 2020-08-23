import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testAdd() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> rightad1 = new ArrayDequeSolution<>();

        for (int i = 0; i < 10; i += 1) {
            double numberZeroOne = StdRandom.uniform();
            if (numberZeroOne < 0.5) {
                sad1.addFirst(i);
                assertFalse(sad1.size() == 0);
                rightad1.addFirst(i);
            } else {
                sad1.addLast(i);
                assertFalse(sad1.size() == 0);
                rightad1.addLast(i);
            }

        }

    }

    @Test
    public void testRemove() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> rightad1 = new ArrayDequeSolution<>();

        for (int i = 0; i < 10; i += 1) {
            double numberZeroOne = StdRandom.uniform();
            if (numberZeroOne < 0.5) {
                sad1.addFirst(i);
                rightad1.addFirst(i);
                assertEquals(sad1.removeFirst(), rightad1.removeFirst());
            } else {
                sad1.addLast(i);
                rightad1.addLast(i);
                assertEquals(sad1.removeLast(), rightad1.removeLast());
            }

        }

    }

    @Test
    public void testRandomCall() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> rightad1 = new ArrayDequeSolution<>();
        sad1.addFirst(5);
        rightad1.addFirst(5);

        sad1.addLast(3);
        rightad1.addLast(3);

        sad1.addFirst(1);
        rightad1.addFirst(1);

        assertEquals(rightad1.removeFirst(), sad1.removeFirst());
        assertEquals(rightad1.removeLast(), sad1.removeLast());
        assertEquals(rightad1.removeLast(), sad1.removeLast());
        assertEquals(rightad1.size(), sad1.size());
        

    }

}
