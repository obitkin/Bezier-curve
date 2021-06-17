package mypackage.primitives;

public class Point {

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double x;
    double y;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
