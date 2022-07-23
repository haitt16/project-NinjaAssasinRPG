package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {// Người chơi kế thừa nhân vật
    EntityGraphic projectileGraphic;
    public int hasKey = 0;
    public int countChest = 0;

    public Player(EntityGraphic entityGraphic) {
        super(entityGraphic);
        // Key handler
        // Axes
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

    public void setKeyH(KeyHandler keyH) {
        this.entityGraphic.keyH = keyH;
    }

    public void setDefaultValues() {
        /*
         * Set default value for player
         */
        x = 80;
        y = 80;
        speed = 4;
        direction = "down";

        maxLife = 6;
        life = maxLife;

        maxMana = 4;
        mana = maxMana;

        hasKey = 0;
        countChest = 0;
        projectileGraphic = new EntityGraphic(entityGraphic.gp);
        projectile = new PROJ_Shiruken(projectileGraphic);
    }

    public void setNewMapValues() {
        /*
         * When player complete 1 map and take a transition to new map, these attributes
         * will be set
         * .x, .y : move player back to the top left of the map
         * .speed, .direction
         * .hasKey, countChest: keys and chests owned on new map will be reseted
         * 
         */
        x = 80;
        y = 80;
        speed = 4;
        direction = "down";
        hasKey = 0;
        countChest = 0;
    }

    public void setDefaultPosition() {
        x = 80;
        y = 80;
        direction = "down";
    }

    public void restoreLifeAndMan() {
        life = maxLife;
        mana = maxMana;
        invincible = false;
    }

    public void getPlayerImage() {
        // up1 = setupImage("ninja_up_1", gp.tileSize, gp.tileSize);
        entityGraphic.up1 = setupImage("ninja_up_1", entityGraphic.gp.tileSize, entityGraphic.gp.tileSize);
        entityGraphic.up2 = setupImage("ninja_up_2", entityGraphic.gp.tileSize, entityGraphic.gp.tileSize);
        entityGraphic.down1 = setupImage("ninja_down_1", entityGraphic.gp.tileSize, entityGraphic.gp.tileSize);
        entityGraphic.down2 = setupImage("ninja_down_2", entityGraphic.gp.tileSize, entityGraphic.gp.tileSize);
        entityGraphic.left1 = setupImage("ninja_left_1", entityGraphic.gp.tileSize, entityGraphic.gp.tileSize);
        entityGraphic.left2 = setupImage("ninja_left_2", entityGraphic.gp.tileSize, entityGraphic.gp.tileSize);
        entityGraphic.right1 = setupImage("ninja_right_1", entityGraphic.gp.tileSize, entityGraphic.gp.tileSize);
        entityGraphic.right2 = setupImage("ninja_right_2", entityGraphic.gp.tileSize, entityGraphic.gp.tileSize);
        entityGraphic.face = setupImage("face", entityGraphic.gp.tileSize * 2, entityGraphic.gp.tileSize * 2);
    }

    public void getPlayerAttackImage() {
        entityGraphic.attackUp1 = setupImage("ninja_attack_up_1", entityGraphic.gp.tileSize, entityGraphic.gp.tileSize);
        entityGraphic.attackUp2 = setupImage("ninja_attack_up_2", entityGraphic.gp.tileSize,
                entityGraphic.gp.tileSize * 2);
        entityGraphic.attackDown1 = setupImage("ninja_attack_down_1", entityGraphic.gp.tileSize,
                entityGraphic.gp.tileSize);
        entityGraphic.attackDown2 = setupImage("ninja_attack_down_2", entityGraphic.gp.tileSize,
                entityGraphic.gp.tileSize * 2);
        entityGraphic.attackLeft1 = setupImage("ninja_attack_left_1", entityGraphic.gp.tileSize,
                entityGraphic.gp.tileSize);
        entityGraphic.attackLeft2 = setupImage("ninja_attack_left_2", entityGraphic.gp.tileSize * 2,
                entityGraphic.gp.tileSize);
        entityGraphic.attackRight1 = setupImage("ninja_attack_right_1", entityGraphic.gp.tileSize,
                entityGraphic.gp.tileSize);
        entityGraphic.attackRight2 = setupImage("ninja_attack_right_2", entityGraphic.gp.tileSize * 2,
                entityGraphic.gp.tileSize);
    }

    public BufferedImage setupImage(String imageName, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/animation/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void update() {
        /*
         * Update with key handler
         */
        // Check if event is ocurring
        entityGraphic.gp.eHandler.checkEvent();
        // resume();
        if (attacking == true) {
            attacking();
        } else if (entityGraphic.keyH.upPressed == true || entityGraphic.keyH.downPressed == true
                || entityGraphic.keyH.leftPressed == true
                || entityGraphic.keyH.rightPressed == true || entityGraphic.keyH.shotKeyPressed == true) {
            // Change direction using WSAD keys
            if (entityGraphic.keyH.upPressed == true) {
                direction = "up";
            } else if (entityGraphic.keyH.downPressed == true) {
                direction = "down";
            } else if (entityGraphic.keyH.leftPressed == true) {
                direction = "left";
            } else if (entityGraphic.keyH.rightPressed == true) {
                direction = "right";
            }
            // Check the collision, default is false
            entityGraphic.collisionOn = false;
            entityGraphic.gp.cChecker.checkTile(this);
            // Check objects collision
            int objIndex = entityGraphic.gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
            // Check NPC collision
            int npcIndex = entityGraphic.gp.cChecker.checkEntity(this, entityGraphic.gp.npc);
            interactNPC(npcIndex);
            // Check monster collision
            int monsterIndex = entityGraphic.gp.cChecker.checkEntity(this, entityGraphic.gp.monster);
            contactMonster(monsterIndex);
            // If Collision is false then player can move
            if (entityGraphic.collisionOn == false) {
                switch (direction) {
                    case "up":
                        y -= speed;
                        break;
                    case "down":
                        y += speed;
                        break;
                    case "left":
                        x -= speed;
                        break;
                    case "right":
                        x += speed;
                        break;
                }
            }

            // Change image in 1 direction
            entityGraphic.spriteCounter++;
            if (entityGraphic.spriteCounter > 12) {
                if (entityGraphic.spriteNum == 1) {
                    entityGraphic.spriteNum = 2;
                } else if (entityGraphic.spriteNum == 2) {
                    entityGraphic.spriteNum = 1;
                }
                entityGraphic.spriteCounter = 0;
            }

            // If life < 0, player dies immedietely, game is over
            if (life < 0) {
                entityGraphic.gp.gameState = "die";
            }

            // ?
            if (entityGraphic.gp.keyH.shotKeyPressed == true && projectile.alive == false
                    && entityGraphic.shotAvailableCounter == 20) {

                if (entityGraphic.gp.player.mana <= 0) {
                    entityGraphic.gp.ui.showMessage("You don't have enough mana. Go to recuperate to restore");
                } else {
                    projectile.set(x, y + 10, 15, direction, true, this);
                    entityGraphic.gp.projectileList.add(projectile);
                    entityGraphic.shotAvailableCounter = 0;
                }
                entityGraphic.gp.player.mana -= 1;

            }

        }

        // Invincible latency
        if (invincible == true) {
            entityGraphic.invincibleCounter++;
            if (entityGraphic.invincibleCounter > 60) {
                invincible = false;
                entityGraphic.invincibleCounter = 0;
            }
        }
        // ?
        if (entityGraphic.shotAvailableCounter < 20) {
            entityGraphic.shotAvailableCounter++;
        }
    }

    public void attacking() {
        entityGraphic.spriteCounter++;

        if (entityGraphic.spriteCounter <= 5) {
            // Prepare the sword image
            entityGraphic.spriteNum = 1;
        }
        if (entityGraphic.spriteCounter > 5 && entityGraphic.spriteCounter <= 25) {
            // Release the sword image
            entityGraphic.spriteNum = 2;

            // Ajust .solidArea and axes to .attackArea to check collision for attacking
            // Save the current .x, .y, .solidArea
            int currentX = x;
            int currentY = y;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player's .x, .y for the .attackArea
            switch (direction) {
                case "up":
                    y -= attackArea.height;
                    break;
                case "down":
                    y += attackArea.height;
                    break;
                case "left":
                    x -= attackArea.width;
                    break;
                case "right":
                    x += attackArea.width;
                    break;
            }

            // .attackArea become .solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // Check monster collision with update worldX, worldY and solidArea
            int monsterIndex = entityGraphic.gp.cChecker.checkEntity(this, entityGraphic.gp.monster);
            damageMonster(monsterIndex, attack);

            // After checking collision, restore the original data
            x = currentX;
            y = currentY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (entityGraphic.spriteCounter > 25) {
            entityGraphic.spriteNum = 1;
            entityGraphic.spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = entityGraphic.gp.obj[i].name;
            switch (objectName) {
                case "Key":
                    hasKey++;
                    entityGraphic.gp.obj[i] = null;
                    entityGraphic.gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if (hasKey > 0) {
                        entityGraphic.gp.obj[i] = null;
                        hasKey--;
                        entityGraphic.gp.ui.showMessage("Door was opened!");
                    } else {
                        entityGraphic.gp.ui.showMessage("You need a key!");
                    }
                    break;
                case "Recuperate":
                    life = maxLife;
                    mana = maxMana;
                    projectile.alive = false;
                    entityGraphic.gp.ui.showMessage("You have recovered");
                    break;
                case "Chest":
                    entityGraphic.gp.obj[i] = null;
                    countChest++;
                    break;
                case "pokeball":
                    entityGraphic.gp.obj[i] = null;
                    entityGraphic.gp.ui.showMessage("You got pokeball");
                    break;
            }
        }
    }

    public void interactNPC(int i) {

        if (i != 999) {
            System.out.println("hitting npc");
            entityGraphic.gp.ui.showMessage("Pick up the key to open the door");
        }
    }

    public void contactMonster(int i) {
        /*
         * Hit or be attack by monster will get damage
         */
        if (i != 999) {
            System.out.println("hitting monster");
            entityGraphic.gp.ui.showMessage("Ouchhhh");
            if (invincible == false) {
                life -= 1;
                life -= attack;
                invincible = true;
            }

        }
    }

    public void damageMonster(int i, int attack) {
        if (i != 999) {

            if (entityGraphic.gp.monster[i].invincible == false) {
                // gp.player.mana -= 1;
                entityGraphic.gp.monster[i].life -= attack;
                entityGraphic.gp.monster[i].invincible = true;
                entityGraphic.gp.monster[i].damageReaction();
                if (entityGraphic.gp.monster[i].life <= 0) {
                    entityGraphic.gp.monster[i].dying = true;
                    entityGraphic.gp.monster[i] = null;
                    entityGraphic.gp.monsterCount -= 1;
                }
            }

        }
    }

    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white);
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (attacking == false) {
                    if (entityGraphic.spriteNum == 1) {
                        image = entityGraphic.up1;
                    }
                    if (entityGraphic.spriteNum == 2) {
                        image = entityGraphic.up2;
                    }
                }
                if (attacking == true) {
                    if (entityGraphic.spriteNum == 1) {
                        image = entityGraphic.attackUp1;
                    }
                    if (entityGraphic.spriteNum == 2) {
                        image = entityGraphic.attackUp2;
                    }
                }
                break;
            case "down":
                if (attacking == false) {
                    if (entityGraphic.spriteNum == 1) {
                        image = entityGraphic.down1;
                    }
                    if (entityGraphic.spriteNum == 2) {
                        image = entityGraphic.down2;
                    }
                }
                if (attacking == true) {
                    if (entityGraphic.spriteNum == 1) {
                        image = entityGraphic.attackDown1;
                    }
                    if (entityGraphic.spriteNum == 2) {
                        image = entityGraphic.attackDown2;
                    }
                }
                break;
            case "left":
                if (attacking == false) {
                    // x = defaultX;
                    if (entityGraphic.spriteNum == 1) {
                        image = entityGraphic.left1;
                    }
                    if (entityGraphic.spriteNum == 2) {
                        image = entityGraphic.left2;
                    }
                }
                if (attacking == true) {
                    // x = x - gp.tileSize;
                    if (entityGraphic.spriteNum == 1) {
                        image = entityGraphic.attackLeft1;
                    }
                    if (entityGraphic.spriteNum == 2) {
                        image = entityGraphic.attackLeft2;
                    }
                }
                break;
            case "right":
                if (attacking == false) {
                    if (entityGraphic.spriteNum == 1) {
                        image = entityGraphic.right1;
                    }
                    if (entityGraphic.spriteNum == 2) {
                        image = entityGraphic.right2;
                    }
                }
                if (attacking == true) {
                    if (entityGraphic.spriteNum == 1) {
                        image = entityGraphic.attackRight1;
                    }
                    if (entityGraphic.spriteNum == 2) {
                        image = entityGraphic.attackRight2;
                    }
                }
                break;
        }

        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        if (attacking == true && direction == "up") {
            g2.drawImage(image, x, y - entityGraphic.gp.tileSize, image.getWidth(), image.getHeight(), null);
        } else if (attacking == true && direction == "left") {
            g2.drawImage(image, x - entityGraphic.gp.tileSize, y, image.getWidth(), image.getHeight(), null);
        } else {
            g2.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
        }

        // reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}