package entity;

import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import main.KeyHandler;

import java.awt.Graphics2D;
import java.awt.Color;
import main.GamePanel;

public class EntityGraphic {
    // Animation
    GamePanel gp;
    KeyHandler keyH;
    Entity entity;

    public EntityGraphic(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, face;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1,
            attackRight2;

    public int spriteCounter = 0; // Change image in 1 direction
    public int spriteNum = 1; // Image animation management

    public boolean collisionOn = false;
    public int actionLockCounter = 0; // NPC and Moster auto moving
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;

}
