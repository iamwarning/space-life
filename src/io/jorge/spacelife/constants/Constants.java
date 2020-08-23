package io.jorge.spacelife.constants;

public class Constants {

    public static final double BOMB_DROPPING_PROBABILITY = 0.0005;
    public static final String GAME_OVER = "Game Over";
    public static final String WIN = "Cool";

    private Constants() {

    }

    public static final String TITLE_GAME = "Space Life";
    public static final int BOARD_WIDTH = 640;
    public static final int BOARD_HEIGHT = 750;
    public static final int BORDER_RIGHT = 50;
    public static final int BORDER_LEFT = 50;
    public static final int GROUND = 700;

    /* Game speed */
    public static final int GAME_SPEED = 10;
    public static final int SPACESHIP_WIDTH = 34;
    public static final int SPACESHIP_HEIGHT = 28;

    /* Speed of the laser */
    public static final int LASER_HORIZONTAL_TRANSLATION = 4;

    /* UFO */
    public static final int ENEMY_SHIP_HEIGHT = 24;
    public static final int ENEMY_SHIP_WIDTH = 32;
    public static final int ENEMY_SHIP_INIT_X = 275;
    public static final int ENEMY_SHIP_INIT_Y = 100;
    public static final int ENEMY_SHIP_ROW = 3;
    public static final int ENEMY_SHIP_COLUMNS = 8;

    /* Border padding */
    public static final int BORDER_PADDING = 50;
    public static final int GO_DOWN = 30;

    /* Bomb */
    public static final int BOMB_HEIGHT = 6;




    /* Images for objects */
    public static final String UFO_IMAGE_URL = "src/images/ufo.png";
    public static final String LASER_IMAGE_URL = "src/images/laser.png";
    public static final String BOMB_IMAGE_URL = "src/images/bomb.png";
    public static final String BACKGROUND_IMAGE_URL = "src/images/background.png";
    public static final String SPACESHIP_IMAGE_URL = "src/images/spaceship.png";
}
