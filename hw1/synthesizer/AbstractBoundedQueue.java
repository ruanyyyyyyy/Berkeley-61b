package synthesizer;

/**
 * an abstract class abstract bounded queue.
 * @param <T>
 * @author kathy
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {

    /**
     * index for fillcount.
     */
    protected int fillCount;
    /**
     * index for capacity.
     */
    protected int capacity;

    /* capacity() */
    @Override
    public int capacity() {
        return capacity;
    }
    /* fillCount() */
    @Override
    public int fillCount() {
        return fillCount;
    }



}
