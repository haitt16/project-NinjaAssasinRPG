package entity;

import java.awt.Color;

import main.GamePanel;

public class Particle extends Entity {
	Entity generator; //The entity that produce this particle
	Color color;
	int size;
	int xd,yd;
	public Particle(GamePanel gp,Entity generator,Color color,int size,int speed, int maxLife,int xd,int yd) {
		super(gp);
		this.generator=generator;
		this.color=color;
		this.size=size;
		this.speed=speed;
		this.xd=xd;
		this.yd=yd;
		life=maxLife;
		x=generator.x;
		y=generator.y;
	}
}
