


import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;



// Shortest ancestral path. An ancestral path between two vertices v and w in a digraph is a directed path
// from v to a common ancestor x, together with a directed path from w to the same ancestor x. A shortest
// ancestral path is an ancestral path of minimum total length.
public class SAP {
    private Digraph diGraph;

    private class Result {
        private int length;
        private int ancestor;

        public Result(int lengthNew, int ancestorNew) {
            if (lengthNew == Integer.MAX_VALUE) {
                length = -1;
                ancestor = -1;
            }
            else {
                length = lengthNew;
                ancestor = ancestorNew;
            }
        }

        public int getLength() {
            return length;
        }

        public int getAncestor() {
            return ancestor;
        }
    }

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new NullPointerException();
        diGraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || v > diGraph.V() || w < 0 || w > diGraph.V()) {
            throw new IndexOutOfBoundsException();
        }

        final BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(diGraph, v);
        final BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(diGraph, w);
        final Result res = helper(bfs1, bfs2);
        return res.getLength();
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || v > diGraph.V() || w < 0 || w > diGraph.V()) {
            throw new IndexOutOfBoundsException();
        }

        final BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(diGraph, v);
        final BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(diGraph, w);
        final Result res = helper(bfs1, bfs2);
        return res.getAncestor();
    }

    private Result helper(BreadthFirstDirectedPaths bfs1, BreadthFirstDirectedPaths bfs2) {
        int length = Integer.MAX_VALUE, ancestor = 0;
        for (int i = 0, temp = 0; i < diGraph.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                temp = bfs1.distTo(i) + bfs2.distTo(i);
                if (temp < length) {
                    length = temp;
                    ancestor = i;
                }   
            }
        }
        return new Result(length, ancestor);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new NullPointerException();
        }

        final BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(diGraph, v);
        final BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(diGraph, w);
        final Result res = helper(bfs1, bfs2);
        return res.getLength();
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new NullPointerException();
        }

        final BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(diGraph, v);
        final BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(diGraph, w);
        final Result res = helper(bfs1, bfs2);
        return res.getAncestor();
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}

