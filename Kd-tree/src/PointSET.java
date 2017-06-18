import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> pointset;

    public PointSET() {
        pointset = new SET<>();
    }

    public boolean isEmpty() {
        return pointset.size() == 0;
    }

    public int size() {
        return pointset.size();
    }

    public void insert(Point2D p) {
        checkArg(p);
        pointset.add(p);
    }

    public boolean contains(Point2D p) {
        checkArg(p);
        return pointset.contains(p);
    }

    public void draw() {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        for (Point2D point2d : pointset) {
            point2d.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        checkArg(rect);
        Queue<Point2D> pointsQ = new Queue<>();
        for (Point2D point2d : pointset) {
            if (rect.contains(point2d))
                pointsQ.enqueue(point2d);
        }
        return pointsQ;
    }

    public Point2D nearest(Point2D p) {
        checkArg(p);
        if (isEmpty())
            return null;
        double minD = 0;
        Point2D nearest = null;
        for (Point2D point2d : pointset) {
            double d = p.distanceSquaredTo(point2d);
            if (nearest == null || d < minD) {
                minD = d;
                nearest = point2d;
            }
        }
        return nearest;
    }

    private void checkArg(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException();
    }

    private void checkArg(RectHV r) {
        if (r == null)
            throw new java.lang.NullPointerException();
    }
}