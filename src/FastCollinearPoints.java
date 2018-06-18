import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
public class FastCollinearPoints {
    private int numberOfSegments;
    private ListNode last;
    private class ListNode {
	private LineSegment node;
	private ListNode pre;
    }
    public FastCollinearPoints(Point[] points) {
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
	Arrays.sort(copy);
	for (int i = 0; i < copy.length-1; i++) {
	    Point origin = copy[i];
	    int otherPointsNumber = 0;
	    Point[] otherPoints = new Point[copy.length-1];
	    for (int j = 0; j < copy.length; j++) {
		if (i != j)
		    otherPoints[otherPointsNumber++] = copy[j];
	    }
	    Arrays.sort(otherPoints, origin.slopeOrder());
	    int count = 0;
	    Point max = null;
	    Point min = null;
	    for (int j = 0; j <otherPoints.length - 1; j++) {
		if (Double.compare(origin.slopeTo(otherPoints[j]), origin.slopeTo(otherPoints[j+1])) == 0) {
		    count++;
		    if (min ==null && max == null) {
			if (origin.compareTo(otherPoints[j]) < 0) {
			    min = origin;
			    max = otherPoints[j];
			}
			else {
			    min = otherPoints[j];
			    max = origin;
			}
		    }
		    if (otherPoints[j+1].compareTo(min) < 0)
			min = otherPoints[j+1];
		    if (otherPoints[j+1].compareTo(max) > 0)
			max = otherPoints[j+1];
		    if (j == otherPoints.length-2 && count >= 2 && origin.compareTo(min) == 0) {
			addListNode(min,max);
			numberOfSegments++;
		    }
		}
		else {
		    if (count >= 2 && origin.compareTo(min) == 0) {
			addListNode(min, max);
			numberOfSegments++;
		    }
		    count = 0;
		    max = null;
		    min = null;
		}
	    }
	}
	
    }
    
    public int numberOfSegments() {
	return numberOfSegments;
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
    
    private void addListNode(Point a, Point b){
   	LineSegment ls = new LineSegment(a, b);
   	ListNode ln = new ListNode();
   	ln.node = ls;
   	ln.pre = last;
   	last = ln;
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
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}
