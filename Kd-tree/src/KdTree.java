import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private static double sXMIN = 0.0, sYMIN = 0.0, sXMAX = 1.0, sYMAX = 1.0;

    private class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;

        public Node(Point2D theP, RectHV theRect) {
            this.p = theP;
            this.rect = theRect;
            this.lb = null;
            this.rt = null;
        }
    }

    private Node root;
    private int n;

    public KdTree() {
        // TODO Auto-generated constructor stub
        root = null;
        n = 0;
    }

    public boolean isEmpty() {
        return this.n == 0;
    }

    public int size() {
        return this.n;
    }

    public void insert(Point2D p) {
        checkArg(p);
        root = insert(root, p, sXMIN, sYMIN, sXMAX, sYMAX, 0);
    }

    private Node insert(Node x, Point2D p, double xmin, double ymin, double xmax, double ymax, int level) {
        if (x == null) {
            this.n++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }
        if (p.equals(x.p))
            return x;
        int cmp = cmp(p, x.p, level);
        if (level % 2 == 0) {
            if (cmp < 0)
                x.lb = insert(x.lb, p, xmin, ymin, x.p.x(), ymax, level + 1);
            else
                x.rt = insert(x.rt, p, x.p.x(), ymin, xmax, ymax, level + 1);
        } 
        else {
            if (cmp < 0)
                x.lb = insert(x.lb, p, xmin, ymin, xmax, x.p.y(), level + 1);
            else
                x.rt = insert(x.rt, p, xmin, x.p.y(), xmax, ymax, level + 1);
        }
        return x;
    }

    public boolean contains(Point2D p) {
        checkArg(p);
        Node cur = root;
        int level = 0;
        while (cur != null) {
            if (p.compareTo(cur.p) == 0)
                return true;
            int cmp = cmp(p, cur.p, level);
            if (cmp < 0)
                cur = cur.lb;
            else
                cur = cur.rt;
            level++;
        }
        return false;
    }

    public Iterable<Point2D> range(RectHV rect) {
        checkArg(rect);
        Queue<Point2D> pointsQ = new Queue<>();
        range(root, rect, pointsQ, 0);
        return pointsQ;
    }

    private void range(Node x, RectHV rect, Queue<Point2D> q, int level) {
        if (x == null)
            return;
        Point2D p = x.p;
        if (rect.contains(p)) {
            q.enqueue(p);
        }
        if (x.lb != null && rect.intersects(x.lb.rect))
            range(x.lb, rect, q, level + 1);
        if (x.rt != null && rect.intersects(x.rt.rect))
            range(x.rt, rect, q, level + 1);
    }

    public Point2D nearest(Point2D p) {
        if (isEmpty())
            return null;
        return nearest(root, p, root.p, 0);
    }

    private Point2D nearest(Node x, Point2D p, Point2D nearestP, int level) {
        if (x == null)
            return nearestP;
        double curDist = p.distanceSquaredTo(x.p);
        double minDist = p.distanceSquaredTo(nearestP);
        if (curDist < minDist)
            nearestP = x.p;
        int cmp = cmp(p, x.p, level);
        if (cmp < 0) {
            nearestP = nearest(x.lb, p, nearestP, level + 1);
            if (x.rt == null)
                return nearestP;
            RectHV rightRect = x.rt.rect;
            double leftmin = p.distanceSquaredTo(nearestP);
            double rightmin = rightRect.distanceSquaredTo(p);
            if (cmp(rightmin, leftmin) < 0)
                nearestP = nearest(x.rt, p, nearestP, level + 1);
        } 
        else {
            nearestP = nearest(x.rt, p, nearestP, level + 1);
            if (x.lb == null)
                return nearestP;
            RectHV leftRect = x.lb.rect;
            double rightmin = p.distanceSquaredTo(nearestP);
            double leftmin = leftRect.distanceSquaredTo(p);
            if (cmp(leftmin, rightmin) < 0)
                nearestP =  nearest(x.lb, p, nearestP, level + 1);
        }
        return nearestP;
    }

    public void draw() {
        StdDraw.clear();
        draw(root, 0);
    }

    private void draw(Node x, int level) {
        if (x == null)
            return;
        draw(x.lb, level + 1);
        drawline(x, level);
        draw(x.rt, level + 1);
    }

    private void drawline(Node x, int level) {
        StdDraw.setPenRadius();
        if (level % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        } 
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.p.draw();
    }

    private int cmp(Point2D p1, Point2D p2, int level) {
        double x1 = p1.x();
        double y1 = p1.y();
        double x2 = p2.x();
        double y2 = p2.y();
        if (level % 2 == 0)
            return cmp(x1, x2);
        else
            return cmp(y1, y2);
    }

    private int cmp(double d1, double d2) {
        if (d1 < d2)
            return -1;
        if (d1 > d2)
            return 1;
        return 0;
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
