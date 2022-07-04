package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.awt.Graphics2D;

import main.GamePanel;

//used in player,npc,monster class
public class Entity {
    // GamePanel gp;
    public int worldX, worldY;
    public int x, y;
    public int speed, HP, MP;
    public String direction;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 32, 32);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    // public Entity(GamePanel gp) {
    // this.gp = gp;
    // }

    // public void draw(Graphics2D g2) {
    // BufferedImage image = null;
    // int screenX = worldX - gp.player.worldX + gp.player.screenX;
    // int screenY = worldY - gp.player.worldY + gp.player.screenY;
    // if (worldX + gp.titleSize > gp.player.worldX - gp.player.screenX &&
    // worldX - gp.titleSize < gp.player.worldX + gp.player.screenX &&
    // worldY + gp.titleSize > gp.player.worldY - gp.player.screenY &&
    // worldY - gp.titleSize < gp.player.worldY + gp.player.screenY) {
    // switch (direction) {
    // case "up":
    // if (spriteNum == 1) {
    // image = up1;
    // break;
    // } else {
    // image = up2;
    // break;
    // }
    // case "down":
    // if (spriteNum == 1) {
    // image = down1;
    // break;
    // } else {
    // image = down2;
    // break;
    // }
    // case "left":
    // if (spriteNum == 1) {
    // image = left1;
    // break;
    // } else {
    // image = left2;
    // break;
    // }
    // case "right":
    // if (spriteNum == 1) {
    // image = right1;
    // break;
    // } else {
    // image = right2;
    // break;
    // }

    // }
    // g2.drawImage(image, screenX, screenY, gp.titleSize, gp.titleSize, null);
    // }
    // }
}
