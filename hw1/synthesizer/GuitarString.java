package synthesizer;

import java.util.HashSet;
import java.util.Iterator;

public class GuitarString {
    /** Constants. Do not change.
     * In case you're curious, the keyword final means
     * the values cannot be changed at runtime.
     * We'll discuss this and other topics
     * in lecture on Friday. */
    private static final int SR = 44100;
    private static final double DECAY = .996;

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /**
     *  Create a guitar string of the given frequency.
     * capacity = SR / frequency
     * @param frequency
     */
    public GuitarString(double frequency) {
        int capacity = (int) Math.round(SR / frequency);
        buffer = new ArrayRingBuffer<Double>(capacity);
        for (int i = 0; i < capacity; i += 1) {
            buffer.enqueue(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        /**
         * Make sure that your random numbers are different from each other.
         */

        HashSet<Double> hs = new HashSet<Double>();
        while (hs.size() != buffer.capacity()) {
            double r = Math.random() - 0.5;
            hs.add(r);
        }
        Iterator<Double> i = hs.iterator();
        while (i.hasNext()) {
            buffer.dequeue();
            buffer.enqueue(i.next());
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        /**
         * Dequeue the front sample and enqueue a new sample that is.
         * the average of the two multiplied by the DECAY factor.
         * Do not call StdAudio.play().
         */

        double front1 = sample();
        buffer.dequeue();
        double front2 = sample();
        double newFront = (front1 + front2) / 2 * 0.996;
        buffer.enqueue(newFront);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
