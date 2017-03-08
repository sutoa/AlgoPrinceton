import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class Permutation {
    public static void main(String[] args){
        if(args.length != 1){
            throw new IllegalArgumentException("must provide permutation size");
        }

        final Integer size = Integer.valueOf(args[0]);

        RandomizedQueue<String> items = new RandomizedQueue<>();
        try{
            final String item = StdIn.readString();
            items.enqueue(item);
        } catch (NoSuchElementException e){

        }

        for (int i = 0; i < size; i++) {
            StdOut.println(items.dequeue());
        }
    }
}
