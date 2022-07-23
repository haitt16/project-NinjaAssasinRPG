package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;

import Object.OBJ_Chest;
import Object.OBJ_Heart;
import Object.OBJ_Key;
import Object.OBJ_Mana;
import Object.SuperObject;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font arial_40, arial_80B;
	BufferedImage keyImage, chestImage, heart_full, heart_half, heart_blank, mana_blank, mana_full;
	public boolean messageOn = false;
	public String message = "";
	public int messageCount = 0;
	public int commandNum = 0;

	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
		OBJ_Key key = new OBJ_Key();
		keyImage = key.image;

		OBJ_Chest chest = new OBJ_Chest();
		chestImage = chest.image;

		OBJ_Heart heart = new OBJ_Heart(gp);
		heart_full = heart.full;
		heart_half = heart.half;
		heart_blank = heart.blank;

		OBJ_Mana mana = new OBJ_Mana(gp);
		mana_blank = mana.blank;
		mana_full = mana.full;
	}

	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}

	public void draw(Graphics2D g2) {
		switch (gp.gameState) {
			case "title":
				drawTitleStateScreen(g2);
				break;
			case "finish":
				drawGameFinishScreen(g2);
				break;
			case "play":
				drawPlayStateScreen(g2);
				break;
			case "die":
				drawDieStateScreen(g2);
				break;
			case "character":
				drawCharacterStateScreen(g2);
				break;
			case "pause":
				drawPauseStateScreen(g2);
		}
	}

	private void drawPauseStateScreen(Graphics2D g2) {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		int x, y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));

		text = "Paused";
		// Shadow
		g2.setColor(Color.black);
		x = getXforCenteredText(text, g2);
		y = gp.tileSize * 3;
		g2.drawString(text, x, y);
		// Main
		g2.setColor(Color.WHITE);
		g2.drawString(text, x - 4, y - 4);

		// Retry
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));
		text = "Retry";
		x = getXforCenteredText(text, g2);
		y += gp.tileSize * 2;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			g2.drawString(">", x - gp.tileSize + 4, y);
			g2.drawString("<", x + textLength + gp.tileSize / 2 - 4, y);
		}

		// New game
		text = "New Game";
		x = getXforCenteredText(text, g2);
		y += 60;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			g2.drawString(">", x - gp.tileSize + 4, y);
			g2.drawString("<", x + textLength + gp.tileSize / 2 - 4, y);
		}

		// Quit
		text = "Quit";
		x = getXforCenteredText(text, g2);
		y += 60;
		g2.drawString(text, x, y);
		if (commandNum == 2) {
			int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			g2.drawString(">", x - gp.tileSize + 4, y);
			g2.drawString("<", x + textLength + gp.tileSize / 2 - 4, y);
		}
	}

	private void drawCharacterStateScreen(Graphics2D g2) {
		// FRAME
		final int frameX = gp.tileSize * 2;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize * 5;
		final int frameHeight = gp.tileSize * 10;
		drawSubWindow(g2, frameX, frameY, frameWidth, frameHeight);

		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(24F));

		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 40;

		// NAMES
		g2.drawString("Life", textX, textY);
		textY += lineHeight;
		g2.drawString("Mana", textX, textY);
		textY += lineHeight;
		g2.drawString("Chest", textX, textY);
		textY += lineHeight;
		g2.drawString("Key", textX, textY);
		textY += lineHeight;
		g2.drawString("Monsters", textX, textY);
		textY += lineHeight;

		// VALUES
		int tailX = (frameX + frameWidth) - 30;
		textY = frameY + gp.tileSize;
		String value;

		value = String.valueOf(gp.player.life) + "/" + String.valueOf(gp.player.maxLife);
		textX = getXforAlignToRightText(value, g2, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		if (gp.player.mana < 0) {
			value = "0/" + String.valueOf(gp.player.maxMana);
		} else {
			value = String.valueOf(gp.player.mana) + "/" + String.valueOf(gp.player.maxMana);
		}

		textX = getXforAlignToRightText(value, g2, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.countChest);
		textX = getXforAlignToRightText(value, g2, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.hasKey);
		textX = getXforAlignToRightText(value, g2, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.monsterCount);
		textX = getXforAlignToRightText(value, g2, tailX);
		g2.drawString(value, textX, textY);

	}

	public void drawSubWindow(Graphics2D g2, int x, int y, int width, int height) {
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);

		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}

	private void drawTitleStateScreen(Graphics2D g2) {
		// BACKGROUND COLOR
		g2.setColor(new Color(0, 0, 0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		// TITLE
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 75F));
		String text = "Ninja Assasin I";
		int x = getXforCenteredText(text, g2);
		int y = gp.tileSize * 3;

		// SHADOW
		g2.setColor(Color.GRAY);
		g2.drawString(text, x + 3, y + 3);

		g2.setColor(Color.RED);
		g2.drawString(text, x, y);

		// MAIN IMAGE
		x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
		y += gp.tileSize * 2;
		g2.drawImage(gp.player.entityGraphic.face, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

		// MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));

		text = "NEW GAME";
		x = getXforCenteredText(text, g2);
		y += gp.tileSize * 4;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			g2.drawString(">", x - gp.tileSize + 4, y);
			g2.drawString("<", x + textLength + gp.tileSize / 2 - 4, y);
		}

		text = "RESTART";
		x = getXforCenteredText(text, g2);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			g2.drawString(">", x - gp.tileSize + 4, y);
			g2.drawString("<", x + textLength + gp.tileSize / 2 - 4, y);
		}

		text = "QUIT";
		x = getXforCenteredText(text, g2);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 2) {
			int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			g2.drawString(">", x - gp.tileSize + 4, y);
			g2.drawString("<", x + textLength + gp.tileSize / 2 - 4, y);
		}

	}

	public void drawPlayStateScreen(Graphics2D g2) {
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
		g2.setColor(Color.WHITE);
		g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize / 2 - 5, gp.tileSize / 2, null);
		g2.drawString(": " + gp.player.hasKey, 50, 48);

		g2.drawImage(chestImage, gp.tileSize / 2, gp.tileSize + 13, gp.tileSize / 2, gp.tileSize / 2 - 5, null);
		g2.drawString(": " + gp.player.countChest, 50, 80);
		if (messageOn == true) {
			g2.setFont(g2.getFont().deriveFont(30F));
			g2.drawString(message, gp.tileSize * 2, gp.tileSize * 5);

			messageCount++;

			if (messageCount > 120) {
				messageCount = 0;
				messageOn = false;
			}
		}

		int x = 95;
		int y = gp.tileSize / 2 + 3;
		int i = 0;

		while (i < gp.player.maxLife / 2) {
			g2.drawImage(heart_blank, x, y, gp.tileSize / 2, gp.tileSize / 2, null);
			i++;
			x += gp.tileSize / 2;
		}

		x = 95;
		y = gp.tileSize / 2 + 3;
		i = 0;

		while (i < gp.player.life) {
			g2.drawImage(heart_half, x, y, gp.tileSize / 2, gp.tileSize / 2, null);
			i++;
			if (i < gp.player.life) {
				g2.drawImage(heart_full, x, y, gp.tileSize / 2, gp.tileSize / 2, null);
			}
			i++;
			x += gp.tileSize / 2;
		}

		x = 95;
		y = gp.tileSize + 12;
		i = 0;

		while (i < gp.player.maxMana) {
			g2.drawImage(mana_blank, x, y, gp.tileSize / 2, gp.tileSize / 2, null);
			i++;
			x += gp.tileSize / 2;
		}

		x = 95;
		y = gp.tileSize + 12;
		i = 0;

		while (i < gp.player.mana) {
			g2.drawImage(mana_full, x, y, gp.tileSize / 2, gp.tileSize / 2, null);
			i++;
			x += gp.tileSize / 2;
		}

	}

	public void drawDieStateScreen(Graphics2D g2) {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		int x, y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));

		text = "Game Over";
		// Shadow
		g2.setColor(Color.black);
		x = getXforCenteredText(text, g2);
		y = gp.tileSize * 3;
		g2.drawString(text, x, y);
		// Main
		g2.setColor(Color.RED);
		g2.drawString(text, x - 4, y - 4);

		// Retry
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));
		text = "Retry";
		x = getXforCenteredText(text, g2);
		y += gp.tileSize * 2;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			g2.drawString(">", x - gp.tileSize + 4, y);
			g2.drawString("<", x + textLength + gp.tileSize / 2 - 4, y);
		}

		// New game
		text = "New Game";
		x = getXforCenteredText(text, g2);
		y += 60;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			g2.drawString(">", x - gp.tileSize + 4, y);
			g2.drawString("<", x + textLength + gp.tileSize / 2 - 4, y);
		}

		// Quit
		text = "Quit";
		x = getXforCenteredText(text, g2);
		y += 60;
		g2.drawString(text, x, y);
		if (commandNum == 2) {
			int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			g2.drawString(">", x - gp.tileSize + 4, y);
			g2.drawString("<", x + textLength + gp.tileSize / 2 - 4, y);
		}

	}

	public void drawGameFinishScreen(Graphics2D g2) {
		g2.setFont(arial_40);
		g2.setColor(Color.WHITE);

		String text;
		int textLength;
		int x, y;

		if (gp.mapLevel == 0 || gp.mapLevel == 1) {
			text = "Press N to transit to the next map!";

		} else {
			text = "You destroyed the game!";
		}
		textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

		x = gp.screenWidth / 2 - textLength / 2;
		y = gp.screenHeight / 2 - gp.tileSize * 3;
		g2.drawString(text, x, y);

		g2.setFont(arial_80B);
		g2.setColor(Color.RED);

		text = "Well done";
		textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

		x = gp.screenWidth / 2 - textLength / 2;
		y = gp.screenHeight / 2 + (gp.tileSize * 2);
		g2.drawString(text, x, y);

		// gp.gameThread = null;
	}

	public int getXforCenteredText(String text, Graphics2D g2) {
		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return (gp.screenWidth / 2 - textLength / 2);
	}

	public int getXforAlignToRightText(String text, Graphics2D g2, int tailX) {
		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return (tailX - textLength);
	}

}
