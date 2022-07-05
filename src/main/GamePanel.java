package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;

public class GamePanel extends JPanel implements Runnable {
	
	final int originalTileSize=16;
	final int scale=3;
	public final int tileSize= originalTileSize*scale;
	final int maxScreenCol=16;
	final int maxScreenRow=12;
	final int screenWidth= tileSize*maxScreenCol;
	final int screenHeight= tileSize*maxScreenRow;
	
	int FPS=60; //THIET LAP FPS
	
	KeyHandler keyH=new KeyHandler();
	Thread gameThread;
	//ENTITY AND OBJECT
	Player player=new Player(this, keyH);
	public Entity obj[]=new Entity[10];
	public Entity npc[]=new Entity[10];
	public Entity monster[]=new Entity[20];
	public ArrayList<Entity> projectileList=new ArrayList<>();
	public ArrayList<Entity> particleList=new ArrayList<>();
	
	ArrayList<Entity> entityList=new ArrayList<>();
	
	//Set Default Position
	int playerX=100;
	int playerY=100;
	int playerSpeed=4;
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.white);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		while(gameThread != null) {
			
			double drawInterval=1000000000/FPS;
			double nextDrawTime=System.nanoTime()+drawInterval;
			
			//1 UPDATE: update information such as character positions
			update();
			//2 DRAW: draw the screen with the updated information
			repaint();
			try {
				double remainingTime=nextDrawTime-System.nanoTime();
				remainingTime=remainingTime/1000000;
				if (remainingTime<0) remainingTime=0;
			
				Thread.sleep((long)remainingTime);
				nextDrawTime+=drawInterval;
			
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update() {
		if (gameState==playState) {
			//PLAYER
			player.update();
			//NPC
			for (int i=0;i < npc.length;i++) {
				if (npc[i] !=null) {
					npc[i].update();
				}
			}
			
			for (int i=0;i < monster.length;i++) {
				if (monster[i] !=null) {
					
				}
				
			}
			for (int i=0;i<projectileList.size();i++) {
				if (projectileList.get(i) !=null) {
					if (projectileList.get(i).alive=true) {
						projectileList.get(i).update();
					}
					if (projectileList.get(i).alive==false) {
						projectileList.remove(i);
					}
				}
			}
			
			for (int i=0;i<particleList.size();i++) {
				if (particleList.get(i) !=null) {
					if (particleList.get(i).alive=true) {
						particleList.get(i).update();
					}
					if (particleList.get(i).alive==false) {
						particleList.remove(i);
					}
				}
			}
			for (int i=0; i<iTile.length;i++) {
				if (iTile[i]!=null) {
					iTile[i].update();
				}
			}
		}
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2= (Graphics2D) g;
		//DEBUG
		
		//TITLE SCREEN
		if (gameState==titleState) {
			ui.draw(g2);
		}
		//OTHERS
		else {
			//TITLE
			tileM.draw(g2);
			
			//INTERACTIVE TILE
			
			//ADD ENTITIES TO THE LIST
			entityList.add(player);
			for (int i=0;i<npc.length;i++) {
				if (npc[i] !=null) {
					entityList.add(npc[i]);
				}
			}
			for (int i=0;i<obj.length;i++) {
				if (obj[i] !=null) {
					entityList.add(obj[i]);
				}
			}
			for (int i=0;i<monster.length;i++) {
				if (monster[i] !=null) {
					entityList.add(monster[i]);
				}
			}
			for (int i=0;i<projectileList.size();i++) {
				if (projectileList.get(i) !=null) {
					entityList.add(projectileList.get(i));
				}
			}
			for (int i=0;i<particleList.size();i++) {
				if (particleList.get(i) !=null) {
					entityList.add(particleList.get(i));
				}
			}
		}
		
	}
}
