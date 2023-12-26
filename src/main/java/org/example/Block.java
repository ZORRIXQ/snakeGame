package org.example;

import java.awt.*;

public class Block extends Rectangle {
    public int width;
    public int height;
    public int x;
    public int y;
    public int prevX;
    public int prevY;

    Block(){
        width = height = 25;
        if (!GameField.body.isEmpty()){
            x = GameField.body.get(GameField.body.size() - 1).x + width;
            y = GameField.body.get(GameField.body.size() - 1).y + height;
        }
        else {
            x = UI.FIELD_WIDTH/2;
            y = UI.FIELD_HEIGHT/2;
        }

    }
}
