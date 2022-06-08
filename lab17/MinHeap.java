import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;
    private int size;
    // TODO: YOUR CODE HERE (no code should be needed here if not
    // implementing the more optimized version)

    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        contents.add(null);
    }

    /* Returns the element at index INDEX, and null if it is out of bounds. */
    private E getElement(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
    }

    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        setElement(index2, element1);
        setElement(index1, element2);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
        return 2 * index;
    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
        return 2 * index + 1;
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
        if (index % 2 == 1) {
            return (index - 1) / 2;
        } else {
            return index / 2;
        }
    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. If the elements are equal, return either index. */
    private int min(int index1, int index2) {
        if (getElement(index1) == null) {
            return index2;
        }
        else if  (getElement(index2) == null) {
            return index1;
        }
        else if (Objects.requireNonNull(getElement(index2)).compareTo(Objects.requireNonNull(getElement(index1))) > 0) {
            return index2;
        }
        else if (Objects.requireNonNull(getElement(index1)).compareTo(Objects.requireNonNull(getElement(index2))) < 0) {
            return index1;
        }
        else {
            return index1;
        }
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E findMin() {
        if (contents == null) {
            return null;
        }
        return getElement(1);
    }

    /* Bubbles up the element currently at index INDEX. */
    private void bubbleUp(int index) {
        E cur = getElement(index);
        E par = getElement(getParentOf(index));
        if (cur != null && par != null) {
            if (cur.compareTo(par) < 0) {
                swap(index, getParentOf(index));
                bubbleUp(getParentOf(index));
            }
        }
    }

    /* Bubbles down the element currently at index INDEX. */
    private void bubbleDown(int index) {
        int left = getLeftOf(index);
        int right = getRightOf(index);
        int child;
        if (getElement(left) == null) {
            return;
        }
        if (getElement(right) == null) {
            child = left;
        }
        else {
            child = (getElement(left).compareTo(getElement(right)) < 0) ? left : right;
        }
        if (getElement(index).compareTo(getElement(child)) < 0) {
            return;
        }
        swap(index, child);
        bubbleDown(child);
    }

    /* Returns the number of elements in the MinHeap. */
    public int size() {
        return size;
    }

    /* Inserts ELEMENT into the MinHeap. If ELEMENT is already in the MinHeap,
       throw an IllegalArgumentException.*/
    public void insert(E element) {
        if (contents.contains(element)) {
            throw new IllegalArgumentException();
        }
        setElement(contents.size(),element);
        size += 1;
        bubbleUp(contents.size()-1);
    }

    /* Returns and removes the smallest element in the MinHeap. */
    public E removeMin() {
        if (contents.get(1) == null) {
            return null;
        }
        else {
            E item = contents.get(1);
            swap(1, contents.size()-1);
            contents.remove(contents.size()-1);
//            contents.remove(contents.size() - 1);
            size -= 1;
            bubbleDown(1);
            return item;
        }
    }

    /* Replaces and updates the position of ELEMENT inside the MinHeap, which
       may have been mutated since the initial insert. If a copy of ELEMENT does
       not exist in the MinHeap, throw a NoSuchElementException. Item equality
       should be checked using .equals(), not ==. */
    public void update(E element) {
        if (contents == null) {
            return;
        }
        for (int i = 1; getElement(i) != null; i++) {
            if (getElement(i).equals(element)) {
                setElement(i, element);
            }
            bubbleDown(i);
            bubbleUp(i);
        }
        if (!contents.contains(element)) {
            throw new NoSuchElementException();
        }
    }

    /* Returns true if ELEMENT is contained in the MinHeap. Item equality should
       be checked using .equals(), not ==. */
    public boolean contains(E element) {
        if (contents != null) {
            int i = 1;
            while (getElement(i) != null) {
                if (getElement(i).equals(element)) {
                    return true;
                }
                i += 1;
            }
        }
        return false;
    }
}
