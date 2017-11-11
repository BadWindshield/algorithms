// To compile this code,
// $ javac-algs4 Outcast.java

// To run the Checkstyle tool,
// $ checkstyle-algs4 Outcast.java

// To run the PMD tool,
// $ pmd-algs4 Outcast.java

// To run a unit test,
// $ java-algs4 Outcast ../test/synsets.txt ../test/hypernyms.txt ../test/outcast5.txt ../test/outcast8.txt ../test/outcast11.txt
// ../test/outcast5.txt: table
// ../test/outcast8.txt: bed
// ../test/outcast11.txt: potato


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


// % more outcast5.txt
// horse zebra cat bear table

// % more outcast8.txt
// water soda bed orange_juice milk apple_juice tea coffee

// % more outcast11.txt
// apple pear peach banana lime lemon blueberry strawberry mango watermelon potato


// % java Outcast synsets.txt hypernyms.txt outcast5.txt outcast8.txt outcast11.txt
// outcast5.txt: table
// outcast8.txt: bed
// outcast11.txt: potato


public class Outcast {

    private final WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int[][] distances = new int[nouns.length][nouns.length];

        for (int i = 0, sz = nouns.length; i < sz; i++) {
            for (int j = i + 1; j < sz; j++) {
                distances[i][j] = wordNet.distance(nouns[i], nouns[j]);
            }
        }

        return nouns[findMaximumDistance(distances)];
    }

    private int findMaximumDistance(int[][] distances) {
        int result = 0, maximumDistance = 0, sum = 0;

        for (int i = 0, sz = distances.length; i < sz; i++) {
            sum = 0;

            for (int j = 0, sz2 = distances[i].length; j < sz2; j++) {
                if (j < i) {
                    sum += distances[j][i];
                }
                else {
                    sum += distances[i][j];
                }

                if (i == 0 || sum > maximumDistance) {
                    maximumDistance = sum;
                    result = i;
                }
            }
        }

        return result;
    }

    // see test client below
    public static void main(String[] args) {
        final WordNet wordnet = new WordNet(args[0], args[1]);
        final Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

}
