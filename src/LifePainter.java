import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.concurrent.*;


public class LifePainter extends JFrame{
    public ScheduledExecutorService updateExecutor;
    public ButtonGrid buttonGrid;
    public Runnable oneIteration;
    public boolean isRunning;


    public LifePainter(){
        super("Life game");

        isRunning = false;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 5));

        buttonGrid = new ButtonGrid();
        mainPanel.add(buttonGrid);
        getContentPane().add(mainPanel);

        updateExecutor = Executors.newSingleThreadScheduledExecutor();

        oneIteration = () -> {
            buttonGrid.lifeEngine.update();
            refreshGrid();
        };

        JButton start = new JButton("start");
        start.setBackground(Color.darkGray);
        start.setForeground(Color.green);
        start.setFocusPainted(false);
        start.addActionListener(e -> {
            if (!isRunning) {
                isRunning = true;
                updateExecutor = Executors.newSingleThreadScheduledExecutor();
                updateExecutor.scheduleAtFixedRate(oneIteration, 0, 500, TimeUnit.MILLISECONDS);
            }
        });
        buttonsPanel.add(start);

        JButton stop = new JButton("stop");
        stop.setBackground(Color.darkGray);
        stop.setForeground(Color.red);
        stop.setFocusPainted(false);
        stop.addActionListener(e -> {
            isRunning = false;
            updateExecutor.shutdown();
        });
        buttonsPanel.add(stop);

        JButton reset = new JButton("reset");
        reset.setBackground(Color.darkGray);
        reset.setForeground(Color.blue);
        reset.setFocusPainted(false);
        reset.addActionListener(e -> {
            isRunning = false;
            resetGrid();
        });
        buttonsPanel.add(reset);

        JButton load = new JButton("load");
        load.setBackground(Color.darkGray);
        load.setForeground(Color.yellow);
        load.setFocusPainted(false);
        load.addActionListener(e -> load());
        buttonsPanel.add(load);

        JButton save = new JButton("save");
        save.setBackground(Color.darkGray);
        save.setForeground(Color.yellow);
        save.setFocusPainted(false);
        save.addActionListener(e -> save());
        buttonsPanel.add(save);

        mainPanel.add(buttonsPanel, BorderLayout.NORTH);
    }


    public void refreshGrid(){
        for (int i = 0; i < 23; i++){
            for (int j = 0; j < 23; j++){
                buttonGrid.grid[i][j].setText(buttonGrid.lifeEngine.field[i+1][j+1] + "");
            }
        }
    }


    public void resetGrid(){
        updateExecutor.shutdown();
        for (int i = 0; i < 23; i++){
            for (int j = 0; j < 23; j++){
                buttonGrid.grid[i][j].setText(".");
                buttonGrid.lifeEngine.field[i+1][j+1] = '.';
            }
        }
    }


    public void save(){
        try {
            FileOutputStream fos = new FileOutputStream("src/field");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(buttonGrid.lifeEngine.field);
            oos.close();
            refreshGrid();
        }catch (Exception e){
            System.out.println("File exception!");
        }
    }


    public void load(){
        try {
            FileInputStream fis = new FileInputStream("src/field");
            ObjectInputStream ois = new ObjectInputStream(fis);
            buttonGrid.lifeEngine.field = (char[][]) ois.readObject();
            ois.close();
            refreshGrid();
        }catch(Exception e){
            System.out.println("File exception!");
        }
    }


    public static void main(String[] args){
        JFrame frame = new LifePainter();
        frame.setPreferredSize(new Dimension(1000, 1000));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
