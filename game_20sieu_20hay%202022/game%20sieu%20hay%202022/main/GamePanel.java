
package main;

import java.awt.Color;//màu
import java.awt.Dimension;//kích thước
import java.awt.Graphics;//Đồ hoạ
import java.awt.Graphics2D;//Đồ hoạ 2D
import java.util.ArrayList;

import javax.swing.JPanel;//Tổ chức các component

import Object.SuperObject;
import entity.Entity;
import entity.EntityGraphic;
import entity.Player;
import tile.TileManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {// Runable thực thi một luồng
    // SCREEN SETTINGS
    final int originalTileSize = 16;// 16*16 title
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;// 48*48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;// 48*16=768 pixels
    public final int screenHeight = tileSize * maxScreenRow;// 48*12=576 pixels

    public int mapLevel = 0;

    int FPS = 60;

    TileManager tileM = new TileManager(this);

    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;

    // Entity

    public CollisionChecker cChecker = new CollisionChecker(this);
    public EntityGraphic playerGraphic = new EntityGraphic(this);
    public Player player = new Player(playerGraphic);
    public SuperObject obj[] = new SuperObject[100];
    public int drawPitCounter = 0;
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);

    public Entity monster[] = new Entity[20];
    public EntityGraphic monsterGraphics[] = new EntityGraphic[20];
    public int monsterCount;
    public Entity npc[] = new Entity[10];
    public EntityGraphic npcGraphics[] = new EntityGraphic[21];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    // Game State
    public String gameState = "play";

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        gameState = "title";
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        monsterCount = aSetter.monsterCount;
        player.setKeyH(keyH);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        double drawInterval = 1000000000 / FPS;
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
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                System.out.println(gameState);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void retry() {
        tileM = new TileManager(this);
        aSetter = new AssetSetter(this);
        eHandler = new EventHandler(this);
        player.setDefaultPosition();
        player.setDefaultValues();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        monsterCount = aSetter.monsterCount;
        gameState = "play";
        this.ui.messageOn = false;
    }

    public void startNewGame() {
        mapLevel = 0;
        resetAssets();
        tileM = new TileManager(this);
        aSetter = new AssetSetter(this);
        eHandler = new EventHandler(this);
        player.setDefaultValues();
        player.setDefaultPosition();
        player.restoreLifeAndMan();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        monsterCount = aSetter.monsterCount;
        gameState = "play";
        this.ui.messageOn = false;

    }

    public void resetAssets() {
        // OBJECTS

        for (int i = 0; i < obj.length; i++) {
            obj[i] = null;
        }
        for (int i = 0; i < monster.length; i++) {
            monster[i] = null;
        }
    }

    public void changeMap() {
        mapLevel += 1;
        resetAssets();
        tileM = new TileManager(this);
        aSetter = new AssetSetter(this);
        eHandler = new EventHandler(this);
        player.setNewMapValues();
        player.setDefaultPosition();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        monsterCount = aSetter.monsterCount;
        gameState = "play";
        this.ui.messageOn = false;
    }

    public void update() {
        player.update();
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].update();
            }
        }
        for (int i = 0; i < monster.length; i++) {
            if (monster[i] != null) {
                if (monster[i].alive == true && monster[i].dying == false) {
                    monster[i].update();
                }
                if (monster[i].alive == false) {
                    monster[i] = null;
                    // monsterCount -= 1;
                }
            }
        }
        for (int i = 0; i < projectileList.size(); i++) {
            if (projectileList.get(i) != null) {
                if (projectileList.get(i).alive == true) {
                    projectileList.get(i).update();
                }
                if (projectileList.get(i).alive == false) {
                    projectileList.remove(i);
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // TILE SCREEN
        if (gameState == "play" || gameState == "character" || gameState == "die") {
            // TILE
            tileM.draw(g2);

            // OBJECTS
            drawPitCounter++;
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    if (obj[i].name != "Pit") {
                        obj[i].draw(g2, this);
                    } else {

                        System.out.println("count: " + drawPitCounter);
                        if (drawPitCounter > 100 && drawPitCounter < 150) {
                            obj[i].draw(g2, this);
                            System.out.println("draw: " + drawPitCounter);

                        } else if (drawPitCounter >= 150) {
                            drawPitCounter = 0;
                        }
                    }
                }
            }
            // NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].draw(g2);
                }
            }
            // Monster
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    monster[i].draw(g2);
                }
            }
            // Projectile
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    projectileList.get(i).draw(g2);
                }
            }
            player.draw(g2);
            ui.draw(g2);
        } else {
            ui.draw(g2);
        }

        g2.dispose();
    }
}
