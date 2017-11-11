// To compile this code,
// $ javac-algs4 WordNet.java

// To run the Checkstyle tool,
// $ checkstyle-algs4 WordNet.java

// To run the PMD tool,
// $ pmd-algs4 WordNet.java

// To run the unit tests,
// $ java-algs4 xx


import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;


import java.util.HashMap;


public class WordNet {
    private final SAP sap;
    private final HashMap<Integer, String> id2synset;
    private final HashMap<String, Bag<Integer>> noun2ids;

    // The constructor takes the name of the two input files.

    // The file synsets.txt lists all the (noun) synsets in WordNet.
    // The first field is the synset id (an integer),
    // the second field is the synonym set (or synset),
    // and the third field is its dictionary definition (or gloss). For example, the line
    //     36,AND_circuit AND_gate,a circuit in a computer that fires only when all of its inputs fire  
    // means that the synset { AND_circuit, AND_gate } has an id number of 36 and
    // its gloss is a "circuit in a computer that fires only when all of its inputs fire".
    // The individual nouns that comprise a synset are separated by spaces (and a synset
    // element is not permitted to contain a space). The S synset ids are numbered 0 through S − 1;
    // the id numbers will appear consecutively in the synset file.

    // The file hypernyms.txt contains the hypernym relationships:
    // The first field is a synset id; subsequent fields are the id numbers of the synset's hypernyms.
    // For example, the following line
    //     164,21012,56099
    // means that the the synset 164 ("Actifed") has two hypernyms: 21012 ("antihistamine")
    // and 56099 ("nasal_decongestant"), representing that Actifed is both an antihistamine and a nasal decongestant. 

    public WordNet(String synsets, String hypernyms) {
        // < synset id, synonym set >.
        id2synset = new HashMap<Integer, String>();

        // < noun, bag< id > >.
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
                }
                else {
                    bag.add(id);
                }
            }
        }
    }

    private Digraph readHypernyms(String hypernymsFile) {
        Digraph digraph = new Digraph(id2synset.size());
        In input = new In(hypernymsFile);

        while (input.hasNextLine()) {
            String[] tokens = input.readLine().split(",");
            int id = Integer.parseInt(tokens[0]);
            for (int i = 1, sz = tokens.length; i < sz; i++) {
                digraph.addEdge(id, Integer.parseInt(tokens[i]));
            }
        }

        verifyCycle(digraph);
        verifyRoot(digraph);

        return digraph;
    }

    private void verifyCycle(Digraph digraph) {
        final DirectedCycle directedCycle = new DirectedCycle(digraph);
        
        if (directedCycle.hasCycle()) {
            throw new IllegalArgumentException();
        }
    }

    private void verifyRoot(Digraph digraph) {
        int roots = 0;

        for (int i = 0, sz = digraph.V(); i < sz; i++) {
            if (!digraph.adj(i).iterator().hasNext()) {
                roots += 1;
            }
        }

        if (roots != 1) {
            throw new IllegalArgumentException();
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return noun2ids.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return noun2ids.containsKey(word);
    }

    private void verifyNoun(String noun) {
        if (!isNoun(noun)) {
            throw new IllegalArgumentException();
        }
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        verifyNoun(nounA);
        verifyNoun(nounB);

        return sap.length(noun2ids.get(nounA), noun2ids.get(nounB));        
    }

    // A synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below).
    public String sap(String nounA, String nounB) {
        verifyNoun(nounA);
        verifyNoun(nounB);

        return id2synset.get(sap.ancestor(noun2ids.get(nounA), noun2ids.get(nounB)));        
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);

    }
}

