import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("must provide permutation size");
        }

        final Integer size = Integer.valueOf(args[0]);

        RandomizedQueue<String> items = new RandomizedQueue<>();
        final String[] input = StdIn.readAllStrings();
        for (String s : input) {
            items.enqueue(s);
        }

        for (int i = 0; i < size; i++) {
            StdOut.println(items.dequeue());
        }
    }
}
