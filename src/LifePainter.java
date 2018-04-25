import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
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

        oneIteration = new Runnable(){
            @Override
            public void run() {
                buttonGrid.lifeEngine.update();
                refreshGrid();
            }
        };

        JButton start = new JButton("start");
        start.setBackground(Color.darkGray);
        start.setForeground(Color.green);
        start.setFocusPainted(false);
        start.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (!isRunning) {
                    isRunning = true;
                    updateExecutor = Executors.newSingleThreadScheduledExecutor();
                    updateExecutor.scheduleAtFixedRate(oneIteration, 0, 500, TimeUnit.MILLISECONDS);
                }
            }
        });
        buttonsPanel.add(start);

        JButton stop = new JButton("stop");
        stop.setBackground(Color.darkGray);
        stop.setForeground(Color.red);
        stop.setFocusPainted(false);
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                isRunning = false;
                updateExecutor.shutdown();
            }
        });
        buttonsPanel.add(stop);

        JButton reset = new JButton("reset");
        reset.setBackground(Color.darkGray);
        reset.setForeground(Color.blue);
        reset.setFocusPainted(false);
        reset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                isRunning = false;
                resetGrid();
            }
        });
        buttonsPanel.add(reset);

        JButton load = new JButton("load");
        load.setBackground(Color.darkGray);
        load.setForeground(Color.yellow);
        load.setFocusPainted(false);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileReader fr = new FileReader("/home/caladrius/IdeaProjects/Life/src/field");
                    for (int i = 0; i < 23; i++) {
                        for (int j = 0; j < 23; j++) {
                            char sym = (char) fr.read();
                            buttonGrid.grid[i][j].setText(sym + "");
                            buttonGrid.lifeEngine.field[i + 1][j + 1] = sym;
                        }
                        int nonce = fr.read();
                    }
                }catch(Exception e1){
                    System.out.println("File exception!");
                }
            }
        });
        buttonsPanel.add(load);

        JButton save = new JButton("save");
        save.setBackground(Color.darkGray);
        save.setForeground(Color.yellow);
        save.setFocusPainted(false);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileWriter fw = new FileWriter("/home/caladrius/IdeaProjects/Life/src/field");
                    for (int i = 0; i < 23; i++) {
                        for (int j = 0; j < 23; j++) {
                            char sym = buttonGrid.lifeEngine.field[i + 1][j + 1];
                            fw.write((int)sym);
                        }
                        fw.write((int)'\n');
                    }
                    fw.flush();
                    fw.close();
                }catch (Exception e1){
                    System.out.println("File exception!");
                }
            }
        });
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

    public static void main(String[] args){
        JFrame frame = new LifePainter();
        frame.setPreferredSize(new Dimension(1000, 1000));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
