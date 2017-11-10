// To compile this code,
// $ javac-algs4 Percolation.java

// To run the Checkstyle tool,
// $ checkstyle-algs4 Percolation.java

// To run the PMD tool,
// $ pmd-algs4 Percolation.java

// To run the unit tests,
// $ java-algs4 PercolationVisualizer ../test/percolation/input20.txt


import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;


import java.util.ArrayList;
import java.util.TreeMap;


public class WordNet {
    private final SAP sap;
    private final HashMap<Integer, String> id2synset;
    private final HashMap<String, Bag<Integer>> noun2ids;

   // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        id2synset = new HashMap<Integer, String>();
        noun2ids = new HashMap<String, Bag<Integer>>();

        readSynsets(synsets);
        readHypernyms(hypernyms);

        sap = new SAP(readHypernyms(hypernyms));
    }

    private void readSynsets(String synsetsFile) {
        In input = new In(synsetsFile);
        Bag<Integer> bag;

        while (input.hasNextLine()) {
            String[] tokens = input.readLine().split(",");
            int id = Integer.parseInt(tokens[0]);
            id2synset.put(id, tokens[1]);

            for (String noun : tokens[1].split(" ")) {
                bag = noun2ids.get(noun);
                
                if (bag == null) {
                    bag = new Bag<Integer>();
                    bag.add(id);
                    noun2ids.put(noun, bag);
                } else {
                    bag.add(id);
                }
            }
        }
    }


   // returns all WordNet nouns
   public Iterable<String> nouns()

   // is the word a WordNet noun?
   public boolean isNoun(String word)

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB)

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB)

   // do unit testing of this class
   public static void main(String[] args)
}

