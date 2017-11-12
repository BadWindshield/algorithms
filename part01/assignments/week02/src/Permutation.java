// To compile this code,
// $ javac-algs4 Permutation.java

// To run the Checkstyle tool,
// $ checkstyle-algs4 Permutation.java

// To run the PMD tool,
// $ pmd-algs4 Permutation.java

// Write a client program Permutation.java that takes a command-line integer k;
// reads in a sequence of N strings from standard input using StdIn.readString();
// and prints out exactly k of them, uniformly at random. Each item from the sequence
// can be printed out at most once. You may assume that k ≥ 0 and no greater than the
// number of string N on standard input.

// % echo A B C D E F G H I | java-algs4 Permutation 3     % echo AA BB BB BB BB BB CC CC | java-algs4 Permutation 8
// C                                                       BB
// G                                                       AA
// A                                                       BB
//                                                         CC
// % echo A B C D E F G H I | java-algs4 Permutation 3     BB
// E                                                       BB
// F                                                       CC
// G                                                       BB


import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Permutation {

    public static void main(final String[] args) {
        // Read in the value of k.
        int k = 0;
        if (args.length >= 1) {
            k = Integer.parseInt(args[0]);
        }
        else {
            System.err.println("Invalid arguments");
            throw new IllegalArgumentException();
        }

        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            final String item = StdIn.readString();
            q.enqueue(item);
        }

        for (int i = 0; i < k; ++i) {
            final String s = q.dequeue();
            StdOut.println(s);
        }
    }

}
