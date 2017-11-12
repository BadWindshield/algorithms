// To compile this code,
// $ javac-algs4 BruteCollinearPoints.java

// To run the Checkstyle tool,
// $ checkstyle-algs4 BruteCollinearPoints.java

// To run the PMD tool,
// $ pmd-algs4 BruteCollinearPoints.java

// To run the unit tests,
// $ java-algs4 BruteCollinearPoints ../test/input8.txt


import java.util.Arrays;
import java.util.ArrayList;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;


public class BruteCollinearPoints {
    private final ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

    // finds all line segments containing 4 or more points
    public BruteCollinearPoints(Point[] points) {

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
            final Point p = ptArray[i];

            for (int j = i+1; j < n; ++j) { // avoid duplicates, including j == 1.
                final Point q = ptArray[j];
                final double slope1 = p.slopeTo(q);

                for (int k = j+1; k < n; ++k) {
                    final Point r = ptArray[k];
                    final double slope2 = p.slopeTo(r);

                    if (almostEqual(slope1, slope2, 1e-9)) {
                        // We can check the 3rd slope now.
                        for (int m = k + 1; m < n; ++m) {
                            final Point s = ptArray[m];
                            final double slope3 = p.slopeTo(s);
                              
                            if (almostEqual(slope1, slope3, 1e-9)) {
                                // StdOut.println( p + " -> " + q + " -> " + r + " -> " + s );
                                segments.add(new LineSegment(p, s));
                            }
                        }
                    }
                }
            }
        }

    }

    private static boolean almostEqual(double a, double b, double eps) {
        boolean retVal = false;
        if (a == Double.POSITIVE_INFINITY && b == Double.POSITIVE_INFINITY) {
            retVal = true;
        }
        else if (a == Double.NEGATIVE_INFINITY && b == Double.NEGATIVE_INFINITY) {
            retVal = true;
        }
        else {
            retVal = Math.abs(a-b) < eps;
        }
        return retVal;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        // display to screen all at once
        StdDraw.show();

    }

}

