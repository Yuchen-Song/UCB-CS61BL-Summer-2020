public class BinaryTree<T> {

    TreeNode<T> root;

    public BinaryTree() {
        root = null;
    }

    public BinaryTree(TreeNode<T> t) {
        root = t;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    /* Creates two BinaryTrees and prints them out in inorder. */
    public static void main(String[] args) {
        BinaryTree t;
        t = new BinaryTree();
        System.out.print(t.height());
        System.out.print(t.isCompletelyBalanced());
        print(t, "fib tree 5");

        print(t, "the empty tree");
        t.sampleTree1();
        print(t, "sample tree 1");
        t.sampleTree2();
        print(t, "sample tree 2");
    }

    /* Returns the height of the tree. */
    public int height() {
        BinaryTree t = new BinaryTree(root);
        if (root == null) {
            return 0;
        } else if (root.left == null && root.right == null) {
            return 1;
        } else {
            if (root.left == null) {
                BinaryTree tr = new BinaryTree(root.right);
                return 1 + tr.height();
            } else if (root.right == null) {
                BinaryTree tl = new BinaryTree(root.left);
                return 1 + tl.height();
            }
            BinaryTree tl = new BinaryTree(root.left);
            BinaryTree tr = new BinaryTree(root.right);
            return 1 + (tl.height()>tr.height()? tl.height():tr.height());
        }
    }

    /* Returns a BinaryTree representing the Fibonacci calculation for N. */
    public static BinaryTree<Integer> fibTree(int N) {
        BinaryTree<Integer> result = new BinaryTree<Integer>();
        if (N==0 || N==1) {
            TreeNode root = new TreeNode(null);
            root.setItem(N);
            root.setLeft(null);
            root.setRight(null);
            return  result = new BinaryTree(root);
        } else {
            TreeNode root = new TreeNode(null);
            root.setItem(fib(N));
            root.setLeft(fibTree(N-1).getRoot());
            root.setRight(fibTree(N-2).getRoot());
            return result = new BinaryTree<Integer>(root);
        }
    }

    private static int fib(int n){
        if (n==0 || n==1){
            return n;
        } else {
            return fib(n-1) + fib(n-2);
        }
    }

    /* Print the values in the tree in preorder: root value first, then values
       in the left subtree (in preorder), then values in the right subtree
       (in preorder). */
    public void printPreorder() {
        if (root == null) {
            System.out.println("(empty tree)");
        } else {
            root.printPreorder();
            System.out.println();
        }
    }

    /* Print the values in the tree in inorder: values in the left subtree
       first (in inorder), then the root value, then values in the first
       subtree (in inorder). */
    public void printInorder() {
        if (root == null) {
            System.out.println("(empty tree)");
        } else {
            root.printInorder();
            System.out.println();
        }
    }

    /* Prints out the contents of a BinaryTree with a description in both
       preorder and inorder. */
    private static void print(BinaryTree t, String description) {
        System.out.println(description + " in preorder");
        t.printPreorder();
        System.out.println(description + " in inorder");
        t.printInorder();
        System.out.println();
    }

    /* Fills this BinaryTree with values a, b, and c. DO NOT MODIFY. */
    public void sampleTree1() {
        root = new TreeNode("a", new TreeNode("b"), new TreeNode("c"));
    }

    /* Fills this BinaryTree with values a, b, and c, d, e, f. DO NOT MODIFY. */
    public void sampleTree2() {
        root = new TreeNode("a",
                  new TreeNode("b", new TreeNode("d", new TreeNode("e"),
                  new TreeNode("f")), null), new TreeNode("c"));
    }

    /* Fills this BinaryTree with the values a, b, c, d, e, f. DO NOT MODIFY. */
    public void sampleTree3() {
        root = new TreeNode("a", new TreeNode("b"), new TreeNode("c",
               new TreeNode("d", new TreeNode("e"), new TreeNode("f")), null));
    }

    /* Fills this BinaryTree with the same leaf TreeNode. DO NOT MODIFY. */
    public void sampleTree4() {
        TreeNode leafNode = new TreeNode("c");
        root = new TreeNode("a", new TreeNode("b", leafNode, leafNode),
                                 new TreeNode("d", leafNode, leafNode));
    }

    /* Returns true if the tree's left and right children are the same height
       and are themselves completely balanced. */
    public boolean isCompletelyBalanced() {
        BinaryTree t = new BinaryTree(root);
        if (root == null) {
            return true;
        } else if (root.left == null && root.right == null) {
            return true;
        } else if (root.left == null || root.right == null) {
            return false;
        } else {
            BinaryTree tl = new BinaryTree(root.left);
            BinaryTree tr = new BinaryTree(root.right);
            return (tl.height() == tr.height() && tl.isCompletelyBalanced() && tr.isCompletelyBalanced());
        }
    }

    /* Note: this class is public in this lab for testing purposes. However,
       in professional settings as well as the rest of your labs and projects,
       we recommend that you keep your inner classes private. */
    static class TreeNode<T> {

        private T item;
        private TreeNode left;
        private TreeNode right;

        TreeNode(T obj) {
            item = obj;
            left = null;
            right = null;
        }

        TreeNode(T obj, TreeNode<T> left, TreeNode<T> right) {
            item = obj;
            this.left = left;
            this.right = right;
        }

        public T getItem() {
            return item;
        }

        public TreeNode<T> getLeft() {
            return left;
        }

        public TreeNode<T> getRight() {
            return right;
        }

        void setItem(T item) {
            this.item = item;
        }

        void setLeft(TreeNode<T> left) {
            this.left = left;
        }

        void setRight(TreeNode<T> right) {
            this.right = right;
        }

        private void printPreorder() {
            System.out.print(item + " ");
            if (left != null) {
                left.printPreorder();
            }
            if (right != null) {
                right.printPreorder();
            }
        }

        private void printInorder() {
            if (left != null) {
                left.printInorder();
            }
            System.out.print(item + " ");
            if (right != null) {
                right.printInorder();
            }
        }

        // TODO: ADD HELPER METHODS HERE

    }
}
