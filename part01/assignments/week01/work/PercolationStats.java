// To run,
// $ java-algs4 PercolationStats 200 100
// mean = 0.5920290000000001
// stddev = 0.00993328040457731
// 95% confidence interval = 0.590082077040703, 0.5939759229592972

// To hand in the homework,
// $ zip percolation.zip Percolation.java PercolationStats.java


import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;


public class PercolationStats {

    private final double mu;  // mean
    private final double sigma;  // std dev
    private final double confLo;
    private final double confHi;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int n, int t) {

        // Validate input arguments.
        if (n <= 0) {
            throw new IllegalArgumentException("Input N is out of bounds");
        } 
        if (t <= 0) {
            throw new IllegalArgumentException("Input T is out of bounds");
        } 

        // array to store the percolation threshold.
        double[] threshold = new double[t];

        for (int k = 0; k < t; ++k) {
            // At each experiment.
            Percolation per = new Percolation(n);

            int nOpenSite = 0;
            final int nSq = n * n;

            do {
                nOpenSite++;

                // Open a random site.
                int i = 0;
                int j = 0;
                do {
                    i = StdRandom.uniform(n) + 1;
                    j = StdRandom.uniform(n) + 1;
                } while (per.isOpen(i, j));
                per.open(i, j);

            } while (!per.percolates());

            threshold[k] = nOpenSite * 1.0 / nSq;

        }

        // Update stats.
        mu = StdStats.mean(threshold);
        sigma = StdStats.stddev(threshold);
        double tmp = 1.96*sigma/Math.sqrt(t);
        confLo = mu - tmp;
        confHi = mu + tmp;

    } 

    // sample mean of percolation threshold
    public double mean() {
        return mu;
    }                

    // sample standard deviation of percolation threshold
    public double stddev() {
        return sigma;
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return confLo;
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return confHi;
    }

    public static void main(final String[] args) {

        // Read in the values of N an T.
        int n = 0;
        int t = 0;
        if (args.length >= 2) {
            n = Integer.parseInt(args[0]);
            t = Integer.parseInt(args[1]);
        }
        else {
            System.err.println("Invalid arguments");
        }

        PercolationStats perStats = new PercolationStats(n, t);

        StdOut.println("mean = " + perStats.mean());
        StdOut.println("stddev = " + perStats.stddev());
        StdOut.println("95% confidence interval = " + perStats.confidenceLo() 
                       + ", " + perStats.confidenceHi());
    }
}
