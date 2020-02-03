import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.lang.Thread.sleep;

/**
 * Created by Anatoliy.Polyakov on 28.08.2019.
 */
public class GamePanel extends JPanel {
    MineField mineField;
    GamePanel self;
    MainWindow parentWindow;
    boolean timer = false;
    TimeThread timeThread;

    public GamePanel(MainWindow parent){
        self = this;
        this.parentWindow = parent;
        mineField = new MineField(Options.width, Options.height, Options.bombs);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(!timer){
                    timeThread = new TimeThread(parentWindow);
                    timeThread.start();
                    timer = true;
                }
                if (mineField != null && !mineField.gameOver && !mineField.gameWon) {
                    int x = e.getX() / (getWidth() / mineField.fieldX);
                    int y = e.getY() / (getHeight() / mineField.fieldY);
                    if(mineField.firstClick){
                        mineField.generateField(x,y,Options.bombs);
                    }
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if (e.getClickCount() > 1 && (!mineField.field[x][y].hidden || !mineField.field[x][y].checked))
                            mineField.revealCellsAround(x, y);
                        else if (!mineField.field[x][y].checked)
                            mineField.revealCell(x, y);
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                        int n = mineField.checkCell(x, y);
                        parentWindow.bombs.setText("Бомбы: " + n);
                    }
                    repaint();
                    if (mineField.gameOver) {
                        for (int i = 0; i < mineField.fieldX; i++) {
                            for (int j = 0; j < mineField.fieldY; j++) {
                                mineField.revealCell(i,j);
                            }
                        }
                        timeThread.stop = true;
                        JOptionPane.showMessageDialog(self, "Проиграл");
                        timer = false;
                    }
                    if (mineField.gameWon) {
                        for (int i = 0; i < mineField.fieldX; i++) {
                            for (int j = 0; j < mineField.fieldY; j++) {
                                mineField.revealCell(i,j);
                            }
                        }
                        timeThread.stop = true;
                        JOptionPane.showMessageDialog(self, "Выиграл");
                        timer = false;
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void paint(Graphics g) {
        for (int i = 0; i < mineField.fieldX; i++) {
            for (int j = 0; j < mineField.fieldY; j++) {
                int x = getWidth()/mineField.fieldX * i;
                int y = getHeight()/mineField.fieldY * j;
                int width = getWidth()/mineField.fieldX;
                int height = getHeight()/mineField.fieldY;
                g.setColor(Color.BLACK);
                g.drawRect(x, y, width, height);
                g.setFont(new Font("TimesRoman", Font.BOLD, 16));
                if(mineField.field[i][j].hidden){
                    g.setColor(Color.LIGHT_GRAY);
                    g.fill3DRect(x+1, y-1, width-1, height+1, true);
                    if(mineField.field[i][j].checked){
                        g.setColor(Color.RED);
                        g.drawString("!", x+width/2, y+height/2 + 2);
                    }
                }
                else{
                    g.setColor(Color.WHITE);
                    g.fillRect(x+1, y+1, width-1, height-1);
                    if(!mineField.field[i][j].bomb){
                        switch (mineField.field[i][j].number) {
                            case 1:
                                g.setColor(Color.BLUE);
                                break;
                            case 2:
                                g.setColor(Color.GREEN);
                                break;
                            case 3:
                                g.setColor(Color.ORANGE);
                                break;
                            case 4:
                                g.setColor(Color.CYAN);
                                break;
                            case 5:
                                g.setColor(Color.MAGENTA);
                                break;
                            case 6:
                                g.setColor(Color.PINK);
                                break;
                            case 7:
                                g.setColor(Color.YELLOW);
                                break;
                            case 8:
                                g.setColor(Color.BLACK);
                                break;
                        }
                        g.drawString(mineField.field[i][j].number + "", x+width/2, y+height/2 + 4);
                    }
                    else{
                        if(mineField.field[i][j].fatal)
                            g.setColor(Color.RED);
                        else
                            g.setColor(Color.BLACK);
                        g.fillOval(x + width/4, y + width/4, width/2, height/2);
                    }
                }
            }
        }

    }
}
