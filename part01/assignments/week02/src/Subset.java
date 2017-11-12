// Write a client program Subset.java that takes a command-line integer k;
// reads in a sequence of N strings from standard input using StdIn.readString();
// and prints out exactly k of them, uniformly at random. Each item from the sequence
// can be printed out at most once. You may assume that k ≥ 0 and no greater than the
// number of string N on standard input.
//
// % echo A B C D E F G H I | java Subset 3       % echo AA BB BB BB BB BB CC CC | java Subset 8
// C                                              BB
// G                                              AA
// A                                              BB
//                                                CC
// % echo A B C D E F G H I | java Subset 3       BB
// E                                              BB
// F                                              CC
// G                                              BB


import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Subset {

    public static void main(final String[] args) {
        // Read in the value of k.
        int k = 0;
        if (args.length >= 1) {
            k = Integer.parseInt(args[0]);
        } else {
            System.err.println("Invalid arguments");
            System.exit(1);
        }
        //StdOut.println("k = " + k);

        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            final String item = StdIn.readString();
            q.enqueue( item );
        }

        for (int i=0; i<k; ++i) {
            final String s = q.dequeue();
            StdOut.println(s);
        }
    }

}
