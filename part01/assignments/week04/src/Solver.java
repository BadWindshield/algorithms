// To compile this code,
// $ javac-algs4 Solver.java

// To run the Checkstyle tool,
// $ checkstyle-algs4 Solver.java

// To run the PMD tool,
// $ pmd-algs4 Solver.java

// To run the unit tests,
// $ java-algs4 Solver ../test/puzzle04.txt
// Minimum number of moves = 4
// 3
//  0  1  3 
//  4  2  5 
//  7  8  6 

// 3
//  1  0  3 
//  4  2  5 
//  7  8  6 

// 3
//  1  2  3 
//  4  0  5 
//  7  8  6 

// 3
//  1  2  3 
//  4  5  0 
//  7  8  6 

// 3
//  1  2  3 
//  4  5  6 
//  7  8  0 

// We need to ask for additional memory for more difficult puzzles.
// $ java-algs4 Solver -Xmx6400m ../test/puzzle48.txt

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;


public class Solver {

    // The stack containing the solution.
    private Stack<Board> stack;

    private class Node implements Comparable<Node> {
        private final Board board;
        private final int moves;
        private final Node prev;

        public Node(Board b, int moves, Node prev) {
            this.board = b;
            this.moves = moves;
            this.prev = prev;
        }

        Board getBoard() { return board; }

        int getMoves() { return moves; }

        Node getNodePrev() { return prev; }

        public int compareTo(Node rhs) {
            final int aManh = this.board.manhattan();
            final int bManh = rhs.board.manhattan();
            final int a = aManh + this.moves;
            final int b = bManh + rhs.moves;
            if (a < b) {
                return -1;
            }
            else if (a > b) {
                return 1;
            }
            else {
                // Break ties.
                if (aManh < bManh) {
                    return -1;
                }
                else if (aManh > bManh) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        }

    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<Node> pq = new MinPQ<Node>();
        MinPQ<Node> pqTwin = new MinPQ<Node>();

        pq.insert(new Node(initial, 0, null));
        pqTwin.insert(new Node(initial.twin(), 0, null));

        /////
        // Solve the 2 puzzles here.
        /////
        stack = new Stack<Board>();

        Node currNode = null;
        boolean bFound = false, bFoundTwin = false;
        do {
            // remove the search node with the minimum priority from the PQ.
            currNode = pq.delMin();
            Board currBoard = currNode.getBoard();
            Node prevNode = currNode.getNodePrev();

            Node currNodeTwin = pqTwin.delMin();
            Board currBoardTwin = currNodeTwin.getBoard();
            Node prevNodeTwin = currNodeTwin.getNodePrev();

            // insert all neighboring search nodes into the PQ.
            for (Board neighbor : currBoard.neighbors()) {
                if (prevNode != null && prevNode.getBoard().equals(neighbor)) {
                    // critical optimization.
                } else {
                    Node node = new Node(neighbor, currNode.getMoves()+1, currNode);
                    pq.insert(node);
                }
            }

            for (Board neighbor : currBoardTwin.neighbors()) {
                if (prevNodeTwin != null && prevNodeTwin.getBoard().equals(neighbor)) {
                    // critical optimization.
                } else {
                    Node node = new Node(neighbor, currNodeTwin.getMoves()+1, currNodeTwin);
                    pqTwin.insert(node);
                }
            }

            if (currNode.getBoard().isGoal()) {
                bFound = true;
            }
            if (currNodeTwin.getBoard().isGoal()) {
                bFoundTwin = true;
            }
        } while (!bFound && !bFoundTwin);

        if (bFound) { 
            // back track
            while (currNode != null) {
                stack.push(currNode.getBoard());
                currNode = currNode.getNodePrev();
            }
        }
        else { 
            // The twin was solved.
            stack = null;
        }

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return (stack != null);
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (stack == null) {
            return -1;
        }
        else {
            return stack.size() - 1;
        }
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        return stack;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        final Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        }
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}

