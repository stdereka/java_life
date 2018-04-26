import javax.swing.*;
import java.awt.*;


public class ButtonGrid extends JPanel{
    public LifeEngine lifeEngine;
    public JButton[][] grid;
    public ButtonGrid(){

        lifeEngine = new LifeEngine();
        setLayout(new GridLayout(23, 23));
        setBackground(Color.black);
        grid = new JButton[23][23];

        for(int y=0; y<23; y++){
            for(int x=0; x<23; x++){

                grid[x][y]=new JButton(lifeEngine.field[x+1][y+1]+"");
                grid[x][y].setForeground(Color.white);
                //grid[x][y].setBorderPainted(false);
                grid[x][y].setFocusPainted(false);
                grid[x][y].setBackground(Color.darkGray);
                final int x_ = x;
                final int y_ = y;

                grid[x][y].addActionListener(e -> {
                    if (grid[x_][y_].getText().equals("*")){
                        grid[x_][y_].setText(".");
                        lifeEngine.field[x_+1][y_+1] = '.';
                    }else{
                        grid[x_][y_].setText("*");
                        lifeEngine.field[x_+1][y_+1] = '*';
                    }
                });

                add(grid[x][y]);
            }
        }

        setVisible(true);
    }
}
