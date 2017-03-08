import edu.princeton.cs.algs4.StdRandom;

import java.util.*;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size = 0;
    private int capacity = 1;
    private Item[] items = (Item[]) new Object[capacity];

    public RandomizedQueue() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    protected int capacity(){
        return capacity;
    }

    public void enqueue(Item item) {
        Objects.requireNonNull(item);

         items[size++] = item;
        if(size == capacity){
            doubleCapacity();
        }
    }

    private void doubleCapacity() {
        capacity = capacity * 2;
        items = Arrays.copyOf(items, capacity);
    }

    public Item dequeue() {
        if(isEmpty()) throw new NoSuchElementException();

        final int index = StdRandom.uniform(size());
        final Item item = items[index];
        items[index] = items[size - 1];
        items[size - 1] = null;
        size--;
        if(size == (capacity / 4)){
            reduceCapacityByHalf();
        }
        return item;
    }

    private void reduceCapacityByHalf() {
        capacity = capacity / 2;
        items = Arrays.copyOf(items, capacity);
    }

    public Item sample() {
        if(isEmpty()) throw new NoSuchElementException();
        return null;
    }

    @Override
    public Iterator<Item> iterator() {
        return null;
    }
}

