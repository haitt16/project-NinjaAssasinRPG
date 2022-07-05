package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;

public class Player extends Entity{
	private GamePanel gp;           
	private KeyHandler keyH;		
	public Player(GamePanel gp,KeyHandler keyH) {
		this.gp=gp;
		this.keyH=keyH;
		setDefaultValues();
	}
	public void setDefaultValues() {
		x=100;
		y=100;
		speed=4;
		//PLAYER STATUS
		
		
		projectile=new OBJ_Fireball(gp);
	}
	public void update() {
		if (keyH.upPressed==true) {
			y=y-speed;	
		}
		else if (keyH.downPressed==true) {
			y=y+speed;
		}
		else if (keyH.rightPressed==true) {
			x=x+speed;
		}
		else if (keyH.leftPressed==true) {
			x=x-speed;
		}
	
	
		
		
		if(gp.keyH.shotKeyPressed ==true && projectile.alive==false) {
			//SET DEFAULT COORDINATES, DIRECTION AND USER
			projectile.set(worldX,worldY,direction,true,this);
			//ADD TO LIST
			gp.projectileList.add(projectile);
		}
	}
	
	public void damageInteractive(int i) {
		if (i!=999 && gp.tileSize[i].destructible==true 
				&& gp.tileSize[i].isCorrectItem(this)==true && gp.tileSize[i].invincible==false) {
			gp.iTile[i].playSE();
			gp.iTile[i].life--;
			gp.iTile[i].invincible=true;
			
			generateParticle(gp.iTile[i],gp.iTile[i]);
			if (gp.iTile[i].life==0) {
				gp.iTile[i]=gp.iTile[i].getDestroyedForm();
				
			}
		}
	}
	public void checkLevelUp() {
		
	}
	public void selectItem() {
		
	}
	public void draw(Graphics2D g2) {
		g2.setColor(Color.black);
		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
	}
	
}
