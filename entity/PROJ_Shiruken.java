package entity;

import java.io.IOException;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class PROJ_Shiruken extends Projectile {
	GamePanel gp;

	public PROJ_Shiruken(EntityGraphic entityGraphic) {
		super(entityGraphic);
		name = "Shiruken";
		solidArea.x = 20;
		solidArea.y = 20;
		solidArea.width = 8;
		solidArea.height = 8;
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
			entityGraphic.up1 = ImageIO.read(getClass().getResourceAsStream("/animation/tiles/shiruken_1.png"));
			entityGraphic.up2 = ImageIO.read(getClass().getResourceAsStream("/animation/tiles/shiruken_2.png"));
			entityGraphic.down1 = ImageIO.read(getClass().getResourceAsStream("/animation/tiles/shiruken_1.png"));
			entityGraphic.down2 = ImageIO.read(getClass().getResourceAsStream("/animation/tiles/shiruken_2.png"));
			entityGraphic.left1 = ImageIO.read(getClass().getResourceAsStream("/animation/tiles/shiruken_1.png"));
			entityGraphic.left2 = ImageIO.read(getClass().getResourceAsStream("/animation/tiles/shiruken_2.png"));
			entityGraphic.right1 = ImageIO.read(getClass().getResourceAsStream("/animation/tiles/shiruken_1.png"));
			entityGraphic.right2 = ImageIO.read(getClass().getResourceAsStream("/animation/tiles/shiruken_2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		BufferedImage image = null;

		// Draw image with each .direction

		switch (direction) {
			case "up":
				if (entityGraphic.spriteNum == 1) {
					image = entityGraphic.up1;
				}
				if (entityGraphic.spriteNum == 2) {
					image = entityGraphic.up2;
				}
				break;
			case "down":
				if (entityGraphic.spriteNum == 1) {
					image = entityGraphic.down1;
				}
				if (entityGraphic.spriteNum == 2) {
					image = entityGraphic.down2;
				}
				break;
			case "left":
				if (entityGraphic.spriteNum == 1) {
					image = entityGraphic.left1;
				}
				if (entityGraphic.spriteNum == 2) {
					image = entityGraphic.left2;
				}
				break;
			case "right":
				if (entityGraphic.spriteNum == 1) {
					image = entityGraphic.right1;
				}
				if (entityGraphic.spriteNum == 2) {
					image = entityGraphic.right2;
				}
				break;
		}

		// Monster HP bar

		// if invincible image, turn on the HP bar animation

		g2.drawImage(image, x, y, entityGraphic.gp.tileSize / 2, entityGraphic.gp.tileSize / 2, null);

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

}