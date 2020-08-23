package io.jorge.spacelife.models;

import io.jorge.spacelife.images.Image;
import io.jorge.spacelife.images.ImageFactory;

import javax.swing.*;

public class EnemyShip extends Sprite {

    private boolean visible = true;

    @Override
    public void move() {

    }

    public EnemyShip() {
    }

    public EnemyShip(int x, int y) {
        this.x = x;
        this.y = y;
        initialize();
    }

    private void initialize() {
        ImageIcon imageIcon = ImageFactory.createImage(Image.UFO);
        setImage(imageIcon.getImage());
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


    public void move(int direction) {
        this.x += direction;
    }

}
