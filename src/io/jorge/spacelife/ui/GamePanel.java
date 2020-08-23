package io.jorge.spacelife.ui;

import io.jorge.spacelife.callbacks.GameEventListener;
import io.jorge.spacelife.constants.Constants;
import io.jorge.spacelife.images.Image;
import io.jorge.spacelife.images.ImageFactory;
import io.jorge.spacelife.models.Bomb;
import io.jorge.spacelife.models.EnemyShip;
import io.jorge.spacelife.models.Laser;
import io.jorge.spacelife.models.SpaceShip;
import io.jorge.spacelife.sound.SoundFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel {
    private List<EnemyShip> enemyShips;
    private List<Bomb> bombs;
    private SpaceShip spaceShip;
    private Laser laser;
    private int direction = -1;
    private int deaths = 0;
    private boolean inGame = true;
    private String message;
    private Timer timer;
    private ImageIcon backgroundImage;
    private Random generator;
    private SoundFactory soundFactory;
    private int score;
    private int shields = 3;

    public GamePanel() {
        initializeVariables();
        initializeBoard();
        initializeGame();
    }

    private void initializeVariables() {
        this.soundFactory = new SoundFactory();
        this.backgroundImage = ImageFactory.createImage(Image.BACKGROUND);
        this.generator = new Random();
        this.enemyShips = new ArrayList<>();
        this.bombs = new ArrayList<>();
        this.spaceShip = new SpaceShip();
        this.laser = new Laser();
    }

    private void initializeBoard() {
        setPreferredSize(new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT));
        addKeyListener(new GameEventListener(this));
        setFocusable(true);
        timer = new Timer(Constants.GAME_SPEED, new GameLoop(this));
        timer.start();
    }

    private void initializeGame() {

        for (int i = 0; i < Constants.ENEMY_SHIP_ROW; i++) {
            for (int j = 0; j < Constants.ENEMY_SHIP_COLUMNS; j++) {
                EnemyShip enemyShip = new EnemyShip(Constants.ENEMY_SHIP_INIT_X + 50 * j,
                        Constants.ENEMY_SHIP_INIT_Y + 50 * i);
                enemyShips.add(enemyShip);
            }
        }
    }

    private void drawAliens(Graphics g) {

        for (EnemyShip enemyShip : enemyShips) {
            if (enemyShip.isVisible()) {
                g.drawImage(enemyShip.getImage(), enemyShip.getX(), enemyShip.getY(), this);
            }
        }
    }

    private void drawPlayer(Graphics g) {
        g.drawImage(spaceShip.getImage(), spaceShip.getX(), spaceShip.getY(), this);
    }

    private void drawShot(Graphics g) {

        if (!laser.isDead()) {
            g.drawImage(laser.getImage(), laser.getX(), laser.getY(), this);
        }
    }

    private void drawBombing(Graphics g) {

        for (Bomb b : this.bombs) {
            if (!b.isDead()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    // printing the value of the actual score + the number of remaining shields
    private void drawScoreAndShield(Graphics g) {

        if (!inGame)
            return;

        Font font = new Font("Helvetica", Font.BOLD, 20);
        g.setFont(font);
        g.setColor(Color.GRAY);
        g.drawString("Score: " + score, Constants.BOARD_WIDTH - 150, 50);
        g.drawString("Shields: " + shields, 50, 50);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw background image
        g.drawImage(backgroundImage.getImage(), 0, 0, null);
        // draw components
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        if (inGame) {
            drawScoreAndShield(g);
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
        } else {

            if (timer.isRunning()) {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {

        g.drawImage(backgroundImage.getImage(), 0, 0, null);

        Font font = new Font("Helvetica", Font.BOLD, 50);
        FontMetrics fontMetrics = this.getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(message, (Constants.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
                Constants.BOARD_HEIGHT / 2 - 100);
    }

    private void update() {

        // if the spaceship is dead then game over
        if (spaceShip.isDead()) {
            // spaceShip.die();
            inGame = false;
            message = Constants.GAME_OVER;
        }

        // if all the enemy ships are dead then game over
        if (deaths == enemyShips.size()) {
            inGame = false;
            message = Constants.WIN;
            this.soundFactory.missionComplete();
        }

        // player
        spaceShip.move();

        // if laser is on the screen then lets check for collisions
        if (!laser.isDead()) {

            int shotX = laser.getX();
            int shotY = laser.getY();

            // check for enemy-laser collisions
            for (EnemyShip alien : enemyShips) {

                int alienX = alien.getX();
                int alienY = alien.getY();

                // collision related constraints
                if (alien.isVisible() && !laser.isDead()) {
                    if (shotX >= (alienX) && shotX <= (alienX + Constants.ENEMY_SHIP_WIDTH) && shotY >= (alienY)
                            && shotY <= (alienY + Constants.ENEMY_SHIP_HEIGHT)) {
                        // result: both enemy ship and laser die
                        alien.setVisible(false);
                        deaths++;
                        score += 20;
                        soundFactory.explosion();
                        laser.die();
                    }
                }
            }

            // moving the laser beam
            laser.move();
        }

        // moving the enemy ships: maybe the most complex operation
        for (EnemyShip enemyShip : enemyShips) {

            // going down horizontally - on the right
            if (enemyShip.getX() >= Constants.BOARD_WIDTH - 2 * Constants.BORDER_RIGHT && direction != -1
                    || enemyShip.getX() <= Constants.BORDER_LEFT && direction != 1) {

                direction *= -1;

                Iterator<EnemyShip> ufoIterator = enemyShips.iterator();

                while (ufoIterator.hasNext()) {
                    EnemyShip ufo = ufoIterator.next();
                    ufo.setY(ufo.getY() + Constants.GO_DOWN);
                }
            }

            // checking whether the aliens approaches us or not
            // and moving the visible (not dead) enemy ships
            if (enemyShip.isVisible()) {

                // this is when there is a UFO-spaceship collision
                if (enemyShip.getY() > Constants.GROUND - Constants.ENEMY_SHIP_HEIGHT) {
                    enemyShip.die();
                    inGame = false;
                    message = Constants.GAME_OVER;
                    this.soundFactory.gameOver();
                }

                enemyShip.move(direction);
            }
        }

        // generating bombs
        for (EnemyShip enemy : enemyShips) {
            if (enemy.isVisible() && generator.nextDouble() < Constants.BOMB_DROPPING_PROBABILITY) {
                Bomb newBomb = new Bomb(enemy.getX(),enemy.getY());
                bombs.add(newBomb);
            }
        }

        // checking collision between bomb and spaceship
        for (Bomb bomb : bombs) {

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = spaceShip.getX();
            int playerY = spaceShip.getY();

            if (!spaceShip.isDead() && !bomb.isDead()) {

                // these are the constraints for collision
                if (bombX >= (playerX) && bombX <= (playerX + Constants.SPACESHIP_WIDTH) && bombY >= (playerY)
                        && bombY <= (playerY + Constants.SPACESHIP_HEIGHT)) {

                    soundFactory.explosion();
                    laser.die();
                    shields--;
                    if (shields < 0)
                        spaceShip.die();
                    bomb.die();
                }
            }

            if (!bomb.isDead()) {
                bomb.move();
            }
        }
    }

    public void doOneLoop() {
        update();
        repaint();
    }

    public void keyReleased(KeyEvent e) {
        spaceShip.keyReleased(e);
    }

    public void keyPressed(KeyEvent e) {

        spaceShip.keyPressed(e);

        int x = spaceShip.getX();
        int y = spaceShip.getY();

        int key = e.getKeyCode();

        // fire whenever the user hits SPACE
        if (key == KeyEvent.VK_SPACE) {

            if (inGame) {
                if (laser.isDead()) {
                    soundFactory.laser();
                    laser = new Laser(x, y);
                }
            }
        }
    }
}
