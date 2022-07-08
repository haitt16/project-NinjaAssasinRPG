package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Object.OBJ_Fireball;
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

        attackArea.width = 36;
        attackArea.height = 36;
        
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }
    public void setDefaultValues() {
        x = 80;
        y = 80;
        speed = 4;
        direction = "down";
        
        maxLife = 6;
        life = maxLife;
        
        projectile=new OBJ_Fireball(gp);
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
    public void getPlayerAttackImage(){
        try {
            attackUp1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_attack_up_1.png"));
            attackUp2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_attack_up_2.png"));
            attackDown1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_attack_down_1.png"));
            attackDown2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_attack_down_2.png"));
            attackLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_attack_left_1.png"));
            attackLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_attack_left_2.png"));
            attackRight1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_attack_right_1.png"));
            attackRight2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_attack_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
    public void update() {
        if (attacking == true){
            attacking();
        }
    	else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
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
    		if(collisionOn == false && keyH.enterPressed == false) {
    			switch(direction) {
    			case "up": y-=speed; break;
    			case "down": y+=speed; break;
    			case "left": x-=speed; break;
    			case "right": x+=speed; break;
    			}
    		}
    		
            keyH.enterPressed =false;

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
            
    	if (gp.keyH.shotKeyPressed == true && projectile.alive==false) {
    		projectile.set(x,y,direction,true,this);
    	//ADD TO THE LIST
    		gp.projectileList.add(projectile);
 
    	}
    	}
    	
        if(invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }        
        
    
    public void attacking(){
        spriteCounter++;

        if (spriteCounter<=5){
            spriteNum=1;
        }
        if (spriteCounter >5 && spriteCounter <=25){
            spriteNum =2;
            
            //Save the current worldX, worldY, solidArea
            int currentWorldX = x;
            int currentWorldY = y;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust player's worldX/Y for the attackArea
            switch(direction){
                case "up": y -= attackArea.height;break;
                case "down": y +=attackArea.height;break;
                case "left": x -= attackArea.width;break;
                case "right": x +=attackArea.width;break;
            }
            //attackArea become solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            //Check monter collision with update worldX, worldY and solidArea
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonter(monsterIndex);

            //after checking collision, restore the original data
            x = currentWorldX;
            y = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (spriteCounter>25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
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
            case "pokeball":
                gp.obj[i] = null;
                gp.ui.showMessage("You got pokeball");
                break;
            }
    	}
    }
    public void interactNPC(int i) {
        if (keyH.enterPressed == true ){
            if(i != 999) {
                    System.out.println("hitting npc");
                    gp.ui.showMessage("Pick up the key to open the door"); 
                }
            else {
                attacking = true ;
            }
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
    public void damageMonter(int i){
        if(i!=999){
            
            if (gp.monster[i].invincible == false ){

                gp.monster[i].life -=1;
                gp.monster[i].invincible =true;
                gp.monster[i].damageReaction();
                if(gp.monster[i].life <= 0){
                    gp.monster[i].dying = true;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (attacking == false){
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                }
                if (attacking == true){
            	    if (spriteNum == 1) {image = attackUp1;}
                    if (spriteNum == 2) {image = attackUp2;}
                }
                break;
            case "down":
                if (attacking == false){
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                }
                if (attacking == true){
            	    if (spriteNum == 1) {image = attackDown1;}
                    if (spriteNum == 2) {image = attackDown2;}
                }
                break;
            case "left":
                if (attacking == false){
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                }
                if (attacking == true){
                    if (spriteNum == 1) {image = attackLeft1;}
                    if (spriteNum == 2) {image = attackLeft2;}
                }
                break;
            case "right":
                if (attacking == false){
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                }
                if (attacking == true){
                    if (spriteNum == 1) {image = attackRight1;}
                    if (spriteNum == 2) {image = attackRight2;}
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