import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private int numberOfSegments;
    private ListNode last;
    private class ListNode {
	private LineSegment node;
	private ListNode pre;
    }
    public BruteCollinearPoints(Point[] points) {
	if (points == null) {
	    throw new IllegalArgumentException();
	}
	Point[] copy = new Point[points.length];
	for (int i = 0; i < points.length; i++) {
	    if (i == 0){
		if (points[i] == null) {
		    throw new IllegalArgumentException();
		}
	    }
	    for (int j = i + 1; j < points.length; j++) {
		if (i == 0) {
		    if (points[j] == null)
			throw new IllegalArgumentException();
		}
		    
		if (points[i].compareTo(points[j]) == 0){
		    throw new IllegalArgumentException();
		}
	    }
	    copy[i] = points[i];
	}
	if (points.length < 4) {
	    return;
	}
	Arrays.sort(copy);
	for (int i = 0; i < copy.length; i++){
	    for (int j = i + 1; j < copy.length; j++) {
		for (int k = j + 1; k < copy.length; k++) {
		    for (int l = k + 1; l < copy.length; l++) {
			if (Double.compare(copy[i].slopeTo(copy[j]), copy[i].slopeTo(copy[k])) == 0 && Double.compare(copy[i].slopeTo(copy[k]), copy[i].slopeTo(copy[l]))==0) {
				numberOfSegments++;
				addListNode(copy[i], copy[l]);
			}
		    }
		}
	    }
	}
    }
    
    public int numberOfSegments() {
	return numberOfSegments;
    }
    private void addListNode(Point a, Point b){
	LineSegment ls = new LineSegment(a, b);
	ListNode ln = new ListNode();
	ln.node = ls;
	ln.pre = last;
	last = ln;
    }
    public LineSegment[] segments() {
	LineSegment[] s = new LineSegment[numberOfSegments];
	ListNode current = last;
	for (int i = 0; i < numberOfSegments; i++) {
	    s[i] = current.node;
	    current = current.pre;
	}
	return s;
    }
    
    public static void main(String[] args) {

	    // read the n points from a file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
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
	    StdDraw.show();
	}
}
