// To compile this code,
// $ javac-algs4 Board.java

// To run the Checkstyle tool,
// $ checkstyle-algs4 Board.java

// To run the PMD tool,
// $ pmd-algs4 Board.java

// To run the unit tests,
// $ java-algs4 Board ../test/puzzle04.txt


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;


public class Board {

    // The assignment states that the "program should work correctly for arbitrary
    // N-by-N boards (for any 2 <= N < 128)

    // The linear dimension N.
    private final int n;

    private int manhattan;  // Manhattan distance.

    private int hamming;  // Hamming distance.

    // the internal array to represent the board.
    // since a[0] is not used, the length is N*N + 1.
    private final int[][] tiles;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        n = blocks[0].length;
        tiles = new int[n][n];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                tiles[i][j] = blocks[i][j];
            }
        }

        // Compute the distances.
        hamming = privHamming();
        manhattan = privManhattan();
    }

    // number of blocks out of place
    private int privHamming() {
        int retVal = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                final int tileVal = tiles[i][j];
                if (tileVal != 0) {
                    final int corrVal = n*i + j + 1;
                    if (tileVal != corrVal) {
                        retVal++;
                    }
                }
            }
        }
        return retVal;
    }

    // sum of Manhattan distances between blocks and goal
    private int privManhattan() {
        int retVal = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                final int tileVal = tiles[i][j];
                if (tileVal != 0) {
                    final int corrI = (tileVal-1) / n;
                    final int corrJ = (tileVal-1) % n;
                    retVal += Math.abs(corrI - i);
                    retVal += Math.abs(corrJ - j);
                }
            }
        }
        return retVal;
    }

    // board dimension N
    public int dimension() { return n; }

    // number of blocks out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return (this.hamming() == 0);
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        Board tw = new Board(tiles);

        // For each row indexed by i, look for 2 consecutive tiles.
        outerloop:
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n-1; ++j) {
                if (tw.tiles[i][j] != 0 && tw.tiles[i][j+1] != 0) {
                    // swap the two tiles.
                    final int tmp = tw.tiles[i][j];
                    tw.tiles[i][j] = tw.tiles[i][j+1];
                    tw.tiles[i][j+1] = tmp;
                    break outerloop;
                }
            }
        }

        // Update the distances.
        tw.hamming = tw.privHamming();
        tw.manhattan = tw.privManhattan();

        return tw;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board rhs = (Board) y;
        if (rhs.dimension() != this.dimension()) {
            return false;
        }

        // Check the individual tiles.
        boolean bRetVal = true;
        outerloop:
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (this.tiles[i][j] != rhs.tiles[i][j]) {
                    bRetVal = false;
                    break outerloop;
                }
            }
        }
        return bRetVal;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<Board>();

        // Find the empty tile.
        int i0 = -1;  // location of the empty tile.
        int j0 = -1;  // location of the empty tile.
        outerloop:
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (tiles[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                    break outerloop;
                }
            }
        }

        // Try to swap the empty tile with a neighboring tile.
        // Top neighbor.
        if (i0 >= 1) {
            Board neighbor = new Board(tiles);
            int tmp = neighbor.tiles[i0][j0];
            neighbor.tiles[i0][j0] = neighbor.tiles[i0-1][j0];
            neighbor.tiles[i0-1][j0] = tmp;

            // Update the distances.
            neighbor.hamming = neighbor.privHamming();
            neighbor.manhattan = neighbor.privManhattan();

            q.enqueue(neighbor);
        }

        // Bottom neighbor.
        if (i0 <= n-2) {  // i0 goes from 0 to N-1
            Board neighbor = new Board(tiles);
            int tmp = neighbor.tiles[i0][j0];
            neighbor.tiles[i0][j0] = neighbor.tiles[i0+1][j0];
            neighbor.tiles[i0+1][j0] = tmp;

            // Update the distances.
            neighbor.hamming = neighbor.privHamming();
            neighbor.manhattan = neighbor.privManhattan();

            q.enqueue(neighbor);
        }

        // Right neighbor.
        if (j0 <= n-2) {  // j0 goes from 0 to N-1
            Board neighbor = new Board(tiles);
            int tmp = neighbor.tiles[i0][j0];
            neighbor.tiles[i0][j0] = neighbor.tiles[i0][j0+1];
            neighbor.tiles[i0][j0+1] = tmp;

            // Update the distances.
            neighbor.hamming = neighbor.privHamming();
            neighbor.manhattan = neighbor.privManhattan();

            q.enqueue(neighbor);
        }

        // Left neighbor.
        if (j0 >= 1) {
            Board neighbor = new Board(tiles);
            int tmp = neighbor.tiles[i0][j0];
            neighbor.tiles[i0][j0] = neighbor.tiles[i0][j0-1];
            neighbor.tiles[i0][j0-1] = tmp;

            // Update the distances.
            neighbor.hamming = neighbor.privHamming();
            neighbor.manhattan = neighbor.privManhattan();

            q.enqueue(neighbor);
        }

        return q;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit test
    // Use command line "java Board ../8puzzle/puzzle04.txt", for example, to run.
    public static void main(final String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        final int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        StdOut.println(initial);
        StdOut.println("Hamming distance = " + initial.hamming());
        StdOut.println("Manhattan distance = " + initial.manhattan());
        StdOut.println("isGoal = " + initial.isGoal());
        StdOut.println("twin = \n" + initial.twin());

        StdOut.println("neighbors = \n");
        for (Board neighbor : initial.neighbors()) {
            StdOut.println(neighbor);
        }
    }

}
