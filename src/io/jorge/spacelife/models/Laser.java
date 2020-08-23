package io.jorge.spacelife.models;

import io.jorge.spacelife.constants.Constants;
import io.jorge.spacelife.images.Image;
import io.jorge.spacelife.images.ImageFactory;

import javax.swing.*;

public class Laser extends Sprite {

    public Laser() {}

    public Laser(int x, int y) {
        this.x = x;
        this.y = y;
        initialize();
    }

    private void initialize() {
        ImageIcon imageIcon = ImageFactory.createImage(Image.LASER);
        setImage(imageIcon.getImage());

        setX(x + Constants.SPACESHIP_WIDTH);
        setY(y);
    }

    @Override
    public void move() {
        this.y -= Constants.LASER_HORIZONTAL_TRANSLATION;

        if (this.y < 0) {
            this.die();
        }
    }
}
