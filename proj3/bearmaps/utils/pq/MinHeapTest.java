package bearmaps.utils.pq;

import org.junit.Test;

import static org.junit.Assert.*;

public class MinHeapTest {

    @Test
    public void MinHeapTest(){
        MinHeap<Double> testHeap = new MinHeap<Double>();
        testHeap.insert(6.1);
        testHeap.insert(8.2);
        testHeap.insert(4.2);
        testHeap.insert(2.3);
        testHeap.insert(3.4);
        testHeap.removeMin();
        testHeap.removeMin();
        assertEquals(testHeap.size(), 3);
        assertEquals(testHeap.findMin(), 4.2, -1);
    }

    @Test
    public void MinHeapPQTest(){
        MinHeapPQ<Double> testHeapPQ = new MinHeapPQ<Double>();
        testHeapPQ.insert(6.1, 2);
        testHeapPQ.insert(2.7, 1);
        testHeapPQ.insert(4.3, 5);
        testHeapPQ.insert(2.4, 3);
        testHeapPQ.insert(3.1, 6);
        testHeapPQ.changePriority(6.1, 4);
        assertFalse(testHeapPQ.contains(5.2));
        assertTrue(testHeapPQ.contains(2.4));
        assertEquals(2.7, testHeapPQ.peek(), -1);
    }
}
