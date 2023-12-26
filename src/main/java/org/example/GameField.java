package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.abs;

public class GameField extends JPanel implements ActionListener, KeyListener, Runnable {
    Block head = new Block();
    private boolean isAlive = true;
    private int startVelocity = 25;
    private int xVelocity = startVelocity;
    private int yVelocity = 0;
    public int blockSize = 25;
    Apple apple;
    public static ArrayList<Block> body = new ArrayList<>();
    private boolean lastTextPainted = false;

    GameField() {
        head.prevX = head.x+25;
        head.prevY = head.y+25;
        body.add(head);
        apple = new Apple();
        this.setBackground(Color.LIGHT_GRAY);
        head.setBounds(head.x, head.y, head.width, head.height);
        this.setFocusable(true);
        this.addKeyListener(this);
        Timer timer = new Timer(100, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        //border
        g2D.setColor(new Color(204,204,0));
        for (int i = 0; i<24; i++){
            g2D.fillRect(i*25, 0, 25, 25);
            g2D.fillRect(0, i*25, 25, 25);

            g2D.fillRect(i*25, 575, 25, 25);
            g2D.fillRect(575, i*25, 25, 25);
        }

        g2D.setColor(new Color(102,102,0));
        for (int i = 0; i<24; i++){
            g2D.drawRect(i*25, 0, 22, 22);
            g2D.drawRect(0, i*25, 22, 22);

            g2D.drawRect(i*25, 575, 22, 22);
            g2D.drawRect(575, i*25, 22, 22);
        }

        //apple
        g2D.setColor(new Color(0, 153, 0));
        g2D.drawImage(Apple.appleImg, apple.x*25, apple.y*25, null);

        //body
        g2D.setColor(new Color(0, 153, 0));

        for (int i = 1; i<body.size(); i++) {

            body.get(i).prevX = body.get(i).x;
            body.get(i).prevY = body.get(i).y;

            body.get(i).x = body.get(i-1).prevX;
            body.get(i).y = body.get(i-1).prevY;

            g2D.fillRect(body.get(i).x, body.get(i).y, body.get(i).width, body.get(i).height);
            g2D.setColor(new Color(51,102,0));
            g2D.drawRect(body.get(i).x+2, body.get(i).y+2, body.get(i).width-4, body.get(i).height-4);
            g2D.setColor(new Color(0, 153, 0));
        }

        //head
        g2D.fillRect(head.x, head.y, head.width, head.height);
        g2D.setColor(new Color(51,102,0));
        g2D.drawRect(head.x+2, head.y+2, head.width-4, head.height-4);
        g2D.setColor(Color.red);
        g2D.fillOval(head.x+2, head.y+2, head.width-5, head.height-5);
        head.prevX = head.x;
        head.prevY = head.y;

        //scoring
        g2D.setFont(new Font("Calibri", Font.BOLD, 25));
        if (!isAlive){
            g2D.setFont(new Font("Calibri", Font.BOLD, 50));
            g2D.setColor(new Color(153, 0, 0));
            g2D.drawString("You lose with score "+(body.size()-1) + "!", UI.FIELD_WIDTH/2-225, UI.FIELD_HEIGHT/2);
            lastTextPainted = true;
        }
        else{
            g2D.setColor(Color.BLACK);
            g2D.drawString("Score: "+(body.size()-1), 10, 20);
        }
        //apple
        g2D.drawImage(Apple.appleImg, apple.x*25, apple.y*25, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (outOfField(head.x, head.y)) isAlive = false;
        if (abs(head.x - apple.x*25) < blockSize && abs(head.y - apple.y*25) < blockSize) {
            body.add(new Block());
            apple = new Apple();
        }

        for (int i = 1; i<body.size()-1; i++)
            if (collision(head, body.get(i))) isAlive = false;

        if (isAlive) {
            head.x += xVelocity;
            head.y += yVelocity;
        }
        if (!lastTextPainted)
            repaint();
    }

    private boolean outOfField(int x, int y){
        return (x <= 0 || x+25 >= UI.FIELD_WIDTH || y <= 0 || y+25 >= UI.FIELD_HEIGHT);
    }

    public static boolean collision(Block b1, Block b2){
        return (abs(b1.x - b2.x) < 25 && abs(b1.y - b2.y) < 25);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        boolean upRotate = (body.size()!=1 && abs(head.y-(body.get(1).y+25)) < 25);
        boolean downRotate = (body.size()!=1 && abs(head.y-(body.get(1).y-25)) < 25);
        boolean leftRotate = (body.size()!=1 && abs(head.x-(body.get(1).x+25)) < 25);
        boolean rightRotate = (body.size()!=1 && abs(head.x-(body.get(1).x-25)) < 25);
        if ((keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) && !upRotate) {
            xVelocity = 0;
            yVelocity = -blockSize;
        } else if ((keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) && !downRotate) {
            xVelocity = 0;
            yVelocity = blockSize;
        } else if ((keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) && !leftRotate) {
            xVelocity = -blockSize;
            yVelocity = 0;
        } else if ((keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) && !rightRotate) {
            xVelocity = blockSize;
            yVelocity = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void run() {
    }
}