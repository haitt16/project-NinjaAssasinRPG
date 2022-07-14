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
		if (user==gp.player) {
			int monsterIndex=gp.cChecker.checkEntity(this, gp.monster);
			if (monsterIndex !=999) {
				gp.player.damageMonster(monsterIndex, attack);
				alive=false;
			}
		}
		else if (user !=gp.player){
			 boolean contactPlayer=gp.cChecker.checkPlayer(this);
			 if (gp.player.invincible==false && contactPlayer==true) {
				 damagePlayer(attack);
				 alive=false;
			 }
		}
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
