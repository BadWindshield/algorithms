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


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;


public class Solver {

    // The stack containing the solution.
    private Stack<Board> stack;

    private class Node implements Comparable<Node> {
        final private Board board;
        final private int moves;
        final private Node prev;

        public Node(Board b, int moves, Node prev) {
            this.board = b;
            this.moves = moves;
            this.prev = prev;
        }

        Board getBoard() { return board; }

        int getMoves() { return moves; }

        Node getNodePrev() { return prev; }

        public int compareTo(Node rhs) {
            final int A_manh = this.board.manhattan();
            final int B_manh = rhs.board.manhattan();
            final int A = A_manh + this.moves;
            final int B = B_manh + rhs.moves;
            if (A < B) {
                return -1;
            } else if (A > B) {
                return 1;
            } else {
                // Break ties.
                if (A_manh < B_manh) { return -1; }
                else if (A_manh > B_manh) { return 1;}
                else { return 0; }
            }
        }

    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<Node> pq = new MinPQ<Node>();
        MinPQ<Node> pq_twin = new MinPQ<Node>();

        {
            Node node = new Node(initial, 0, null);
            pq.insert(node);

            Node node_twin = new Node(initial.twin(), 0, null);
            pq_twin.insert(node_twin);
        }

        //
        // Solve the 2 puzzles here.
        //
        stack = new Stack<Board>();

        Node curr_node = null;
        boolean bFound = false, bFound_twin = false;
        do {
            // remove the search node with the minimum priority from the PQ.
            curr_node = pq.delMin();
            Board curr_board = curr_node.getBoard();
            Node prev_node = curr_node.getNodePrev();

            Node curr_node_twin = pq_twin.delMin();
            Board curr_board_twin = curr_node_twin.getBoard();
            Node prev_node_twin = curr_node_twin.getNodePrev();

            // insert all neighboring search nodes into the PQ.
            for (Board neighbor : curr_board.neighbors()) {
                if ( prev_node != null && prev_node.getBoard().equals(neighbor) ) {
                    // critical optimization.
                } else {
                    Node node = new Node(neighbor, curr_node.getMoves()+1, curr_node);
                    pq.insert(node);
                }
            }

            for (Board neighbor : curr_board_twin.neighbors()) {
                if ( prev_node_twin != null && prev_node_twin.getBoard().equals(neighbor) ) {
                    // critical optimization.
                } else {
                    Node node = new Node(neighbor, curr_node_twin.getMoves()+1, curr_node_twin);
                    pq_twin.insert(node);
                }
            }

            if ( curr_node.getBoard().isGoal() ) { bFound = true; }
            if ( curr_node_twin.getBoard().isGoal() ) { bFound_twin = true; }
        } while ( !bFound && !bFound_twin );

        if ( bFound ) { 
            // back track
            while (curr_node != null) {
                stack.push( curr_node.getBoard() );
                curr_node = curr_node.getNodePrev();
            }
        } else { 
            // The twin was solved.
            stack = null;
        }

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        if (stack == null) {
            return false;
        }
        else {
            return true;
        }
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
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
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

