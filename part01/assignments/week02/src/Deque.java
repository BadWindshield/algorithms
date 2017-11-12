// To compile this code,
// $ javac-algs4 Deque.java

// To run the Checkstyle tool,
// $ checkstyle-algs4 Deque.java

// To run the PMD tool,
// $ pmd-algs4 Deque.java

// To run the unit tests,
// $ java-algs4 Deque


import java.util.Iterator;
import java.util.NoSuchElementException;


import edu.princeton.cs.algs4.StdOut;


public class Deque<Item> implements Iterable<Item> {
    private int n;         // number of elements on queue
    private Node first;    // beginning of queue
    private Node last;     // end of queue

    // helper doubly-linked list class
    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

   /**
     * Create an empty queue.
     */
    public Deque() {
        first = null;
        last  = null;
        n = 0;
    }

   /**
     * Is the queue empty?
     */
    public boolean isEmpty() {
        return n == 0;
    }

   /**
     * Return the number of items in the queue.
     */
    public int size() {
        return n;     
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;
        if (isEmpty()) {
            // last is null.
            // oldfirst is null.
            last = first;
        }
        else {
            oldfirst.prev = first;
        }
        n++;
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (isEmpty()) {
            // first is null.
            // oldlast is null.
            first = last;
        }
        else { 
            oldlast.next = last;
        }
        n++;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        n--;
        if (isEmpty()) {
            // first is already null.
            last = null;
        }
        else {
            first.prev = null;
        }
        return item;                   // return the saved item
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;        // save item to return
        last = last.prev;            // delete first node
        n--;
        if (isEmpty()) {
            // last is already null.
            first = null;
        }
        else {
            last.next = null;
        }
        return item;                   // return the saved item
    }


   /**
     * Return an iterator that iterates over the items on the queue in FIFO order.
     */
    public Iterator<Item> iterator()  {
        return new ListIterator();  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }

    public static void main(final String[] args) {
        Deque<Integer> q = new Deque<Integer>();
        q.addFirst(1);
        q.addLast(2);
        q.addFirst(3);
        q.addLast(4);
        q.addLast(5);
        // q = {3, 1, 2, 4, 5}.

        Iterator<Integer> it = q.iterator(); 
        while (it.hasNext()) {
            Integer d = it.next();
            StdOut.println(d);
        }

        StdOut.println("removeFirst() = " + q.removeFirst());
        StdOut.println("removeLast() = " + q.removeLast());
    }
}
