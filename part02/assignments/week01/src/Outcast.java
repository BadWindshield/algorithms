


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
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {

    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns)

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
