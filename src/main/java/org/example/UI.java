package org.example;
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;

public class UI extends JFrame {
    public static final int FIELD_WIDTH = 600;
    public static final int FIELD_HEIGHT = 600;
    JLabel scoring;

    UI() {
        this.setResizable(false);
        this.setBounds(0, 0, FIELD_WIDTH, FIELD_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("src/main/snake.png").getImage());

        scoring = new JLabel("Score: "+(GameField.body.size()-1), JLabel.LEFT);
        scoring.setBounds(0, 0, 200, 20);
        scoring.setFont(new Font("Calibri", Font.BOLD, 50));
        scoring.setOpaque(false);
        this.add(scoring);

        this.setTitle("Snake Game");
        GameField gameField = new GameField();
        gameField.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        gameField.requestFocus();
        this.add(gameField);

        this.pack();
        this.setVisible(true);
    }
}
