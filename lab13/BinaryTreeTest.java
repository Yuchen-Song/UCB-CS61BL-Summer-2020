import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BinaryTreeTest {
    @Test
    public void treeFormatTest() {
        BinarySearchTree<String> x = new BinarySearchTree<String>();
        x.add("C");
        x.add("A");
        x.add("E");
        x.add("B");
        x.add("D");
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outContent));
        BinaryTree.print(x, "x");
        System.setOut(oldOut);
        assertEquals("x in preorder\nC A B E D \nx in inorder\nA B C D E \n\n".trim(),
                     outContent.toString().trim());
    }

    @Test
    public void treeFormatTest2() {
        BinarySearchTree<Integer> y = new BinarySearchTree<Integer>();
        y.add(1);
        y.add(1);
        y.add(2);
        y.add(3);
        y.add(3);
        y.add(4);
        y.add(5);
        y.add(5);
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outContent));
        BinaryTree.print(y, "y");
        System.setOut(oldOut);
        assertEquals("y in preorder\n1 2 3 4 5 \ny in inorder\n1 2 3 4 5 \n\n".trim(),
                outContent.toString().trim());
    }
}