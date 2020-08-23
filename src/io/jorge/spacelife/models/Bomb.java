package io.jorge.spacelife.models;

import io.jorge.spacelife.constants.Constants;
import io.jorge.spacelife.images.Image;
import io.jorge.spacelife.images.ImageFactory;

import javax.swing.*;

public class Bomb extends Sprite {

    public Bomb() {}

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
        initialize();
    }

    private void initialize() {
        ImageIcon imageIcon = ImageFactory.createImage(Image.BOMB);
        setImage(imageIcon.getImage());
    }

    @Override
    public void move() {
        this.y++;
        if (y >= Constants.BOARD_HEIGHT - Constants.BOMB_HEIGHT) {
            die();
        }
    }
}
