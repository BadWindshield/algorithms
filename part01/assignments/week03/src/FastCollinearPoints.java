// To compile this code,
// $ javac-algs4 FastCollinearPoints.java

// To run the Checkstyle tool,
// $ checkstyle-algs4 FastCollinearPoints.java

// To run the PMD tool,
// $ pmd-algs4 FastCollinearPoints.java

// To run the unit tests,
// $ java-algs4 FastCollinearPoints ../test/input8.txt


import java.util.Arrays;
import java.util.ArrayList;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;


public class FastCollinearPoints {
    private final ArrayList<LineSegment> segments = new ArrayList<LineSegment>();


    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException();
        }

        // Check for collinear points.
        final int n = points.length;
        Point[] ptArray = new Point[n];
        for (int k = 0; k < n; ++k) {
            if (points[k] == null) {
                throw new IllegalArgumentException();
            }
            ptArray[k] = points[k];
        }

        // Sort the points so that we can determine the end points
        // of a line segment easily.
        Arrays.sort(ptArray);

        // Check if two points are identical.
        for (int i = 0; i < n-1; ++i) {
            if (ptArray[i].compareTo(ptArray[i+1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        for (int i = 0; i < n; ++i) {
            // Select point p as the origin.
            final Point p = ptArray[i];

            // Load the rest of the (N-i-1) points into the ptSlopeArray.
            final int m = n-1;
            Point[] ptSlopeArray = new Point[m];
            for (int j = 0, k = 0; j < n; j++) {
                if (j == i) {
                    // skip this point
                    continue;
                }
                else {
                    ptSlopeArray[k++] = ptArray[j];
                }
            }
 
            // Sort the slopes of all points with respect to point p.
            Arrays.sort(ptSlopeArray, p.slopeOrder());

            // Look for consecutive slopes that have the same value
            // in the M x 1 array.
            int kStart = 0;
            int kStop = kStart + 1;

            // StdOut.println("i = " + i + "; ptArray");
            // for (Point pt : ptArray) {
            //     StdOut.println( pt );
            // }
            // StdOut.println("ptSlopeArray");
            // for (Point pt : ptSlopeArray) {
            //     StdOut.println( pt );
            // }

            while (kStop <= m-1) {
                while (kStop <= m-1 && 
                       almostEqual(p.slopeTo(ptSlopeArray[kStart]), p.slopeTo(ptSlopeArray[kStop]), 1e-9)) {
                    // Advance to past the end of the block.
                    // StdOut.println( "abc: i = " + i + "; N = " + N + "; M = " + M 
                    //                 + "; k_start = " + k_start + "; k_stop = " + k_stop );
                    kStop++;
                }

                // slope are the same between k_start and k_stop-1.
                final Point q = ptSlopeArray[kStart];
                if (kStop - kStart >= 3 &&
                    p.compareTo(q) < 0) { // don't assume compareTo() returns -1!
                    // String s_out = p.toString();
                    // for (int jj=k_start; jj<k_stop; ++jj) {
                    //     s_out += " -> " + ptSlopeArray[jj].toString();
                    // }
                    // StdOut.println( s_out );

                    // Add a line segment.
                    segments.add(new LineSegment(p, ptSlopeArray[kStop-1]));
                }
                kStart = kStop;
                kStop = kStart + 1;
            }
        }

    }

    private static boolean almostEqual(double a, double b, double eps) {
        return Math.abs(a-b) < eps;
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[ segments.size() ]);
    }


    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        // display to screen all at once
        StdDraw.show();

    }

}

