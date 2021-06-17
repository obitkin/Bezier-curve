package mypackage.canvas;

import mypackage.primitives.Bezier;
import mypackage.primitives.Point;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static java.lang.Math.pow;


public class MyBezierCanvas extends Canvas {

    private long timeEstimated;

    private TextField timeOut;

    private List<Bezier> bezierList;

    private List<Point> pointList;

    private BufferedImage curve;

    private int width;
    private int height;

    public MyBezierCanvas(List<Bezier> bezierList, int w, int h, TextField timeOut) {
        setBackground (Color.WHITE);
        setSize(w, h);
        this.bezierList = bezierList;
        this.timeOut = timeOut;
        this.width = w;
        this.height = h;
    }

    private Point getPoint(Bezier bezier, double t) {
        double x = getXOrY(bezier.getX1(), bezier.getCtrlx1(), bezier.getCtrlx2(), bezier.getX2(), t);
        double y = getXOrY(bezier.getY1(), bezier.getCtrly1(), bezier.getCtrly2(), bezier.getY2(), t);
        return new Point(x, y);
    }

    private double getXOrY(double v1, double v2, double v3, double v4, double t) {
        return (v4 * pow(t, 3)) +
                (3 * v3 * pow(t, 2) * (1 - t)) +
                (3 * v2 * t * pow(1-t, 2)) +
                (v1 * pow(1-t, 3));
    }


    public void paint(Graphics g) {
        timeEstimated = System.currentTimeMillis();
        Graphics2D g2;
        g2 = (Graphics2D) g;

        for (Bezier bezier: bezierList) {
            curve = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            for (double t = 0; t < 1; t += 50. / (width * height)) {
                Point point = getPoint(bezier, t);
                curve.setRGB((int) point.getX(), (int) point.getY(), Color.black.getRGB());
            }
            g2.drawImage(curve, null, null);
        }
        timeEstimated = System.currentTimeMillis() - timeEstimated;
        if (timeOut != null)
            timeOut.setText(String.valueOf(timeEstimated));
    }
}
