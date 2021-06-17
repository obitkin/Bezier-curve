package mypackage.windows;

import mypackage.canvas.LibBezierCanvas;
import mypackage.canvas.MyBezierCanvas;
import mypackage.primitives.Bezier;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawerWindow {

    private Frame mainFrame;
    private Panel controlPanel1;
    Label messageForInput;
    Button startTestButton;
    private MyBezierCanvas myBezierCanvas;

    TextField x1 = new TextField();
    TextField y1 = new TextField();
    TextField x2 = new TextField();
    TextField y2 = new TextField();
    TextField x3 = new TextField();
    TextField y3 = new TextField();
    TextField x4 = new TextField();
    TextField y4 = new TextField();

    int windowHeight = 680;
    int windowWidth = 1374;

    int canvasWidth = windowWidth - 2;
    int canvasHeight = windowHeight - 100;

    public DrawerWindow(){
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

        Panel row = new Panel(new GridLayout(1, 2));
        messageForInput = new Label("Введите параметры кривой Безье: 0<=x<=" + canvasWidth + "; 0<=y<=" + canvasHeight);
        startTestButton = new Button("Отрисовать кривую");
        startTestButton.addActionListener(e -> startDraw());
        startTestButton.setSize(windowWidth, 20);
        row.setSize(windowWidth, 30);
        row.add(messageForInput);
        row.add(startTestButton);

        mainFrame.add(row, constraints);

        Panel rowBot = new Panel(new GridLayout(2, 8));
        rowBot.setSize(windowWidth, 60);
        rowBot.add(new Label("x1"));
        rowBot.add(new Label("y1"));
        rowBot.add(new Label("x2"));
        rowBot.add(new Label("y2"));
        rowBot.add(new Label("x3"));
        rowBot.add(new Label("y3"));
        rowBot.add(new Label("x4"));
        rowBot.add(new Label("y4"));

        rowBot.add(x1);
        rowBot.add(y1);
        rowBot.add(x2);
        rowBot.add(y2);
        rowBot.add(x3);
        rowBot.add(y3);
        rowBot.add(x4);
        rowBot.add(y4);

        constraints.gridy = 1;
        mainFrame.add(rowBot, constraints);

        controlPanel1 = new Panel();
        controlPanel1.setLayout(new FlowLayout());
        controlPanel1.setSize(canvasWidth, canvasHeight);

        constraints.gridy = 2;
        constraints.ipady = windowHeight - 150;
        mainFrame.add(controlPanel1, constraints);
        mainFrame.setVisible(true);
    }



    private void startDraw() {
        int[] p = new int[8];

        try {
            p[0] = Integer.parseInt(x1.getText());
            p[1] = Integer.parseInt(y1.getText());
            p[2] = Integer.parseInt(x2.getText());
            p[3] = Integer.parseInt(y2.getText());
            p[4] = Integer.parseInt(x3.getText());
            p[5] = Integer.parseInt(y3.getText());
            p[6] = Integer.parseInt(x4.getText());
            p[7] = Integer.parseInt(y4.getText());

            for (int i = 0; i < p.length; i += 2) {
                if (p[i] > canvasWidth) {
                    throw new NumberFormatException();
                }
            }

            for (int i = 1; i < p.length; i += 2) {
                if (p[i] > canvasHeight) {
                    throw new NumberFormatException();
                }
            }


            controlPanel1.removeAll();
            List<Bezier> bezierList = new ArrayList<>();
            bezierList.add(new Bezier(p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7]));

            myBezierCanvas = new MyBezierCanvas(bezierList, canvasWidth, canvasHeight, null);
            controlPanel1.add(myBezierCanvas);
            messageForInput.setText("Введите параметры кривой Безье: 0<=x<=" + canvasWidth + "; 0<=y<=" + canvasHeight);

        } catch (NumberFormatException e) {
            messageForInput.setText("Некорректные значения, " + "0<=x<=" + canvasWidth + "; 0<=y<=" + canvasHeight);
        }
    }

}
