package mypackage.primitives;

import java.util.Random;

public class Bezier {

    public Bezier(Point[] points) {
        this.x1 = points[0].getX();
        this.y1 = points[0].getY();
        this.ctrlx1 = points[1].getX();
        this.ctrly1 = points[1].getY();
        this.ctrlx2 = points[2].getX();
        this.ctrly2 = points[2].getY();
        this.x2 = points[3].getX();
        this.y2 = points[3].getY();
    }

    public Bezier(Random r, int maxX, int maxY) {
        x1 = r.nextDouble() * maxX;
        y1 = r.nextDouble() * maxY;
        ctrlx1 = r.nextDouble() * maxX;
        ctrly1 = r.nextDouble() * maxY;
        ctrlx2 = r.nextDouble() * maxX;
        ctrly2 = r.nextDouble() * maxY;
        x2 = r.nextDouble() * maxX;
        y2 = r.nextDouble() * maxY;
    }

    public Bezier(double x1, double y1, double ctrlx1, double ctrly1, double ctrlx2, double ctrly2, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.ctrlx1 = ctrlx1;
        this.ctrly1 = ctrly1;
        this.ctrlx2 = ctrlx2;
        this.ctrly2 = ctrly2;
        this.x2 = x2;
        this.y2 = y2;
    }

    double x1;
    double y1;
    double ctrlx1;
    double ctrly1;
    double ctrlx2;
    double ctrly2;
    double x2;
    double y2;

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getCtrlx1() {
        return ctrlx1;
    }

    public double getCtrly1() {
        return ctrly1;
    }

    public double getCtrlx2() {
        return ctrlx2;
    }

    public double getCtrly2() {
        return ctrly2;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    public Point[] getPoints() {
        Point[] points = new Point[4];
        points[0] = new Point(x1, y1);
        points[1] = new Point(ctrlx1, ctrly1);
        points[2] = new Point(ctrlx2, ctrly2);
        points[3] = new Point(x2, y2);
        return points;
    }

    public double[] getValues() {
        return new double[] {x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2};
    }
}
