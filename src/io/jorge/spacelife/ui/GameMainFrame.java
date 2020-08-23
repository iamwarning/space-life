package io.jorge.spacelife.ui;

import io.jorge.spacelife.constants.Constants;
import io.jorge.spacelife.images.Image;
import io.jorge.spacelife.images.ImageFactory;
import io.jorge.spacelife.sound.SoundFactory;

import javax.swing.*;

public class GameMainFrame extends JFrame {
    private SoundFactory soundFactory;
    public GameMainFrame() {
        initializeLayout();
    }

    private void initializeLayout() {
        soundFactory = new SoundFactory();
        add(new GamePanel());
        setTitle(Constants.TITLE_GAME);
        setIconImage(ImageFactory.createImage(Image.SPACESHIP).getImage());
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        soundFactory.spaceLife();
    }

}
