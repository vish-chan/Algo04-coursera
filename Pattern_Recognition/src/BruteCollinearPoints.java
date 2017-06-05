import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> mLinesegments;
    private ArrayList<Point> collinearMin, collinearMax;

    public BruteCollinearPoints(Point[] points) {
        // TODO Auto-generated constructor stub
        check(points);
        this.mLinesegments = new ArrayList<>();
        this.collinearMin = new ArrayList<>();
        this.collinearMax = new ArrayList<>();
        for (int i = 0; i < points.length - 3; i++) {
            Point p1 = points[i];
            for (int j = i + 1; j < points.length - 2; j++) {
                Point p2 = points[j];
                double slope12 = p1.slopeTo(p2);
                for (int k = j + 1; k < points.length - 1; k++) {
                    Point p3 = points[k];
                    double slope13 = p1.slopeTo(p3);
                    if (slope12 != slope13)
                        continue;
                    for (int l = k + 1; l < points.length; l++) {
                        Point p4 = points[l];
                        double slope14 = p1.slopeTo(p4);
                        if (slope14 == slope12) {
                            addLine(p1, p2, p3, p4);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < collinearMin.size(); i++)
            mLinesegments.add(new LineSegment(collinearMin.get(i), collinearMax.get(i)));
        collinearMin.clear();
        collinearMax.clear();
    }

    private void addLine(Point p1, Point p2, Point p3, Point p4) {
        Point[] lines = new Point[4];
        lines[0] = p1;
        lines[1] = p2;
        lines[2] = p3;
        lines[3] = p4;
        Arrays.sort(lines);
        Point min = lines[0];
        Point max = lines[3];
        for (int i = 0; i < collinearMin.size(); i++)
            if (collinearMin.get(i) == min && collinearMax.get(i) == max)
                return;
        collinearMin.add(min);
        collinearMax.add(max);
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
