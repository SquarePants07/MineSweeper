import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Anatoliy.Polyakov on 28.08.2019.
 */
public class MainWindow extends JFrame {

    GamePanel gamePanel;
    JMenuBar menu = new JMenuBar();
    JPanel stats = new JPanel();
    FacePanel facePanel;
    JLabel time;
    JLabel bombs;
    Thread timeCounter;
    int seconds = 0;
    OptionWindow optionWindow;
    TimeThread timeThread;

    public MainWindow(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        optionWindow = new OptionWindow(this);
        //setLayout(new GridLayout(3,1));
        createMenu();
        createStats();
        setJMenuBar(menu);
        gamePanel = new GamePanel(this);

        add(gamePanel, BorderLayout.CENTER);
        add(stats, BorderLayout.SOUTH);
        setResizable(false);
        setSize(gamePanel.mineField.fieldX*23,gamePanel.mineField.fieldY*23 + 60);

        facePanel = new FacePanel();
        add(facePanel, BorderLayout.NORTH);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                facePanel.state = 1;
                facePanel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                facePanel.state = 0;
                facePanel.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void openOptions() {
        optionWindow.setVisible(true);
    }

    public void newGame() {
        gamePanel.mineField = new MineField(Options.width,Options.height,Options.bombs);
        setSize(gamePanel.mineField.fieldX*23,gamePanel.mineField.fieldY*23 + 30);
        bombs.setText("Бомбы: " + Options.bombs);
        gamePanel.repaint();
    }

    private void openRecords() {

    }

    private void exit() {
        System.exit(0);
    }

    private void createStats() {
        time = new JLabel("Время: 0");
        stats.add(time);
        bombs = new JLabel("Бомбы: " + Options.bombs);
        stats.add(bombs);
    }

    private void createMenu() {
        menu.add(new JMenu("Игра"));
        menu.add(new JMenu("Справка"));
        JMenuItem newGame = new JMenuItem("Новая игра");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        menu.getMenu(0).add(newGame);

        JMenuItem options = new JMenuItem("Параметры");
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openOptions();
            }
        });

        menu.getMenu(0).add(options);

        JMenuItem records = new JMenuItem("Рекорды");
        records.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRecords();

            }
        });
        menu.getMenu(0).add(records);

        JMenuItem exit = new JMenuItem("Выход");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
        menu.getMenu(0).add(exit);
    }
}
