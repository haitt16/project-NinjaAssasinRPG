package entity;//Nhân vật trong game(player and monster)

import java.awt.AlphaComposite;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;

import main.GamePanel;

public class Entity {
    GamePanel gp;
    
    public int x, y;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public String direction;//Hướng đi
    public boolean attacking=false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;

    public int spriteCounter = 0;//sprite: Hình ảnh hai chiểu tích hợp vào một khung cảnh lớn hơn
    public int spriteNum = 1;
    
    public Rectangle solidArea = new Rectangle (0, 0, 48, 48);//Không gian giảm sự phụ thuộc
    public Rectangle attackArea = new Rectangle (0,0,0,0); 
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;//va chạm
    public int actionLockCounter = 0;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public int type; // 0= player, 1 = npc, 2 = monster
    int dyingCounter =0;
    int hpBarCounter =0;

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

        //Monter HP bar
        if (type ==2 && hpBarOn == true ){
        
            double oneScale = (double) gp.tileSize / maxLife;
            double hpBarValue = oneScale*life;
        
            g2.setColor(new Color(35,35,35));
            g2.fillRect( x-1 , y -16, gp.tileSize +2 , 12 );
            
            g2.setColor(new Color(255,0,30));
            g2.fillRect( x , y -15, (int)hpBarValue , 10 );

            hpBarCounter++;

            if (hpBarCounter> 600){
                hpBarCounter = 0;
                hpBarOn = false;
            }
        }
        
        if(invincible == true) {
            hpBarOn=true;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        if (dying == true) {
            dyingAnimation(g2);
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    public void dyingAnimation(Graphics2D g2){
        
        dyingCounter++;

        int i = 5;

        if (dyingCounter <=i) {changeAlpha(g2, 0f);}
        if (dyingCounter >i && dyingCounter <=i) {changeAlpha(g2, 1f);}
        if (dyingCounter >i*2 && dyingCounter <=i*3) {changeAlpha(g2, 0f);}
        if (dyingCounter >i*3 && dyingCounter <=i*4) {changeAlpha(g2, 1f);}
        if (dyingCounter >i*4 && dyingCounter <=i*5) {changeAlpha(g2, 0f);}
        if (dyingCounter >i*5 && dyingCounter <=i*6) {changeAlpha(g2, 1f);}
        if (dyingCounter >i*6 && dyingCounter <=i*7) {changeAlpha(g2, 0f);}
        if (dyingCounter >i*7 && dyingCounter <=i*8) {changeAlpha(g2, 1f);}
        if (dyingCounter >i*8){
            dying = false;
            alive = false;
        }
    }
    public void changeAlpha(Graphics2D g2 , float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    public void setAction() {}
    public void damageReaction() {}
    /**
     * 
     */
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
    
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    
    }
    
}
