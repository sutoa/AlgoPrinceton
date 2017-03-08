import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RandomizedQueueTest {
    private RandomizedQueue<Integer> numbers;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        numbers = new RandomizedQueue<>();
    }

    @Test
    public void newQueueIsEmpty() throws Exception {
        assertEquals(true, numbers.isEmpty());
    }

    @Test
    public void dequeueRightAfterEnqueueFirstItemReturnTheItem() throws Exception {
        numbers.enqueue(1);
        assertEquals(Integer.valueOf(1), numbers.dequeue());
    }

    @Test
    public void addNewItemThrowsException() throws Exception {
        expectedException.expect(NullPointerException.class);
        numbers.enqueue(null);
    }

    @Test
    public void dequeueFromEmptyQueueThrowsException() throws Exception {
        expectedException.expect(NoSuchElementException.class);
        numbers.dequeue();

    }

    @Test
    public void sampleFromEmptyQueueThrowsException() throws Exception {
        expectedException.expect(NoSuchElementException.class);
        numbers.sample();
    }

    @Test
    public void dequeueNTimesToEmptyTheQueue() throws Exception {
        Integer[] data = {2, 45, 12, 53, 12};
        for (Integer i :
                data) {
            numbers.enqueue(i);
        }

        assertEquals(5, numbers.size());

        final ArrayList<Integer> output = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            output.add(numbers.dequeue());
        }

        assertEquals(data.length, output.size());
        assertTrue(Arrays.asList(data).containsAll(output));

    }

    @Test
    public void doublesCapacityWhenArrayIsFull() throws Exception {
        assertEquals(1, numbers.capacity());
        numbers.enqueue(1);
        assertEquals(2, numbers.capacity());
        numbers.enqueue(2);
        assertEquals(4, numbers.capacity());

    }

    @Test
    public void reducesCapacityWhenArrayIsQuarterFull() throws Exception {
        numbers.enqueue(1);
        numbers.enqueue(2);
        numbers.enqueue(3);
        numbers.enqueue(4);
        numbers.enqueue(5);
        assertEquals(8, numbers.capacity());
                      numbers.dequeue();
        assertEquals(8, numbers.capacity());
        numbers.dequeue();
        numbers.dequeue();

        assertEquals(4, numbers.capacity());

    }
}
