package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Object.OBJ_Heart;
import Object.OBJ_Key;
import Object.SuperObject;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font arial_40,arial_80B;;
	BufferedImage keyImage, heart_full, heart_half, heart_blank;
	public boolean messageOn = false;
	public String message = "";
	public int messageCount = 0;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		OBJ_Key key = new OBJ_Key();
		keyImage = key.image;
		
		SuperObject heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
	}
	
	public void showMessage (String text) {
		message = text;
		messageOn = true;
	}
	

	public void draw(Graphics2D g2) {
		switch (gp.gameState) {
			case "finish":
				drawGameFinishScreen(g2);
				break;
			case "play":
				drawPlayStateScreen(g2);
				break;
			case "die":
				drawDieStateScreen(g2);
				break;
		}
	}

	public void drawPlayStateScreen(Graphics2D g2) {
		g2.setFont(arial_40);
		g2.setColor(Color.WHITE);
		g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
		g2.drawString("x " + gp.player.hasKey, 70, 70);

		if (messageOn == true) {
			g2.setFont(g2.getFont().deriveFont(30F));
			g2.drawString(message, gp.tileSize * 2, gp.tileSize * 5);

			messageCount++;

			if (messageCount > 120) {
				messageCount = 0;
				messageOn = false;
			}
		}
		// gp.player.life = 6;

		int x = gp.tileSize * 3;
		int y = gp.tileSize / 2;
		int i = 0;

		while (i < gp.player.maxLife / 2) {
			g2.drawImage(heart_blank, x, y, gp.tileSize, gp.tileSize, null);
			i++;
			x += gp.tileSize;
		}

		x = gp.tileSize * 3;
		y = gp.tileSize / 2;
		i = 0;

		while (i < gp.player.life) {
			g2.drawImage(heart_half, x, y, gp.tileSize, gp.tileSize, null);
			i++;
			if (i < gp.player.life) {
				g2.drawImage(heart_full, x, y, gp.tileSize, gp.tileSize, null);
			}
			i++;
			x += gp.tileSize;
		}
	}

	public void drawDieStateScreen(Graphics2D g2) {
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
		g2.setColor(Color.RED);
		String text = "Game Over";
		int x = getXforCenteredText(text, g2);
		int y = gp.tileSize * 3;

		g2.drawString(text, x, y);

	}

	public void drawGameFinishScreen(Graphics2D g2) {
		g2.setFont(arial_40);
		g2.setColor(Color.WHITE);

		String text;
		int textLength;
		int x, y;

		text = "You hit the end of the map!";
		textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

		x = gp.screenWidth / 2 - textLength / 2;
		y = gp.screenHeight / 2 - gp.tileSize * 3;
		g2.drawString(text, x, y);

		g2.setFont(arial_80B);
		g2.setColor(Color.YELLOW);

		text = "Well done";
		textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

		x = gp.screenWidth / 2 - textLength / 2;
		y = gp.screenHeight / 2 + (gp.tileSize * 2);
		g2.drawString(text, x, y);

		gp.gameThread = null;
	}

	public int getXforCenteredText(String text, Graphics2D g2) {
		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return gp.screenWidth / 2 - textLength / 2;
	}
}

