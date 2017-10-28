// To compile this code,
// $ javac-algs4 Percolation.java


import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // data structure to represent the sites.
    // For an N x N grid, there will be N^2 + 2 internal sites.
    // The top & bottom virtual sites are the (N+1)-th and (N+2)-th
    // sites, respectively.

    // (N^2 + 2) x 1 array.
    private boolean[] siteOpen;
 
    private int n;

    // Address the percolation question; connect to both top & bottom virtual sites.
    private WeightedQuickUnionUF uf; 

    // Address the "fullness" question; connect to the top virtual site only.
    private WeightedQuickUnionUF ufTop;  

    private int indexTop, indexBottom;  // indices for the virtual top and bottom.

    private void checkCoor(int i, int j) {
        if (i <= 0 || i > n) { throw new IndexOutOfBoundsException("row index i out of bounds"); }
        if (j <= 0 || j > n) { throw new IndexOutOfBoundsException("column index j out of bounds"); }
    }

    // row i, column j.
    private int getIndex(int i, int j) {
        checkCoor(i, j);
        return (i-1)*n + (j-1);
    }

    // create N-by-N grid, with all sites blocked
    public Percolation(int nNew) {
        if (nNew <= 0) {
            throw new IllegalArgumentException("N <= 0 in constructor");
        }

        n = nNew;
    	final int nSq = n * n;
    	siteOpen = new boolean[ nSq+2 ];

        // Initialize sites to the blocked state.
        for (int k=0; k<nSq; ++k) {
            siteOpen[k] = false;
        }
        // The virtual sites remain open.
        siteOpen[nSq] = true;
        siteOpen[nSq+1] = true;

        // Connect the virtual sites to the grid.
        uf = new WeightedQuickUnionUF(nSq+2);
        ufTop = new WeightedQuickUnionUF(nSq+1);

        indexTop = nSq;
        indexBottom = nSq + 1;
        for (int j=1; j<=n; ++j) {
            // Connect the top row to the top site.
            int k = getIndex(1, j);
            uf.union(indexTop, k);
            ufTop.union(indexTop, k);

            // Connect the bottom row to the bottom site.
            k = getIndex(n, j);
            uf.union(indexBottom, k);
        }
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        final int q = getIndex(i, j);
        if ( !siteOpen[q] ) {
            // Need to open the site.
            siteOpen[q] = true;

            // Connect the site to the neighbors if they are open too.
            // Top
            if (i >= 2) {
                if (isOpen(i-1, j)) {
                    uf.union( q, getIndex(i-1, j) );
                    ufTop.union( q, getIndex(i-1, j) );
                }
            }

            // Left
            if (j >= 2) {
                if (isOpen(i, j-1)) {
                    uf.union( q, getIndex(i, j-1) );
                    ufTop.union( q, getIndex(i, j-1) );
                }
            }

            // Right
            if (j <= n-1) {
                if (isOpen(i, j+1)) {
                    uf.union( q, getIndex(i, j+1) );
                    ufTop.union( q, getIndex(i, j+1) );
                }
            }

            // Bottom
            if (i <= n-1) {
                if (isOpen(i+1, j)) {
                    uf.union( q, getIndex(i+1, j) );
                    ufTop.union( q, getIndex(i+1, j) );
                }
            }
        }
    }
       
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        final int q = getIndex(i, j);
        return siteOpen[q];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        // check if the site is connected to the top virtual site.
        final int q = getIndex(i, j);
        return (isOpen(i, j) && ufTop.connected(indexTop, q));
    }

    // number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (isOpen(i,j)) {
                    count++;
                }
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        // Check (1, 1) for the N=1 corner case.
        boolean bRetVal = false;
        if (n == 1) {
            bRetVal = isOpen(1, 1) && uf.connected(indexTop, indexBottom);
        } else {
            bRetVal = uf.connected(indexTop, indexBottom);
        }
        return bRetVal;
    }

    public static void main(final String[] args) {

        if (false) {
            final int N = 3;
            Percolation per = new Percolation(N);

            StdOut.println("isFull(1,1) = " + per.isFull(1,1));
            StdOut.println("isOpen(1,1) = " + per.isOpen(1,1));
            StdOut.println("open(1,1)"); per.open(1,1);
            StdOut.println("isOpen(1,1) = " + per.isOpen(1,1));
            StdOut.println("percolates() = " + per.percolates());

            StdOut.println("open(2,1)"); per.open(2,1);
            StdOut.println("percolates() = " + per.percolates());
            StdOut.println("open(3,1)"); per.open(3,1);
            StdOut.println("percolates() = " + per.percolates());

            // Check for the "backwash" bug.
            StdOut.println("open(3,3)"); per.open(3,3);
            StdOut.println("isFull(3,3) = " + per.isFull(3,3));

            // Check to see if an exception is thrown.
            StdOut.println("isOpen(0, 6) = " + per.isOpen(0,6));
        }

        // Test the N=1 case.
        if (true) {
            // Percolation perTest = new Percolation(-10);

            final int N = 1;
            Percolation per = new Percolation(N);

            StdOut.println("isFull(1,1) = " + per.isFull(1,1));
            StdOut.println("isOpen(1,1) = " + per.isOpen(1,1));
            StdOut.println("percolates() = " + per.percolates());

            StdOut.println("open(1,1)"); per.open(1,1);
            StdOut.println("isFull(1,1) = " + per.isFull(1,1));
            StdOut.println("isOpen(1,1) = " + per.isOpen(1,1));
            StdOut.println("percolates() = " + per.percolates());

        }

    }

}

