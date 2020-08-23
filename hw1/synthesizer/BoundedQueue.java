package synthesizer;
import java.util.Iterator;

/**
 * created by KathyR on Aug, 2020.
 * @param <T>
 */
public interface BoundedQueue<T> extends Iterable<T> {
    /** return size of the buffer. */
    int capacity();

    /** return current number of the buffer. */
    int fillCount();

    /** add item x to the queue. */
    void enqueue(T x);

    /** delete item x in the queue. */
    T dequeue();

    /** return but not delete the item from the fron. */
    T peek();

    /** is the buffer empty. */
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /** is the buffer full. */
    default boolean isFull() {
        return fillCount() == capacity();
    }

    @Override
    Iterator<T> iterator();
}
