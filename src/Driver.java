/**
 * Created by Kendy on 2016-10-30.
 * Driver class to run the program
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.ActionListener;

public class Driver extends JFrame implements ActionListener {

    boardPanel boardGame;
    JLabel label;
    String about = "Othello - Reversi   Comp 472    Kendy Jeune, Mathieu Beauchemin";
    int response = 0;

    public Driver() {

        super("Othello - Reversi Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);

        JMenu file = new JMenu("File");
        menu.add(file);

        JMenuItem ngame = new JMenuItem("New Game");
        file.add(ngame);
        ngame.addActionListener(this);

        JMenuItem exit = new JMenuItem("Exit");
        file.add(exit);
        exit.addActionListener(this);

        JMenu help = new JMenu("Help");
        menu.add(help);

        JMenuItem about = new JMenuItem("About");
        help.add(about);
        about.addActionListener(this);

        setSize(500, 500);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("New Game")) {
            String options[] = new String[3];
            options[0] = "Easy";
            options[1] = "Medium";
            options[2] = "Difficult";
            response=JOptionPane.showOptionDialog(null,"New game","Choose Difficulty:",0,JOptionPane.QUESTION_MESSAGE, null, options,options[1]);
            boardGame = new boardPanel(70, 70, 40, 40, response);

            add(boardGame);
        }

        if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        }

        if (e.getActionCommand().equals("About")) {
            JOptionPane.showMessageDialog(this, about);


            setLayout(new FlowLayout());
        }
    }

    public static void main(String args[]) throws IOException {
        Driver m = new Driver();
    }
}
