package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import background.TilesManage;
import entity.Player;
import entity.PlayerGraphic;
import object.SuperObject;

import java.awt.Dimension;
import entity.Entity;

public class GamePanel extends JPanel implements Runnable {
    final int originalTitleSize = 16;
    final int scale = 3;
    public final int titleSize = originalTitleSize * scale; // 48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = titleSize * maxScreenCol; // 768
    public final int screenHeight = titleSize * maxScreenRow; // 576
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = maxWorldCol * titleSize;
    public final int worldHeight = titleSize * maxScreenRow;
    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    TilesManage tileM = new TilesManage(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];
    PlayerGraphic playerGraph = new PlayerGraphic(player, this, keyH);
    // player position,speed
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 3;
    int FPS = 60;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        double drawInterval = 1e9 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            if (delta >= 1) {
                // update game forward
                update();
                // repaint everything in game
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }
        playerGraph.draw(g2);

        // for (int i = 0; i < npc.length; i++) {
        // if (npc[i] != null) {
        // npc[i].draw(g2);
        // }
        // }

        ui.draw(g2);
        g2.dispose();
    }

    public void update() {
        player.update();
    }

}
