package mypackage.windows;

import mypackage.canvas.MyBezierCanvas;
import mypackage.primitives.Bezier;
import mypackage.primitives.Point;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class DrawerWindow {

    private Frame mainFrame;
    private Panel controlPanel1;
    private MyBezierCanvas myBezierCanvas;

    int windowHeight = 780;
    int windowWidth = 1374;

    int canvasWidth = windowWidth - 2;
    int canvasHeight = windowHeight - 100;

    Point[] points = new Point[4];

    public DrawerWindow(){
        points[0] = new Point(100., 650.);
        points[1] = new Point(585., 100);
        points[2] = new Point(1170., 100);
        points[3] = new Point(1270., 650.);
        prepareGUI();
    }

    private void prepareGUI(){
        mainFrame = new Frame("Bezier Drawer");
        mainFrame.setSize(windowWidth, windowHeight);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // По умолчанию натуральная высота, максимальная ширина
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.gridy   = 0  ;  // нулевая ячейка таблицы по вертикали
        constraints.gridx = 0;
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        controlPanel1 = new Panel();
        controlPanel1.setLayout(new FlowLayout());
        controlPanel1.setSize(canvasWidth, canvasHeight);

        myBezierCanvas = new MyBezierCanvas(List.of(new Bezier(points)), canvasWidth, canvasHeight, null);
        controlPanel1.add(myBezierCanvas);
        myBezierCanvas.addMouseListener(new MouseListenerMy());

        constraints.ipady = windowHeight - 150;
        mainFrame.add(controlPanel1, constraints);
        mainFrame.setVisible(true);
    }

    void rewrite() {
        controlPanel1.removeAll();
        myBezierCanvas = new MyBezierCanvas(List.of(new Bezier(points)), canvasWidth, canvasHeight, null);
        controlPanel1.add(myBezierCanvas);
        myBezierCanvas.addMouseListener(new MouseListenerMy());
    }

    class MouseListenerMy implements MouseListener {

        Point moved;

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            moved = whatPointMoved(points, mouseEvent);
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            if (moved != null) {
                moved.setX(mouseEvent.getX());
                moved.setY(mouseEvent.getY());
                rewrite();
                moved = null;
            }
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            if (moved != null) {
                moved.setX(mouseEvent.getX());
                moved.setY(mouseEvent.getY());
                rewrite();
                moved = null;
            }
        }

        private Point whatPointMoved(Point[] currentPoints, MouseEvent mouseEvent) {
            var near = Arrays.stream(currentPoints)
                    .filter(point -> dist(point, mouseEvent) < 10)
                    .collect(Collectors.toList());
            if (near.isEmpty()) {
                return null;
            }
            Point nearest = near.get(0);
            for (Point p : near) {
                if (dist(nearest, mouseEvent) > dist(p, mouseEvent)) {
                    nearest = p;
                }
            }
            return nearest;
        }

        double dist(Point p1, MouseEvent mouseEvent) {
            return sqrt(pow(p1.getX() - mouseEvent.getX(), 2) + pow(p1.getY() - mouseEvent.getY(), 2));
        }
    }


}


