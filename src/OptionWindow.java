import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Anatoliy.Polyakov on 28.08.2019.
 */
public class OptionWindow extends JFrame {
    ButtonGroup group = new ButtonGroup();
    JRadioButton novice = new JRadioButton("Новичок");
    JRadioButton amateur = new JRadioButton("Любитель");
    JRadioButton profi = new JRadioButton("Профи");
    JRadioButton special = new JRadioButton("Особый");

    JLabel widthLabel = new JLabel("Ширина: ");
    JLabel heightLabel = new JLabel("Высота: ");
    JLabel bombsLabel = new JLabel("Мины: ");

    JSpinner a = new JSpinner(new SpinnerNumberModel(9,9,30,1));
    JSpinner b = new JSpinner(new SpinnerNumberModel(9,9,24,1));
    JSpinner c = new JSpinner(new SpinnerNumberModel(10,10,668,1));

    JButton ok = new JButton("Ок");
    JButton cancel = new JButton("Отмена");

    MainWindow parentWindow;

    public OptionWindow(MainWindow parent){

        parentWindow = parent;

        group.add(novice);
        group.add(amateur);
        group.add(profi);
        group.add(special);

        setLayout(new FlowLayout(FlowLayout.LEADING));

        add(novice);
        add(amateur);
        add(profi);
        add(special);
        add(widthLabel);
        add(a);
        add(heightLabel);
        add(b);
        add(bombsLabel);
        add(c);

        add(ok);
        add(cancel);

        setSize(140,280);

        special.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a.setEnabled(true);
                b.setEnabled(true);
                c.setEnabled(true);
            }
        });

        novice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a.setEnabled(false);
                b.setEnabled(false);
                c.setEnabled(false);
            }
        });

        amateur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a.setEnabled(false);
                b.setEnabled(false);
                c.setEnabled(false);
            }
        });

        profi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a.setEnabled(false);
                b.setEnabled(false);
                c.setEnabled(false);
            }
        });

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveOptions();

            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        a.setEnabled(false);
        b.setEnabled(false);
        c.setEnabled(false);
        profi.setSelected(true);
    }

    private void saveOptions() {
        if(special.isSelected()) {
            Options.width = (int) a.getValue();
            Options.height = (int) b.getValue();
            Options.bombs = (int) c.getValue();
            Options.difficulty = 0;
        }
        if(novice.isSelected()) {
            Options.width = 9;
            Options.height = 9;
            Options.bombs = 10;
            Options.difficulty = 1;
        }
        if(amateur.isSelected()) {
            Options.width = 16;
            Options.height = 16;
            Options.bombs = 40;
            Options.difficulty = 2;
        }
        if(profi.isSelected()) {
            Options.width = 30;
            Options.height = 16;
            Options.bombs = 99;
            Options.difficulty = 3;
        }
        parentWindow.newGame();
        setVisible(false);
    }
}
