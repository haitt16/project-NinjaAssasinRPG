package entity;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Entity {
	GamePanel gp;
	public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
	public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,
	attackLeft1,attackLeft,attackRight1,attackRight2;
	public BufferedImage image,image2,image3;
	public Rectangle solidArea =new Rectangle(0,0,48,48);
	public Rectangle attackArea=new Rectangle(0,0,0,0);
	public int solidAreaDefaultX,solidAreaDefaultY;
	public boolean collision=false;
	String dialogues[]=new String [20];
	
	//STATE
	public int worldX,worldY;
	public String direction ="down";
	public int spriteNum=1;
	int dialougeIndex=0;
	public boolean collisionOn=false;
	public boolean invincible=false;
	boolean attacking=false;
	public boolean alive=true;
	public boolean dying=false;
	boolean hpBarOn=false;
	public int x,y;
	//COUNTER
	public int spriteCounter=0;
	public int actionLockCounter=0;
	public int invincibleCounter=0;
	int dyingCounter=0;
	int hpBarCounter=0;
	//CHARACTER ATTRIBUTES
	public String name;
	public int speed;
	public int maxLife;
	public int life;
	public int maxMana;
	public int mana;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public Entity currentWeapon;
	public Entity currentShield;
	public Projectile projectile;
	
	public int ammo;
	//ITEM ATTRIBUTES
	public int attackValue;
	public int defenseValue;
	public String description="";
	public int useCost;
	
	
	//TYPE
	public int type;
	public final int type_player=0;
	public final int type_npc=1;
	public final int type_monster=2;
	public final int type_sword=3;
	public final int type_axe=4;
	public final int type_shield=5;
	public final int type_consumable=6;
	public final int type_pickupOnly=7;
	
	public Entity (GamePanel gp) {
		
	}
	public void setAction() {
		
	}
	//...........
	public void dropItem(Entity droppedItem) {
		
	}
	public Color getParticleColor() {
		Color color=null;
		return color;
	}
	public int getParticleSize() {
		int size=0;
		return size;
	}
	public int getParticleSpeed() {
		int speed=0;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife=0;
		return maxLife;
	}
	
	public void generateParticle(Entity generator, Entity target) {
		Color color=generator.getParticleColor();
		int size=generator.getParticleSize();
		int speed=generator.getParticleSpeed();
		int maxLife=generator.getParticleMaxLife();
		Particle p1= new Particle (gp, generator, color, size, speed, maxLife, -2, -1);
		gp.particleList.add(p1);
		Particle p2= new Particle (gp, generator, color, size, speed, maxLife, 2, -1);
		gp.particleList.add(p2);
		Particle p3= new Particle (gp, generator, color, size, speed, maxLife, -2, -1);
		gp.particleList.add(p3);
		Particle p4= new Particle (gp, generator, color, size, speed, maxLife, 2, -1);
		gp.particleList.add(p4);
	}
	
	
	public void update() {
		
	}
	//.............
}
