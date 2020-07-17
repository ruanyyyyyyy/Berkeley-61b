import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testRandomCall() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> rightad1 = new ArrayDequeSolution<>();

        for (int i = 0; i < 10; i += 1) {
            double numberZeroOne = StdRandom.uniform();
            if (numberZeroOne < 0.25) {
                sad1.addFirst(i);
                rightad1.addFirst(i);
            } else if (numberZeroOne < 0.5) {
                sad1.addLast(i);
                rightad1.addLast(i);
            } else if (numberZeroOne < 0.75) {
                if (sad1.size() > 0) {
                    Integer a1 = sad1.removeFirst();
                    Integer a2 = rightad1.removeFirst();
                    assertTrue(a1.equals(a2));
                }
            } else {
                if (sad1.size() > 0) {
                    Integer a1 =sad1.removeLast();
                    Integer a2 =rightad1.removeFirst();
                    assertTrue(a1.equals(a2));
                }
            }

        }

    }

}

    StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();

        for (int i = 0; i < 10; i += 1) {
        double numberBetweenZeroAndOne = StdRandom.uniform();

        if (numberBetweenZeroAndOne < 0.5) {
        sad1.addLast(i);
        } else {
        sad1.addFirst(i);
        }
        }

        sad1.printDeque();