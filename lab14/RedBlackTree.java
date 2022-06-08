public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    /* Creates an empty RedBlackTree. */
    public RedBlackTree() {
        root = null;
    }

    /* Creates a RedBlackTree from a given BTree (2-3-4) TREE. */
    public RedBlackTree(BTree<T> tree) {
        Node<T> btreeRoot = tree.root;
        root = buildRedBlackTree(btreeRoot);
    }

    /* Builds a RedBlackTree that has isometry with given 2-3-4 tree rooted at
       given node R, and returns the root node. */
    RBTreeNode<T> buildRedBlackTree(Node<T> r) {
        if (r == null) {
            return null;
        }

        if (r.getItemCount() == 1) {
            return new RBTreeNode<T>(true, r.getItemAt(0),
                    buildRedBlackTree(r.getChildAt(0)), buildRedBlackTree(r.getChildAt(1)));
        } else if (r.getItemCount() == 2) {
            RBTreeNode<T> leftRedNode = new RBTreeNode<T>(false, r.getItemAt(0),
                    buildRedBlackTree(r.getChildAt(0)), buildRedBlackTree(r.getChildAt(1)));
            return new RBTreeNode<T>(true, r.getItemAt(1),
                    leftRedNode, buildRedBlackTree(r.getChildAt(2)));
        } else {
            RBTreeNode<T> leftRedNode = new RBTreeNode<T>(false, r.getItemAt(0),
                    buildRedBlackTree(r.getChildAt(0)), buildRedBlackTree(r.getChildAt(1)));
            RBTreeNode<T> rightRedNode = new RBTreeNode<T>(false, r.getItemAt(2),
                    buildRedBlackTree(r.getChildAt(2)), buildRedBlackTree(r.getChildAt(3)));
            return new RBTreeNode<T>(true, r.getItemAt(1), leftRedNode, rightRedNode);
        }
    }

    /* Flips the color of NODE and its children. Assume that NODE has both left
       and right children. */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /* Rotates the given node NODE to the right. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        //TODO: null pointer check
        RBTreeNode<T> newRoot = new RBTreeNode<T>(node.isBlack, node.left.item, node.left.left, node);
        node.left = node.left.right;
        node.isBlack = false;
        newRoot.right = node;
        return newRoot;
    }

    /* Rotates the given node NODE to the left. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        RBTreeNode<T> newRoot = new RBTreeNode<T>(node.isBlack, node.right.item, node, node.right.right);
        node.right = node.right.left;
        node.isBlack = false;
        newRoot.left = node;
        return newRoot;
    }

    public void insert(T item) {   
        root = insert(root, item);  
        root.isBlack = true;    
    }

    //return the root node after thr insertion has done
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        if (node == null) {
            return new RBTreeNode<T>(true, item);
        } else if (node.left == null && node.right == null) {
            if (item.compareTo(node.item) < 0) {
                node.left = new RBTreeNode<T>(false, item);
                return node;
            } else {
                node.right = new RBTreeNode<T>(false, item);
                return rotateLeft(node);
            }
        } else if (!isRed(node) && node.right == null && item.compareTo(node.item) > 0) {
            node.right = new RBTreeNode<>(false, item);
            flipColors(node);
            return node;
        } else {
            assert node.left != null;
            if (node.left.left == null && item.compareTo(node.left.item) < 0 && isRed(node.left)) {
                node.left.left = new RBTreeNode<>(false, item);
                node = rotateRight(node);
                flipColors(node);
                return node;
            } else if (node.left.right == null && item.compareTo(node.left.item) > 0 && isRed(node.left)) {
                node.left.right = new RBTreeNode<>(false, item);
                node.left = rotateLeft(node.left);
                node = rotateRight(node);
                flipColors(node);
                return node;
            } else {
                if (item.compareTo(node.item) < 0) {
                    node.left = insert(node.left, item);
                    return node;
                } else {
                    node.right = insert(node.right, item);
                    return node;
                }
            }
        }
    }

    /* Returns whether the given node NODE is red. Null nodes (children of leaf
       nodes are automatically considered black. */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /* Creates a RBTreeNode with item ITEM and color depending on ISBLACK
           value. */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /* Creates a RBTreeNode with item ITEM, color depending on ISBLACK
           value, left child LEFT, and right child RIGHT. */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }
}
