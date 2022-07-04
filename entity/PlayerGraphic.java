package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Graphics2D;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class PlayerGraphic {
    Player character;
    GamePanel gp;
    KeyHandler keyH;

    public PlayerGraphic(Player character, GamePanel gp, KeyHandler keyH) {
        this.character = character;
        this.gp = gp;
        this.keyH = keyH;
    }

    public void update() {
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
                || keyH.rightPressed == true) {
            if (keyH.upPressed == true) {
                System.out.println("character move up");
                character.direction = "up";
                character.worldY -= character.speed;
            } else if (keyH.downPressed == true) {
                System.out.println("character move down");
                character.direction = "down";
                character.worldY += character.speed;
            } else if (keyH.leftPressed == true) {
                System.out.println("character move left");
                character.direction = "left";
                character.worldX -= character.speed;
            } else if (keyH.rightPressed == true) {
                System.out.println("character move right");
                character.direction = "right";
                character.worldX += character.speed;
            }
            character.spriteCounter++;
            if (character.spriteCounter > 20) {
                if (character.spriteNum == 1) {
                    character.spriteNum = 2;
                } else
                    character.spriteNum = 1;
                character.spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (character.direction) {
            case "up":
                if (character.spriteNum == 1) {
                    image = character.up1;
                    break;
                } else {
                    image = character.up2;
                    break;
                }
            case "down":
                if (character.spriteNum == 1) {
                    image = character.down1;
                    break;
                } else {
                    image = character.down2;
                    break;
                }
            case "left":
                if (character.spriteNum == 1) {
                    image = character.left1;
                    break;
                } else {
                    image = character.left2;
                    break;
                }
            case "right":
                if (character.spriteNum == 1) {
                    image = character.right1;
                    break;
                } else {
                    image = character.right2;
                    break;
                }

        }
        g2.drawImage(image, character.screenX, character.screenY, gp.titleSize, gp.titleSize, null);
    }
}
