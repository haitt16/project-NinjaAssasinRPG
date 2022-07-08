package entity;

import main.GamePanel;

public class Projectile extends Entity {
	Entity user;
	public Projectile (GamePanel gp) {
		super(gp);
	}
	public void set(int x, int y, String direction,boolean alive, Entity user) {
		 this.x=x;
		 this.y=y;
		 this.direction=direction;
		 this.alive=alive;
		 this.user=user;
		 this.life=this.maxLife;
	}
	public void update() {
		switch(direction) {
		case "up": y-=speed;break;
		case "down": y+=speed;break;
		case "left": x-=speed;break;
		case "right": x+=speed;break;
		}
		life--;
		if (life <=0) {
			alive=false;
		}
		spriteCounter++;
		if (spriteCounter>12) {
			if (spriteNum==1) {
				spriteNum=2;
				
			}
			else if (spriteNum==2) {
				spriteNum=1;
			}
			spriteCounter=0;
		}
	}
}
