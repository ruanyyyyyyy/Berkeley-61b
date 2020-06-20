/**
 * Test planets
 */
public class TestPlanet {
    /**
     * Test planets
     */
    public static void main(String[] args) {
        checkPairwiseForce();
    }
    /**
     *  Checks whether or not two Doubles are equal and prints the result.
     *
     *  @param  expected    Expected double
     *  @param  actual      Double received
     *  @param  label       Label for the 'test' case
     *  @param  eps         Tolerance for the double comparison.
     */
    private static void checkEquals(double actual, double expected, String label, double eps) {
        if (Math.abs(expected - actual) <= eps * Math.max(expected, actual)) {
            System.out.println("PASS: " + label + ": Expected " + expected + " and you gave " + actual);
        } else {
            System.out.println("FAIL: " + label + ": Expected " + expected + " and you gave " + actual);
        }
    }

    /**
     *  Checks the Planet class to make sure it works.
     */
    private static void checkPairwiseForce() {
        System.out.println("Checking pairwiseForce...");

        Planet p1 = new Planet(1e12, 2e11, 0.0, 0.0, 2e13, "jupiter.gif");
        Planet p2 = new Planet(2.3e12, 9.5e11, 0.0, 0.0, 6e26, "jupiter.gif");

        checkEquals(p1.calcForceExertedByX(p2), 3.1e22, "F1x()", 0.01);
        checkEquals(p1.calcForceExertedByY(p2), 1.8e22, "F1y()", 0.01);
        checkEquals(p2.calcForceExertedByX(p1), -3.1e22, "F1x()", 0.01);
        checkEquals(p2.calcForceExertedByY(p1), -1.8e22, "F1y()", 0.01);
    }
}