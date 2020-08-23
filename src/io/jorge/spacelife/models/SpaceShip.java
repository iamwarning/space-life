package io.jorge.spacelife.models;

import io.jorge.spacelife.constants.Constants;
import io.jorge.spacelife.images.Image;
import io.jorge.spacelife.images.ImageFactory;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class SpaceShip extends Sprite {


    public SpaceShip() {
        initialize();
    }

    private void initialize() {
        ImageIcon imageIcon = ImageFactory.createImage(Image.SPACESHIP);
        setImage(imageIcon.getImage());

        int startX = Constants.BOARD_WIDTH / 2 - Constants.SPACESHIP_WIDTH / 2;
        int startY = Constants.BOARD_HEIGHT - 100;
        setX(startX);
        setY(startY);
    }

    @Override
    public void move() {
        x += dx;
        if (x < Constants.SPACESHIP_WIDTH) {
            x = Constants.SPACESHIP_WIDTH;
        }

        if (x >= Constants.BOARD_WIDTH - 2 * Constants.SPACESHIP_WIDTH) {
            x = Constants.BOARD_WIDTH - 2 * Constants.SPACESHIP_WIDTH;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }
}
