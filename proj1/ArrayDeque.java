public class ArrayDeque<T> implements Deque<T> {

    private final int arraySize = 8;
    private final int rFactor = 2;
    private final double usageRatio = 0.25;

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /**
     * Constructor which creates an empty array deque.
     */
    public ArrayDeque() {
        items = (T[]) new Object[arraySize];
        nextFirst = arraySize / 2;
        nextLast = arraySize / 2 + 1;
        size = 0;
    }

    /**
     * Adds an item of type T to the front of the deque.
     *
     * @param item, another item with type T.
     */
    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            largerSize(items.length * rFactor);
        }
        items[nextFirst] = item;
        size += 1;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
    }

    /**
     * Adds an item of type T to the back of the deque.
     *
     * @param item, another item with type T.
     */
    @Override
    public void addLast(T item) {
        if (size == items.length) {
            largerSize(items.length * rFactor);
        }
        items[nextLast] = item;
        size += 1;
        nextLast = (nextLast + 1) % items.length;
    }


    /**
     * Make a new array and resize it so it is two times larger
     * than the original array and copy all items to new array.
     */
    private void largerSize(int newSize) {
        T[] newArray = (T[]) new Object[newSize];
        int srcPos = (newArray.length / 2) - (items.length / 2);
        int ptr = srcPos;
        int originalIndex = nextFirst + 1;
        for (int i = 0; i < items.length; i += 1) {
            newArray[ptr] = items[originalIndex % items.length];
            ptr += 1;
            originalIndex += 1;
        }
        items = newArray;
        nextFirst = srcPos - 1;
        nextLast = ptr;
    }

    /**
     * Make a new array and resize it so it is half times smaller
     * than the original array and copy all items to new array.
     */
    private void smallerSize(int newSize) {
        T[] newArray = (T[]) new Object[newSize];
        int srcPos = (newArray.length / 2) - (size / 2);
        int ptr = srcPos;
        int originalIndex = nextFirst + 1;
        for (int i = 0; i < size; i += 1) {
            newArray[ptr] = items[originalIndex % items.length];
            ptr += 1;
            originalIndex += 1;
        }
        items = newArray;
        nextFirst = srcPos - 1;
        nextLast = ptr;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     *
     * @return whether the deque is empty.
     */


    /**
     * Returns the number of items in the deque.
     *
     * @return item number in deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    @Override
    public void printDeque() {
        int index = nextFirst + 1;
        if (index >= items.length) {
            index -= items.length;
        }
        while (index != nextLast) {
            System.out.print(items[index] + " ");
            index += 1;
            if (index >= items.length) {
                index -= items.length;
            }
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
        if (isEmpty()) {
            return null;
        }
        if (items.length >= 16 && (double) size / items.length < usageRatio) {
            smallerSize(items.length / 2);
        }
        nextFirst = (nextFirst + 1) % items.length;
        T rItem = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        return rItem;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     *
     * @return last item of deque.
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (items.length >= 16 && (double) size / items.length < usageRatio) {
            smallerSize(items.length / 2);
        }
        nextLast = (nextLast - 1 + items.length) % items.length;
        T rItem = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        return rItem;
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
        if (index >= items.length || index < 0) {
            return null;
        }
        int ptr = nextFirst + index + 1;
        if (ptr >= items.length) {
            ptr -= items.length;
        }
        return items[ptr];
    }
}
