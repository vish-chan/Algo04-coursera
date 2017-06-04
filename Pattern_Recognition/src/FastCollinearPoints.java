import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    LineSegment[] mLinesegments;

    public FastCollinearPoints(Point[] points) {
        // TODO Auto-generated constructor stub
        check(points);
        LineSegment[] mLinesegments = new LineSegment[points.length];
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Arrays.sort(points, p.slopeOrder());
            this.checkForCollinears(points, p);
        }
    }

    private void checkForCollinears(Point[] points, Point p) {
        Double[] slopes = new Double[points.length];
        for (int i = 0; i < points.length; i++) {
            slopes[i] = p.slopeTo(points[i]);
        } 
        int idx = 0;
        for (int i = 0; i < slopes.length;) {
            double cur = slopes[i];
            int  j = i+1;
            int count = 0;
            while (slopes[j++] == cur) {
                count++;
            }
            if (count >= 3)
                mLinesegments[idx++] = new LineSegment(p, points[j-1]);
            i = j;
        }
    }

    private void check(Point[] points) {
        if (points == null)
            throw new java.lang.NullPointerException();
        for (int i = 0; i < points.length; i++)
            if (points[i] == null)
                throw new java.lang.NullPointerException();
    }

}
