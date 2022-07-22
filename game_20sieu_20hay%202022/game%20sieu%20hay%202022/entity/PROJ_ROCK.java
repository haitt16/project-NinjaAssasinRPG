package entity;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class PROJ_ROCK extends Projectile {
	GamePanel gp;

	public PROJ_ROCK(EntityGraphic entityGraphic) {
		super(entityGraphic);
		name = "Rock";
		speed = 5;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();

	}

	public void getImage() {
		try {
			entityGraphic.up1 = ImageIO.read(getClass().getResourceAsStream("/animation/tiles/rock_down_1.png"));
			entityGraphic.up2 = ImageIO.read(getClass().getResourceAsStream("/animation/tiles/rock_down_1.png"));
			entityGraphic.down1 = ImageIO.read(getClass().getResourceAsStream("/animation/tiles/rock_down_1.png"));
			entityGraphic.down2 = ImageIO.read(getClass().getResourceAsStream("/animation/tiles/rock_down_1.png"));
			entityGraphic.left1 = ImageIO.read(getClass().getResourceAsStream("/animation/tiles/rock_down_1.png"));
			entityGraphic.left2 = ImageIO.read(getClass().getResourceAsStream("/animation/tiles/rock_down_1.png"));
			entityGraphic.right1 = ImageIO.read(getClass().getResourceAsStream("/animation/tiles/rock_down_1.png"));
			entityGraphic.right2 = ImageIO.read(getClass().getResourceAsStream("/animation/tiles/rock_down_1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}