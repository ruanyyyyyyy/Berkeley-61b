import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {
    @Test
    public void testisSameNumber() {
        assertTrue(Flik.isSameNumber(1,1));
        assertEquals(false, Flik.isSameNumber(2,1));
        assertTrue(Flik.isSameNumber(5,5));
        assertTrue(Flik.isSameNumber(127,127));
        assertTrue(Flik.isSameNumber(128,128));

    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.TestRunner.runTests("failed", FlikTest.class);
    }
}
