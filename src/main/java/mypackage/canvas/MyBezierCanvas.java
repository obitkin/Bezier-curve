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
                curve.setRGB((int) (point.getX()), (int) (point.getY()), Color.black.getRGB());
            }
            g2.drawImage(curve, null, null);
            if (timeOut == null) {
                drawPoint(bezier.getPoints()[0], g2);
                drawPoint(bezier.getPoints()[1], g2);
                drawPoint(bezier.getPoints()[2], g2);
                drawPoint(bezier.getPoints()[3], g2);
                g2.drawImage(curve, null, null);
                g2.setColor(Color.RED);
                g2.drawLine(
                        (int)Math.round(bezier.getX1()),
                        (int)Math.round(bezier.getY1()),
                        (int)Math.round(bezier.getCtrlx1()),
                        (int)Math.round(bezier.getCtrly1()));
//                g2.drawLine(
//                        (int)Math.round(bezier.getCtrlx1()),
//                        (int)Math.round(bezier.getCtrly1()),
//                        (int)Math.round(bezier.getCtrlx2()),
//                        (int)Math.round(bezier.getCtrly2()));
                g2.drawLine(
                        (int)Math.round(bezier.getCtrlx2()),
                        (int)Math.round(bezier.getCtrly2()),
                        (int)Math.round(bezier.getX2()),
                        (int)Math.round(bezier.getY2()));
            }
        }
        timeEstimated = System.currentTimeMillis() - timeEstimated;
        if (timeOut != null)
            timeOut.setText(String.valueOf(timeEstimated));
    }

    private void drawPoint(Point point, Graphics2D graphics2D) {
        int r = 10;
        int targetX = (int)point.getX() - r / 2;
        int targetY = (int)point.getY() - r / 2;
        // Paint the target (selector)
        graphics2D.setColor(Color.RED);
        graphics2D.fillOval(targetX, targetY, r, r);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawOval(targetX, targetY, r, r);
    }
}
