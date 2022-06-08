public class LinkedListDeque<T> implements Deque<T> {

    private final Node sentinel;
    private int size;
    /**
     * Constructor which creates an empty linked list deque.
     */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**
     * Adds an item of type T to the front of the deque.
     *
     * @param item, another item with type T.
     */
    @Override
    public void addFirst(T item) {
        sentinel.next = new Node(item, sentinel.next, sentinel);
        sentinel.next.next.prev = sentinel.next;
        size++;
    }

    /**
     * Adds an item of type T to the back of the deque.
     *
     * @param item, another item with type T.
     */
    @Override
    public void addLast(T item) {
        sentinel.prev = new Node(item, sentinel, sentinel.prev);
        sentinel.prev.prev.next = sentinel.prev;
        size++;
    }

    /**
     * Returns the number of items in the deque.
     *
     * @return item number in deque.
     */
    @Override
    public int size() {
        return size;
    }

    /** Returns true if deque is empty, false otherwise.
     *
     * @return whether the deque is empty.
     */
    //This method should be deleted and migrated to Deque.java during Part 3

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    @Override
    public void printDeque() {
        Node node = this.sentinel.next;
        while (node != sentinel) {
            System.out.print(node.item + " ");
            node = node.next;
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     *
     * @return most front item of deque.
     */
    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        } else {
            T firstItem = (T) sentinel.next.item;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            size--;
            return firstItem;
        }
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     *
     * @return last item of deque.
     */
    @Override
    public T removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        } else {
            T lastItem = (T) sentinel.prev.item;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size--;
            return lastItem;
        }
    }

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque!
     *
     * @param index, position of an item
     * @return item at given index
     */
    @Override
    public T get(int index) {
        Node curr = sentinel; //curr should be an address, otherwise, use new
        for (int i = 0; i <= index; i++) {
            curr = curr.next;
        }
        return (T) curr.item;
    }

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque! (Recursively).
     *
     * @param index, position of an item
     * @return item at given index
     */
    public T getRecursive(int index) {
        Node curr = sentinel.next;//execute once
        return getRevursiveHelper(0, index, curr);
    }

    //return the item of node[currIndex]
    private T getRevursiveHelper(int currIndex, int objIndex, Node curr) {
        //only if currIndex == objIndex can we return
        if (currIndex == objIndex) {
            return (T) curr.item;
        }
        curr = curr.next;
        currIndex++;
        return getRevursiveHelper(currIndex, objIndex, curr);
    }

    private static class Node<T> {
        public T item;
        public Node next;
        public Node prev;

        public Node(T item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
