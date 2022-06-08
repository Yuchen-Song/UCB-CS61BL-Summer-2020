import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void constructorTest() {
        ArrayDeque<String> a1 = new ArrayDeque<>();
        assertTrue(a1.isEmpty());
        assertEquals(a1.size(), 0);
        assertEquals(a1.get(0), null);
    }


    @Test
    public void addTest() {
        ArrayDeque<Integer> a1 = new ArrayDeque<>();
        a1.addLast(10);
        a1.addFirst(-5);
        a1.addFirst(0);
        assertFalse(a1.isEmpty());
        assertEquals(a1.size(), 3);
        int temp = a1.get(1);
        assertEquals(temp, -5);
        temp = a1.get(2);
        assertEquals(temp, 10);
    }

    @Test
    public void addTest2() {
        ArrayDeque<Character> a1 = new ArrayDeque<>();
        a1.addLast('a');
        a1.addLast('b');
        a1.addFirst('c');
        a1.addLast('d');
        a1.addLast('e');
        a1.addFirst('f');
        a1.removeFirst();
        a1.addFirst('g');
        assertFalse(a1.isEmpty());
        assertEquals(a1.size(), 6);
        char temp = a1.get(0);
        assertEquals(temp, 'g');
        temp = a1.get(5);
        assertEquals(temp, 'e');
        a1.printDeque();
    }

    @Test
    public void removeTest() {
        ArrayDeque<Integer> a1 = new ArrayDeque<>();
        a1.removeLast();
        a1.removeFirst();
        assertTrue(a1.isEmpty());
        assertEquals(a1.size(), 0);
        a1.addFirst(1);
        a1.addLast(5);
        a1.addLast(-12);
        int temp = a1.get(2);
        assertEquals(temp, -12);
        a1.removeFirst();
        a1.removeLast();
        temp = a1.get(0);
        assertEquals(temp, 5);
    }

    @Test
    public void addRemoveTest() {
        ArrayDeque<String> a2 = new ArrayDeque<>();
        assertTrue(a2.isEmpty());
        a2.addFirst("Daniel");
        a2.addLast("Yuchen");
        a2.addFirst("Wei");
        a2.addLast("Jay");
        a2.addLast("Zoe");
        a2.addFirst("Matthew");

        String name = a2.get(2);
        assertEquals(name, "Daniel");

        a2.removeFirst();
        a2.removeLast();
        a2.removeLast();

        name = a2.get(0);
        assertEquals(a2.size(), 3);
        assertEquals(name, "Wei");
        name = a2.get(2);
        assertEquals(name, "Yuchen");
        a2.printDeque();
    }

    @Test
    public void addRemoveTestResize() {
        ArrayDeque<Integer> a1 = new ArrayDeque<>();

        a1.removeFirst();
        a1.addFirst(22);
        a1.addLast(-1);
        a1.addFirst(7);
        a1.addLast(5);
        a1.addFirst(1);
        a1.addLast(7);
        a1.addFirst(3);
        a1.addLast(-1);
        a1.addFirst(5);
        a1.addFirst(9);
        a1.addFirst(12);
        a1.addLast(-5);
        a1.addFirst(4);
        a1.addFirst(0);
        a1.addLast(-16);
        a1.printDeque();

        int temp = a1.get(0);
        assertEquals(a1.size(), 15);
        assertEquals(temp, 0);
        assertEquals(a1.get(15), null);

        a1.removeFirst();
        a1.removeFirst();
        a1.removeLast();
        a1.removeLast();
        a1.removeFirst();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeFirst();
        a1.removeFirst();
        a1.removeLast();
        a1.removeFirst();

        temp = a1.get(1);
        assertEquals(a1.size(), 2);
        assertEquals(temp, 7);
        assertEquals(a1.get(7), null);
        a1.printDeque();
    }

    @Test
    public void addRemoveTestResize2() {
        ArrayDeque<Integer> a1 = new ArrayDeque<>();
        for (int i = 0; i <= 1000; i++) {
            a1.addFirst(i);
        }
        for (int i = 1000; i >= 0; i--) {
            int temp = a1.removeFirst();
            assertEquals(temp, i);
        }
        assertEquals(a1.size(), 0);
        for (int i = 0; i <= 1000; i++) {
            a1.addLast(i);
        }
        for (int i = 1000; i >= 0; i--) {
            int temp = a1.removeLast();
            assertEquals(temp, i);
        }
        assertEquals(a1.size(), 0);
    }
}
