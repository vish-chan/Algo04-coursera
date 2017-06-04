
public class BruteCollinearPoints {

    LineSegment[] mLinesegments;

    public BruteCollinearPoints(Point[] points) {
        // TODO Auto-generated constructor stub
        check(points);
        this.mLinesegments = new LineSegment[points.length];
        double slope_1, slope_2, slope_3;
        int idx = 0;
        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                for (int k = j + 1; k < points.length; k++) {
                    slope_1 = points[i].slopeTo(points[j]);
                    slope_2 = points[i].slopeTo(points[k]);
                    if (slope_1 != slope_2)
                        continue;
                    for (int l = k + 1; l < points.length; l++) {
                        slope_3 = points[i].slopeTo(points[l]);
                        if (slope_3 == slope_1)
                            mLinesegments[idx++] = new LineSegment(points[i], points[l]);
                    }
                }
    }

    private void check(Point[] points) {
        if (points == null)
            throw new java.lang.NullPointerException();
        for (int i = 0; i < points.length; i++)
            if (points[i] == null)
                throw new java.lang.NullPointerException();
    }

    public int numberOfSegments() {
        return mLinesegments.length;
    }

    public LineSegment[] segments() {
        return this.mLinesegments;
    }

}
