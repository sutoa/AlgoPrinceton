import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class DequeTest {

    private Deque<String> deque;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        deque = new Deque<>();
    }

    @Test
    public void aNewDequeIsEmpty() throws Exception {
        assertTrue(deque.isEmpty());
    }

    @Test
    public void removeAfterAddFirstReturnsTheSameItem() throws Exception {
        final String item = "tom";
        deque.addFirst(item);
        assertEquals(item, deque.removeFirst());
        assertEquals(true, deque.isEmpty());
    }

    @Test
    public void removeAfterAddLastReturnsTheSameItem() throws Exception {
        final String item = "tom";
        deque.addLast(item);
        assertEquals(item, deque.removeLast());
        assertEquals(true, deque.isEmpty());
    }

    @Test
    public void firstAndLastNodeIsTheSameWhenOnlyOneNode() throws Exception {
        deque.addFirst("tom");
        assertEquals(deque.first, deque.last);
    }

    @Test
    public void removeFirstReturnsFirstNode() throws Exception {
        deque.addFirst("tom");
        deque.addFirst("jerry");
        deque.addLast("donald");
        deque.addLast("duck");
        deque.addFirst("cat");

        assertEquals("cat", deque.removeFirst());
        assertEquals("jerry", deque.removeFirst());
        assertEquals("tom", deque.removeFirst());
        assertEquals("donald", deque.removeFirst());
    }

    @Test
    public void removeLastReturnsLastNode() throws Exception {
        deque.addFirst("tom");
        deque.addFirst("jerry");
        deque.addLast("donald");
        deque.addLast("duck");
        deque.addFirst("cat");

        assertEquals("duck", deque.removeLast());
        assertEquals("donald", deque.removeLast());
        assertEquals("tom", deque.removeLast());
        assertEquals("jerry", deque.removeLast());
        assertEquals("cat", deque.removeLast());
        assertEquals(true, deque.isEmpty());
    }

    @Test
    public void iteratesFromFirstToLast() throws Exception {
        deque.addFirst("tom");
        deque.addFirst("jerry");
        deque.addLast("donald");
        deque.addLast("duck");
        deque.addFirst("cat");

        String[] expectedNames = {"cat", "jerry", "tom", "donald", "duck"};

        final Iterator<String> names = deque.iterator();
        int i = 0;
        for (; names.hasNext(); ) {
             assertEquals(expectedNames[i++], names.next());
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFirstFromEmptyQueueThrowsException() throws Exception {
          deque.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void removeLastFromEmptyQueueThrowsException() throws Exception {
          deque.removeLast();
    }

    @Test
    public void addingNullItemThrowsException() throws Exception {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Null item is not allowed.");
        deque.addFirst(null);

    }

    @Test
    public void testBit() throws Exception {
        java.util.Deque<String> d = new ArrayDeque<String>();
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE-1));
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));


        d.addFirst("tom");
        d.addFirst("jerry");
        d.addFirst("donald");
        d.addFirst("duck");
        d.addFirst("micky");



    }
}
