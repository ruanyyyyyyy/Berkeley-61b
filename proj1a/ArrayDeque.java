public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** create an empty list. */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /** Resize the underlying array to the target capacity. */
    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        int oldStart = addOne(nextFirst);
        for (int i = 0; i < size; i += 1) {
            newItems[i] = items[oldStart];
            oldStart = addOne(oldStart);
        }
        this.items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    private int addOne(int a) {
        return (a + 1) % items.length;

    }
    private int subOne(int b) {
        return (b - 1 + items.length) % items.length;
    }

    /** make sure the usage factor be at least 25% for arrays of length 16 or more */
    private void modifyUsageFactor() {
        if ((items.length > 15) && (size < items.length / 4)) {
            resize((items.length / 2)); //FIXME: change the double to integer type
        }
    }

    /** Insert x into the front of the list. */
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = x;
        size += 1;
        nextFirst = subOne(nextFirst);

    }
    /** Insert x into the back of the list. */
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = x;
        size += 1;
        nextLast = addOne(nextLast);
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }


    /** Deletes item from front of the list and
     * return deleted items.
     * @return T
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        modifyUsageFactor();
        int pos = addOne(nextFirst);
        T x = items[pos];
        items[pos] = null;
        size = size - 1;
        nextFirst = addOne(nextFirst);
        return x;

    }
    /** Deletes item from back of the list and
     * return deleted items.
     * @return T
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        modifyUsageFactor();
        int pos = subOne(nextLast);
        T x = items[pos];
        items[pos] = null;
        size = size - 1;
        nextLast = subOne(nextLast);
        return x;

    }
    /** Get the ith item in the list (0 is the front).*/
    public T get(int i) {
        int start = addOne(nextFirst);
        int index = (start + i) % items.length;
        return items[index];
    }

    public void printDeque() {
        for (int i = 0; i < size; i += 1) {
            int pos = addOne(nextFirst) + i;
            System.out.print(items[pos] + " ");
        }
    }

}
