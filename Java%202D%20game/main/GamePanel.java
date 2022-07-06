package main;

import java.awt.Color;//màu
import java.awt.Dimension;//kích thước
import java.awt.Graphics;//Đồ hoạ
import java.awt.Graphics2D;//Đồ hoạ 2D

import javax.swing.JPanel;//Tổ chức các component

import Object.SuperObject;
import entity.Entity;
import entity.Player;
import tile.TileManager;


@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable{//Runable thực thi một luồng
    //SCREEN SETTINGS
    final int originalTileSize = 16;//16*16 title
    final int scale = 3;

    public final int tileSize = originalTileSize*scale;//48*48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;//48*16=768 pixels
    public final int screenHeight = tileSize * maxScreenRow;//48*12=576 pixels
    
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[11];
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);

    //Entity array
    public Entity monster[] = new Entity[20];
    public Entity npc[] = new Entity[10];

    //Game State
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    
    public void setupGame(){
    	aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    

    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime)/drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                System.out.println("FPS: "+drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update(){
        player.update();
        for (int i=0; i<npc.length; i++) {
            if (npc[i] != null) {
                npc[i].update();
            }
        }
        for(int i = 0; i< monster.length; i++) {
            if(monster[i] != null) {
                monster[i].update();
            }
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 =(Graphics2D)g;
        
        tileM.draw(g2);
        
        for (int i=0; i<obj.length; i++) {
        	if (obj[i] != null) {
        		obj[i].draw(g2, this);
        	}
        }
        //NPC 
        for (int i=0; i<npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw(g2);
            }
        }
        //Monster
        for (int i=0; i< monster.length; i++) {
            if (monster[i] != null) {
                monster[i].draw(g2);
            }
        }
        
        player.draw(g2);
        ui.draw(g2);
        //ui.drawPlayerLife();
        g2.dispose();
    }
}
