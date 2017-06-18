import java.awt.event.KeyEvent;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Client {
    public static void main(String[] args) {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();
        KdTree kdTree = new KdTree();
        PointSET brute = new PointSET();
        String filename = args[0];
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // initialize the two data structures with point from standard input
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdTree.insert(p);
            brute.insert(p);
        }
        StdDraw.clear();
        kdTree.draw();
        //brute.draw();
        StdDraw.show();

        int i = 0;
        while (true) {
            if (StdDraw.mousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) {
                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    kdTree.insert(p);
                    brute.insert(p);
                    StdDraw.clear();
                    kdTree.draw();
                    //brute.draw();
                    StdDraw.show();
                }
                if (++i == 10)
                    break;
            }
            StdDraw.pause(100);
        }
        while (true) {
            if (StdDraw.mousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) {
                    StdOut.printf("Nearest : %8.6f %8.6f\n", x, y);
                    StdDraw.clear();
                    kdTree.draw();
                    StdDraw.setPenColor(StdDraw.RED);
                    p.draw();
                    kdTree.nearest(p).draw();
                    StdDraw.show();
                }
            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE))
                break;
            StdDraw.pause(100);
        }
        StdOut.printf("Nearest End\n");

        double x0 = 0.0, y0 = 0.0; // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0; // current location of mouse
        boolean isDragging = false; // is the user dragging a rectangle
        while (true) {

            // user starts to drag a rectangle
            if (StdDraw.mousePressed() && !isDragging) {
                x0 = StdDraw.mouseX();
                y0 = StdDraw.mouseY();
                isDragging = true;
                continue;
            }

            // user is dragging a rectangle
            else if (StdDraw.mousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
                continue;
            }

            // mouse no longer pressed
            else if (!StdDraw.mousePressed() && isDragging) {
                isDragging = false;
            }

            RectHV rect1 = new RectHV(Math.min(x0, x1), Math.min(y0, y1), Math.max(x0, x1), Math.max(y0, y1));
            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            kdTree.draw();

            // draw the rectangle
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect1.draw();

            // draw the range search results for brute-force data structure in
            // red
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            for (Point2D p : kdTree.range(rect1))
                p.draw();
            StdDraw.show();
        }
    }
}
