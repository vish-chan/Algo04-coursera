import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> mLinesegments;
    private ArrayList<Point> collinearMin, collinearMax;

    public FastCollinearPoints(Point[] points) {
        // TODO Auto-generated constructor stub
        check(points);
        collinearMin = new ArrayList<>();
        collinearMax = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Arrays.sort(points, p.slopeOrder());
            this.checkForCollinears(points, p);
        }
        mLinesegments = new ArrayList<>();
        for (int i = 0; i < collinearMin.size(); i++)
            mLinesegments.add(new LineSegment(collinearMin.get(i), collinearMax.get(i)));
        collinearMin.clear();
        collinearMax.clear();
    }

    private void checkForCollinears(Point[] points, Point p) {
        double[] slopes = new double[points.length];
        for (int i = 0; i < points.length; i++) {
            slopes[i] = p.slopeTo(points[i]);
        }
        assert isSorted(slopes);
        for (int i = 0; i < slopes.length;) {
            double cur = slopes[i];
            int j = i + 1;
            if (j >= slopes.length)
                break;
            Point maxPoint = points[i];
            Point minPoint = points[i];
            int count = 1;
            while (j < slopes.length && slopes[j] == cur) {
                count++;
                if (maxPoint.compareTo(points[j]) < 0)
                    maxPoint = points[j];
                if (minPoint.compareTo(points[j]) > 0)
                    minPoint = points[j];
                j++;
            }
            if (count >= 3) {
                if (maxPoint.compareTo(p) < 0)
                    maxPoint = p;
                if (minPoint.compareTo(p) > 0)
                    minPoint = p;
                if (!checkDup(minPoint, maxPoint)) {
                    collinearMin.add(minPoint);
                    collinearMax.add(maxPoint);
                }
            }
            i = j;
        }
    }

    private boolean checkDup(Point p1, Point p2) {
        for (int i = 0; i < collinearMin.size(); i++)
            if (collinearMin.get(i) == p1 && collinearMax.get(i) == p2)
                return true;
        return false;
    }

    private boolean isSorted(double[] a) {
        // TODO Auto-generated method stub
        for (int i = 0; i < a.length - 1; i++)
            if (a[i] > a[i + 1])
                return false;
        return true;
    }

    private void check(Point[] points) {
        if (points == null)
            throw new java.lang.NullPointerException();
        for (int i = 0; i < points.length - 1; i++)
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == null || points[j] == null)
                    throw new java.lang.NullPointerException();
                if (points[i].compareTo(points[j])==0)
                    throw new java.lang.IllegalArgumentException();
            }
    }

    public int numberOfSegments() {
        return mLinesegments.size();
    }

    public LineSegment[] segments() {
        return mLinesegments.toArray(new LineSegment[mLinesegments.size()]);
    }

}
