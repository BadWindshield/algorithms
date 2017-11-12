// To compile this code,
// $ javac-algs4 RandomizedQueue.java

// To run the Checkstyle tool,
// $ checkstyle-algs4 RandomizedQueue.java

// To run the PMD tool,
// $ pmd-algs4 RandomizedQueue.java

// To run the unit tests,
// $ java-algs4 RandomizedQueue


import java.util.Iterator;
import java.util.NoSuchElementException;


import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


// When the queue has N items, first = 0, last = N.
// Items are stored at q[0] to q[N-1].
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;            // queue elements
    private int n = 0;           // number of elements on queue

    // cast needed since no generic array creation in Java
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
    }

    public boolean isEmpty() { return n == 0;    }
    public int size()        { return n;         }

    // resize the underlying array
    private void resize(int max) {
        assert max >= n;
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < n; i++) {
            temp[i] = q[i];
        }
        q = temp;
    }


    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        // double size of array if necessary and recopy to front of array
        if (n == q.length-1) {
            resize(2*q.length);
        }  // double size of array

        if (n == 0) {
            // queue is empty.
            q[n] = item;
        }
        else {
            // Generate a random number r from 0 to n.
            final int r = StdRandom.uniform(n+1);

            if (r < n) {
                // move the new item to the r-th position.
                Item itemSaved = q[r];
                q[r] = item;

                // move the r-th item to the n-th position.
                q[n] = itemSaved;
            }
            else {
                // simply append the new item.
                q[n] = item;
            }
        }

        n++;
    }

    // remove items from the end.
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        final Item item = q[n-1];
        q[n-1] = null;  // to avoid loitering
        n--;

        // shrink size of array if necessary
        if (n > 0 && n == q.length/4) {
            resize(q.length/2);
        } 
        return item;
    }

    // return (but do not delete) a random item.
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        // Generate a random number r from 0 to n-1.
        final int r = StdRandom.uniform(n);
        final Item item = q[r];
        return item;
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private int[] indices;  // randomized indices
 
        public ArrayIterator() {
            indices = new int[n];
            for (int k = 0; k < n; k++) {
                indices[k] = k;
            }
            StdRandom.shuffle(indices);
        }

        public boolean hasNext()  { return i < n;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = q[ indices[i] ];
            i++;
            return item;
        }
    }

   /**
     * A test client.
     */
    public static void main(final String[] args) {

        if (true) {
            RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
            final int n = 5;
            for (int i = 0; i < n; ++i) {
                q.enqueue(i);
            }

            Iterator<Integer> it = q.iterator(); 
            while (it.hasNext()) {
                final Integer d = it.next();
                StdOut.println(d);
            }

            Iterator<Integer> it2 = q.iterator(); 
            while (it2.hasNext()) {
                final Integer d = it2.next();
                StdOut.println(d);
            }
        }

        if (true) {
            RandomizedQueue<String> q = new RandomizedQueue<String>();
            final int n = 8;
            q.enqueue("AA");
            q.enqueue("BB");
            q.enqueue("CC");
            q.enqueue("DD");
            q.enqueue("EE");
            q.enqueue("FF");
            q.enqueue("GG");
            q.enqueue("HH");

            Iterator<String> it = q.iterator(); 
            while (it.hasNext()) {
                final String s = it.next();
                StdOut.println(s);
            }

            for (int i = 0; i < n; ++i) {
                final String s = q.dequeue();
                StdOut.println(s);
            }
        }

    }

}
