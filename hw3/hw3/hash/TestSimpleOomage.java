package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;


public class TestSimpleOomage {

    @Test
    public void testHashCodeDeterministic() {
        SimpleOomage so = SimpleOomage.randomSimpleOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    @Test
    public void testHashCodePerfect() {
        /* Write a test that ensures the hashCode is perfect,
          meaning no two SimpleOomages should EVER have the same
          hashCode UNLESS they have the same red, blue, and green values!
         */
        SimpleOomage ooA = new SimpleOomage(45, 50, 185);
        SimpleOomage ooA2 = new SimpleOomage(55, 220, 130);
        SimpleOomage ooB = new SimpleOomage(0, 0, 65);
        SimpleOomage ooB2 = new SimpleOomage(0, 35, 0);
        SimpleOomage ooC = new SimpleOomage(0, 0, 155);
        SimpleOomage ooC2 = new SimpleOomage(0, 5, 0);
        SimpleOomage ooD = new SimpleOomage(0, 0, 0);
        SimpleOomage ooD2 = new SimpleOomage(0, 0, 85);
        Set<SimpleOomage> hs = new HashSet<>();
        hs.add(ooA);
        assertNotEquals(ooA, ooA2);
        assertNotEquals(ooA.hashCode(), ooA2.hashCode());
        assertFalse(hs.contains(ooA2));
        assertNotEquals(ooB.hashCode(), ooB2.hashCode());
        assertNotEquals(ooC.hashCode(), ooC2.hashCode());
        assertNotEquals(ooD.hashCode(), ooD2.hashCode());
    }

    @Test
    public void testEquals() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        assertNotEquals(ooA, "ketchup");
    }


    @Test
    public void testHashCodeAndEqualsConsistency() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        HashSet<SimpleOomage> hashSet = new HashSet<>();
        hashSet.add(ooA);
        assertTrue(hashSet.contains(ooA2));
    }


    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(SimpleOomage.randomSimpleOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestSimpleOomage.class);
    }
}
