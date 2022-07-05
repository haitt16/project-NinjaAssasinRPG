package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{//Người chơi kế thừa nhân vật
    
    KeyHandler keyH;
    public int hasKey = 0;
    public int countChest = 0;
    
    //public final int screenX;
    //public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);

        
        this.keyH = keyH;        
    
        //screenX = gp.screenWidth/2 - (gp.tileSize/2);
        //screenY = gp.screenHeight/2 - (gp.tileSize/2);
        
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {
        x = 80;
        y = 80;
        speed = 4;
        direction = "down";
        
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void update() {
    	if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
    		if (keyH.upPressed == true){
                direction = "up";
                //y -= speed;
            }
            else if (keyH.downPressed == true){
                direction = "down";
                //y += speed;
            }
            else if (keyH.leftPressed == true){
                direction = "left";
                //x -= speed;
            }
            else if (keyH.rightPressed == true){
                direction = "right";
                //x += speed;
            }
            // Check the Collision
    		collisionOn = false;
    		gp.cChecker.checkTile(this);
    		// check obj collision
    		int objIndex = gp.cChecker.checkObject(this, true);
    		pickUpObject(objIndex);
            //check npc collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
            //check monster collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);
    		
    		gp.eHandler.checkEvent();
    		
    		// If Collision is false then player can move
    		if(collisionOn == false) {
    			switch(direction) {
    			case "up": y-=speed; break;
    			case "down": y+=speed; break;
    			case "left": x-=speed; break;
    			case "right": x+=speed; break;
    			}
    		}
    		
            spriteCounter ++;
            if (spriteCounter > 12) {
            	if (spriteNum == 1) {
            		spriteNum = 2;
            	}
            	else if (spriteNum == 2) {
            		spriteNum = 1;
            	}
            		spriteCounter = 0;
            }
    	}
        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        
    }
    
    public void pickUpObject(int i) {
    	if (i != 999) {
    		String objectName = gp.obj[i].name;
    		switch(objectName) {
    		case "Key":
    			hasKey++;
    			gp.obj[i] = null;
    			gp.ui.showMessage("You got a key!");
    			break;
    		case "Door":
    			if (hasKey > 0) {
    				gp.obj[i] = null;
    				hasKey--;
    				gp.ui.showMessage("Door was opened!");
    			}
    			else {
    				gp.ui.showMessage("You need a key!");
    			}
    			break;
    		case "Recuperate":// Thay the cho Healing Pool
    			life = maxLife;
    			gp.ui.showMessage("You have recovered");
    			break;
    		case "Chest":
    			gp.obj[i] = null;
    			countChest++;
    			break;
    		}
    	}
    }
    public void interactNPC(int i) {
        if(i != 999) {
            System.out.println("hitting npc");
            gp.ui.showMessage("F**K OFF");
        }
    }
    public void contactMonster(int i) {
        if(i != 999) {
            System.out.println("hitting monster");
            gp.ui.showMessage("Ouchhhh");
            if(invincible == false) {
                life -= 1;
                invincible = true;
            }

        }
    }
    
    public void draw(Graphics2D g2) {
        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);

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

        if(invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

        //reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}