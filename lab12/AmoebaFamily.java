import java.util.*;

/* An AmoebaFamily is a tree, where nodes are Amoebas, each of which can have
   any number of children. */
public class AmoebaFamily implements Iterable<AmoebaFamily.Amoeba> {

    /* ROOT is the root amoeba of this AmoebaFamily */
    private Amoeba root = null;

    /* Creates an AmoebaFamily, where the first Amoeba's name is NAME. */
    public AmoebaFamily(String name) {
        root = new Amoeba(name, null);
    }

    /* Creates a new AmoebaFamily and prints it out. */
    public static void main(String[] args) {
        AmoebaFamily family = new AmoebaFamily("Amos McCoy");
        family.addChild("Amos McCoy", "mom/dad");
        family.addChild("Amos McCoy", "auntie");
        family.addChild("mom/dad", "me");
        family.addChild("mom/dad", "Fred");
        family.addChild("mom/dad", "Wilma");
        family.addChild("me", "Mike");
        family.addChild("me", "Homer");
        family.addChild("me", "Marge");
        family.addChild("Mike", "Bart");
        family.addChild("Mike", "Lisa");
        family.addChild("Marge", "Bill");
        family.addChild("Marge", "Hilary");
        System.out.println("Here's the family:");
        //family.print();
        Iterator<Amoeba> i = family.iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }

    }

    /* Adds a new Amoeba with CHILDNAME to this AmoebaFamily as the youngest
       child of the Amoeba named PARENTNAME. This AmoebaFamily must contain an
       Amoeba named PARENTNAME. */
    public void addChild(String parentName, String childName) {
        if (root != null) {
            root.addChildHelper(parentName, childName);
        }
    }

    /* Prints the name of all Amoebas in this AmoebaFamily in preorder, with
       the ROOT Amoeba printed first. Each Amoeba should be indented four spaces
       more than its parent. */
    public void print() {
        int level = 1;
        if (root != null) {
            System.out.print(root.toString());
            root.printHelper(level);
        }
    }

    /* Returns the length of the longest name in this AmoebaFamily. */
    public int longestNameLength() {
        if (root != null) {
            return root.longestNameLengthHelper();
        }
        return 0;
    }

    /* Returns the longest name in this AmoebaFamily. */
    public String longestName() {
        if (root != null) {
            return root.longestNameHelper();
        }
        return null;
    }

    /* Returns an Iterator for this AmoebaFamily. */
    public Iterator<Amoeba> iterator() {
        return new AmoebaBFSIterator();
    }

    /* An Amoeba is a node of an AmoebaFamily. */
    public static class Amoeba {

        private final String name;
        private final Amoeba parent;
        private ArrayList<Amoeba> children;

        public Amoeba(String name, Amoeba parent) {
            this.name = name;
            this.parent = parent;
            this.children = new ArrayList<Amoeba>();
        }

        public String toString() {
            return name;
        }

        public Amoeba getParent() {
            return parent;
        }

        public ArrayList<Amoeba> getChildren() {
            return children;
        }

        /* Adds child with name CHILDNAME to an Amoeba with name PARENTNAME. */
        public void addChildHelper(String parentName, String childName) {
            if (name.equals(parentName)) {
                Amoeba child = new Amoeba(childName, this);
                children.add(child);
            } else {
                for (Amoeba a : children) {
                    a.addChildHelper(parentName, childName);
                }
            }
        }

        /* Returns the length of the longest name between this Amoeba and its
           children. */
        public int longestNameLengthHelper() {
            int maxLengthSeen = name.length();
            for (Amoeba a : children) {
                maxLengthSeen = Math.max(maxLengthSeen,
                        a.longestNameLengthHelper());
            }
            return maxLengthSeen;
        }

        // TODO: ADD HELPER FUNCTIONS HERE
        public void printHelper(int level) {
            for (Amoeba A : children) {
                System.out.println();
                for (int i = 0; i < level; i++) {
                    System.out.print("    ");
                }
                System.out.print(A.name);
                if (A.hasChildren()) {
                    children = A.children;
                    printHelper(level + 1);
                }
            }

        }

        public boolean hasChildren() {
            return children != null;
        }

        public String longestNameHelper() {
            String max = name;
            for (Amoeba a : children) {
                if (a.longestNameHelper().length() > max.length()) {
                    max = a.longestNameHelper();
                }
            }
            return max;
        }
    }

    /* An Iterator class for the AmoebaFamily, running a DFS traversal on the
       AmoebaFamily. Complete enumeration of a family of N Amoebas should take
       O(N) operations. */
    public class AmoebaDFSIterator implements Iterator<Amoeba> {

        private final Stack<Amoeba> fringe = new Stack<Amoeba>();

        /* AmoebaDFSIterator constructor. Sets up all of the initial information
           for the AmoebaDFSIterator. */
        public AmoebaDFSIterator() {
            if (root != null) {
                fringe.push(root);
            }
        }

        /* Returns true if there is a next element to return. */
        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        /* Returns the next element. */
        public Amoeba next() {
            if (!hasNext()) {
                throw new NoSuchElementException("tree ran out of elements");
            }
            Amoeba a = fringe.pop();
            if (a.children != null) {
                for (int i = a.children.size() - 1; i >= 0; i--) {
                    fringe.push(a.children.get(i));
                }
            }
            return a;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* An Iterator class for the AmoebaFamily, running a BFS traversal on the
       AmoebaFamily. Complete enumeration of a family of N Amoebas should take
       O(N) operations. */
    public class AmoebaBFSIterator implements Iterator<Amoeba> {

        private final LinkedList<Amoeba> fringe = new LinkedList<Amoeba>();

        /* AmoebaBFSIterator constructor. Sets up all of the initial information
           for the AmoebaBFSIterator. */
        public AmoebaBFSIterator() {
            if (root != null) {
                fringe.addLast(root);
            }
        }

        /* Returns true if there is a next element to return. */
        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        /* Returns the next element. */
        public Amoeba next() {
            if (!hasNext()) {
                throw new NoSuchElementException("tree ran out of elements");
            }
            Amoeba a = fringe.removeFirst();
            if (a.children != null) {
                for (int i = 0; i < a.children.size(); i++) {
                    fringe.addLast(a.children.get(i));
                }
            }
            return a;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
