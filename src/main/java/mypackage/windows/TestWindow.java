package mypackage.windows;

import mypackage.primitives.Bezier;
import mypackage.canvas.LibBezierCanvas;
import mypackage.canvas.MyBezierCanvas;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestWindow {

    private Frame mainFrame;
    private Panel controlPanel1;
    private Panel controlPanel2;
    Label messageForInput;
    TextField countInput;
    TextField timeOut1;
    TextField timeOut2;
    Button startTestButton;
    private MyBezierCanvas myBezierCanvas;
    private LibBezierCanvas libBezierCanvas;

    int windowHeight = 680;
    int windowWidth = 1374;

    int canvasWidth = windowWidth / 2 - 2;
    int canvasHeight = windowHeight - 100;

    public TestWindow(){
        prepareGUI();
    }

    private void prepareGUI(){
        mainFrame = new Frame("Bezier Drawer Test");
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

        Panel row = new Panel(new GridLayout(1, 3));
        messageForInput = new Label("Введите число кривых Безье: ");
        countInput = new TextField(10);
        countInput.setText("1");
        startTestButton = new Button("Начать тестирование");
        startTestButton.addActionListener(e -> startComparison());
        startTestButton.setSize(windowWidth, 20);
        row.setSize(windowWidth, 30);
        row.add(messageForInput);
        row.add(countInput);
        row.add(startTestButton);

        mainFrame.add(row, constraints);

        Panel rowBot = new Panel(new GridLayout(2, 2));
        timeOut1 = new TextField(20);
        timeOut2 = new TextField(20);
        timeOut1.setEditable(false);
        timeOut1.setText("0");
        timeOut2.setEditable(false);
        timeOut2.setText("0");
        rowBot.add(new Label("Время работы реализованного алгоритма (мс):"));
        rowBot.add(new Label("Время работы библиотечного алгоритма  (мс):"));
        rowBot.setSize(windowWidth, 60);
        rowBot.add(timeOut1);
        rowBot.add(timeOut2);

        constraints.gridy = 1;
        mainFrame.add(rowBot, constraints);

        Panel cvHolder = new Panel(new GridLayout(1, 1));
        controlPanel1 = new Panel();
        controlPanel1.setLayout(new FlowLayout());
        controlPanel1.setSize(canvasWidth, canvasHeight);
        controlPanel2 = new Panel();
        controlPanel2.setLayout(new FlowLayout());
        controlPanel2.setSize(canvasWidth, canvasHeight);
        cvHolder.add(controlPanel1);
        cvHolder.add(controlPanel2);
        cvHolder.setSize(windowWidth, 600);

        constraints.gridy = 2;
        constraints.ipady = windowHeight - 150;
        mainFrame.add(cvHolder, constraints);
        mainFrame.setVisible(true);
    }

    private List<Bezier> generateRandom(int count) {
        Random r = new Random();
        r.setSeed(System.currentTimeMillis());

        List<Bezier> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(new Bezier(r, canvasWidth, canvasHeight));
        }
        return list;
    }


    private void startComparison() {
        int count;
        try {
            count = Integer.parseInt(countInput.getText());
        } catch (NumberFormatException e) {
            count = 1;
            countInput.setText("1");
        }
        if (count > 10000000) {
            count = 10000000;
            countInput.setText("1");
        }

        List<Bezier> bezierList = generateRandom(count);

        controlPanel1.removeAll();
        controlPanel2.removeAll();

        myBezierCanvas = new MyBezierCanvas(bezierList, canvasWidth, canvasHeight, timeOut1);
        libBezierCanvas = new LibBezierCanvas(bezierList, canvasWidth, canvasHeight, timeOut2);

        controlPanel1.add(myBezierCanvas);
        controlPanel2.add(libBezierCanvas);
    }

}
