/** A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 *
 * @author Maurice Lee and Wan Fung Chui
 */

public class IntList {

    /** The integer stored by this node. */
    public int item;
    /** The next node in this IntList. */
    public IntList next;

    /** Constructs an IntList storing ITEM and next node NEXT. */
    public IntList(int item, IntList next) {
        this.item = item;
        this.next = next;
    }

    /** Constructs an IntList storing ITEM and no next node. */
    public IntList(int item) {
        this(item, null);
    }
/*
    public IntList(IntList I){
        this.item = I.item;
        this.next = I.next;
    }

 */
    /** Returns an IntList consisting of the elements in ITEMS.
     * IntList L = IntList.list(1, 2, 3);
     * System.out.println(L.toString()) // Prints 1 2 3 */
    public static IntList of(int... items) {
        /** Check for cases when we have no element given. */
        if (items.length == 0) {
            return null;
        }
        /** Create the first element. */
        IntList head = new IntList(items[0]);
        IntList last = head;
        /** Create rest of the list. */
        for (int i = 1; i < items.length; i++) {
            last.next = new IntList(items[i]);
            last = last.next;
        }
        return head;
    }

    //return the length of the list
    public int length(){
        int l=1;
        IntList L = this;
        while(L.next!=null){
            l++;
            L = L.next;
        }
        return l;
    }
    /**
     * Returns [position]th item in this list. Throws IllegalArgumentException
     * if index out of bounds.
     *
     * @param position, the position of element.
     * @return The element at [position]
     */
    public int get(int position) {
        if(position<0 || position>=this.length()){
            throw new IllegalArgumentException("Out of range!");
        }
        IntList L = this;
        for(int i=0; i<position; i++){
            L = L.next;
        }
        return L.item;
    }

    /**
     * Returns the string representation of the list. For the list (1, 2, 3),
     * returns "1 2 3".
     *
     * @return The String representation of the list.
     */
    public String toString() {
        IntList L = this;
        String s = "";
        while(L!=null){
            if(L.next == null){
                s = s + L.item;
            }
            else{ s = s + L.item + " ";}
            L = L.next;
        }
        return s;
    }

    /**
     * Returns whether this and the given list or object are equal.
     *
     * @param obj, another list (object)
     * @return Whether the two lists are equal.
     */

    public boolean equals(Object obj) {
        if(obj == null || (!(obj instanceof IntList))) {
            return false;
        }
        IntList thisLst = this;
        IntList otherLst = (IntList) obj;
        if(thisLst.length() != otherLst.length()){ return false; }
        else{
            while(thisLst.next!=null){
                if(thisLst.item != otherLst.item){return false;}
                thisLst = thisLst.next;
                otherLst = otherLst.next;
            }
            return true;
        }
    }
    /**
     * Adds the given value at the end of the list.
     *
     * @param value, the int to be added.
     */
    public void add(int value) {
        IntList L = this;
        while(L.next != null){
            L = L.next;
        }
        IntList N = new IntList(value);
        L.next = N;
    }

    /**
     * Returns the smallest element in the list.
     *
     * @return smallest element in the list
     */
    public int smallest() {
        IntList L = this;
        int s = this.item;
        while(L != null){
            s= s > L.item ? L.item : s;
            L = L.next;
        }
        return s;
    }

    /**
     * Returns the sum of squares of all elements in the list.
     *
     * @return The sum of squares of all elements.
     */
    public int squaredSum() {
        IntList L = this;
        int s = 0;
        while(L!=null){
            s += L.item * L.item;
            L = L.next;
        }
        return s;
    }

    /**
     * Destructively squares each item of the list.
     *
     * @param L list to destructively square.
     */
    public static void dSquareList(IntList L) {
        while (L != null) {
            L.item = L.item * L.item;
            L = L.next;
        }
    }

    /**
     * Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListIterative(IntList L) {
        if (L == null) {
            return null;
        }
        IntList res = new IntList(L.item * L.item, null);
        IntList ptr = res;
        L = L.next;
        while (L != null) {
            ptr.next = new IntList(L.item * L.item, null);
            L = L.next;
            ptr = ptr.next;
        }
        return res;
    }

    /** Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListRecursive(IntList L) {
        if (L == null) {
            return null;
        }
        return new IntList(L.item * L.item, squareListRecursive(L.next));
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
    public static IntList dcatenate(IntList A, IntList B) {
        //coding on the basic idea of add method
        if(A == null){return B;}
        if(B == null){return A;}
        IntList L = A;
        while(A.next != null){
            A = A.next;
        }
        IntList N = new IntList(B.item,B.next);
        A.next = N;
        return L;
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * non-destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
     public static IntList catenate(IntList A, IntList B) {
         //iteration
         if(A == null){return B;}
         if(B == null){return A;}
         IntList res = new IntList(A.item);
         IntList ptr = res;
         A = A.next;
         while(A != null){
             ptr.next = new IntList(A.item);
             A = A.next;
             ptr = ptr.next;
         }
         ptr.next = new IntList(B.item, B.next);
         return res;

         //recursion
         //if(A == null){return B;}

     }

     public static void main(String[] args){
         IntList origL1 = IntList.of(1, 2, 3, 4);
         IntList origL2 = IntList.of(5, 6, 7, 8);
         IntList out = catenate(origL1, origL2);
         System.out.println(origL1.toString());
         System.out.print(out.toString());
         //System.out.print(New.toString());
     }
}