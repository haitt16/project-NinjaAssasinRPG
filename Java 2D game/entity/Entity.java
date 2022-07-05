package entity;//Nhân vật trong game(player and monster)

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import main.GamePanel;

public class Entity {
    GamePanel gp;
    
    public int x, y;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;//Hướng đi
    
    public int spriteCounter = 0;//sprite: Hình ảnh hai chiểu tích hợp vào một khung cảnh lớn hơn
    public int spriteNum = 1;
    
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);//Không gian giảm sự phụ thuộc
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;//va chạm
    public int actionLockCounter = 0;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public int type; // 0= player, 1 = npc, 2 = monster

    public int maxLife;
    public int life;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
            	if (spriteNum == 1) {
            		image = up1;
            	}
                if (spriteNum == 2) {
                	image = up2;
                }
                break;
            case "down":
            	if (spriteNum == 1) {
            		image = down1;
            	}
                if (spriteNum == 2) {
                	image = down2;
                }
                break;
            case "left":
            	if (spriteNum == 1) {
            		image = left1;
            	}
                if (spriteNum == 2) {
                	image = left2;
                }
                break;
            case "right":
            	if (spriteNum == 1) {
            		image = right1;
            	}
                if (spriteNum == 2) {
                	image = right2;
                }
                break;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
    public void setAction() {

    }

    public void update(){
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        //gp.cChecker.checkEntity(this, gp.npc);
        //gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer == true) {
            if(gp.player.invincible == false) {
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }

        if(collisionOn == false) {
            switch(direction) {
                case "up": y -= speed; break;
                case "down": y+=speed; break;
                case "left": x-=speed; break;
                case "right": x+=speed; break;
            }
        }
        spriteCounter++;
        if(spriteCounter > 12) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
}
