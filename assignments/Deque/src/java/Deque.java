import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Deque<Item> implements Iterable<Item> {
    Node<Item> first = null;
    Node<Item> last = null;
    private int size = 0;

    public Deque() {
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        Objects.requireNonNull(item, "Null item is not allowed.");

        size++;
        Node<Item> oldFirst = first;
        first = new Node(item, null, oldFirst);
        link(first, oldFirst);

        if (size == 1) {
            last = first;
        }
    }

    private void link(Node<Item> previous, Node<Item> next) {
        if (previous != null) {
            previous.next = next;
        }

        if (next != null) {
            next.previous = previous;
        }
    }

    public void addLast(Item item) {
        Objects.requireNonNull(item, "Null item is not allowed.");

        size++;
        Node<Item> oldLast = last;
        last = new Node<>(item, oldLast, null);
        link(oldLast, last);

        if (size == 1) {
            first = last;
        }
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Node<Item> oldFirst = first;
        first = first.next;
        size--;
        return oldFirst.value;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Node<Item> oldLast = last;
        last = oldLast.previous;
        if (last != null) {
            last.next = null;
        }
        size--;
        return oldLast.value;
    }

    public Iterator<Item> iterator() {

        return new DqIterator();
    }

    public static void main(String[] args) {
    }

    private class Node<Item> {
        Item value;
        Node<Item> previous;
        Node<Item> next;

        public Node(Item value, Node previous, Node next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }

    private class DqIterator implements Iterator<Item> {
        private Node<Item> currentNode = first;

        @Override
        public boolean hasNext() {
            return currentNode != null && currentNode.next != null;
        }

        @Override
        public Item next() {
            final Item value = currentNode.value;
            currentNode = currentNode.next;
            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}