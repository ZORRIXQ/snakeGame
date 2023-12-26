package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static java.lang.Math.abs;

public class Apple extends Block{
    public int x;
    public int y;
    private Random random = new Random();
    public static Image appleImg = (new ImageIcon("apple.png")).getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
    Apple(){
        while (x == 0 || x == 23 || y == 0 || y == 23){
            x = random.nextInt(23);
            y = random.nextInt(23);
        }
    }
}
