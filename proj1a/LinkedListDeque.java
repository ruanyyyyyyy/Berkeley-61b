public class LinkedListDeque<T> {
    public class Node {
        private T item;
        private Node next;
        private Node prior;

        public Node(T i, Node hp, Node hn) {
            item = i;
            prior = hp;
            next = hn;
        }
    }
    private Node sentinel;
    public int size;

    /** create an empty linkedListDeque. */
    public LinkedListDeque() {
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.prior = sentinel;
        sentinel.next = sentinel;
    }


    /**  Adds an item of the front. */
    public void addFirst(T x) {
        size += 1;
        sentinel.next = new Node(x, sentinel, sentinel.next);
        sentinel.next.next.prior = sentinel.next;
    }

    public void addLast(T x) {
        size += 1;
        sentinel.prior = new Node(x, sentinel.prior, sentinel);
        sentinel.prior.prior.next = sentinel.prior;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node p = sentinel;
        p = sentinel.next;
        while ((p != sentinel) && (size > 0)) {
            System.out.print(p.item + " ");
            p = p.next;
            size -= 1;
        }
    }

    public T removeFirst() {
        if (size > 0) {
            size -= 1;
            T x = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prior = sentinel;
            return x;
        }
        else {
            return null;
        }

    }

    public T removeLast() {
        if (size > 0) {
            size -= 1;
            T x = sentinel.prior.item;
            sentinel.prior = sentinel.prior.prior;
            sentinel.prior.next = sentinel;
            return x;
        }
        else {
            return null;
        }
    }

    public T get(int index) {
        Node p = sentinel;
        T x = p.item;
        for (int i = 0; i <= index; i += 1) {
            p = p.next;
        }
        x = p.item;
        return x;
    }

    public T getRecursive(int index) {
        return getRecursive(index, sentinel);
    }
    private T getRecursive(int index, Node refer) {
        if (index == 0) {
            return refer.next.item;
        }
        return getRecursive(index - 1, refer.next);
    }

}
