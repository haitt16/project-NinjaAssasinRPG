package object;

import main.GamePanel;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;

public class OBJ_Rock extends Projectile {
	GamePanel gp;
	public OBJ_Rock(GamePanel gp) {
		super(gp);
		this.gp=gp;
		name="Rock";
		speed=8;
		maxLife=80;
		life=maxLife;
		attack=2;
		useCost=1;
		alive=false;
		getImage();
		
	}
	void getImage() {
		//.................
	}
	public boolean haveResource(Entity user) {
		boolean haveResource=false;
		if (user.ammo >= useCost) {
			haveResource=true;
		}
		return haveResource;
	}
	public void subtractResource(Entity user) {
		user.ammo -= useCost;
	}
	public Color getParticleColor() {
		Color color=new Color(40,50,0);
		return color;
	}
	public int getParticleSize() {
		int size=10;
		return size;
	}
	public int getParticleSpeed() {
		int speed=1;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife=20;
		return maxLife;
	}
}

