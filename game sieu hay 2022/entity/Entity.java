package entity;//Nhân vật trong game(player and monster)

import java.awt.AlphaComposite;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Entity {
    public EntityGraphic entityGraphic;
    // Axes

    public int x, y;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;

    // Static

    public String name;

    public int speed;
    public String direction;
    public int maxLife;
    public int life;
    public int mana;
    public int maxMana;
    public Projectile projectile;
    public boolean invincible = false;
    public int attack = 1;

    public int useCost;

    // Dynamic

    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;

    public int type;
    int dyingCounter = 0;
    int hpBarCounter = 0;

    public Entity(EntityGraphic entityGraphic) {
        this.entityGraphic = entityGraphic;
    }

    public void damagePlayer(int attack) {
        if (entityGraphic.gp.player.invincible == false) {
            entityGraphic.gp.player.life -= attack;
            entityGraphic.gp.player.invincible = true;
        }
    }

    public void checkDimension() {
        if (y < 0) {
            y = 0;
        }
        if (y > (entityGraphic.gp.screenHeight - entityGraphic.gp.tileSize - 1)) {
            y = entityGraphic.gp.screenHeight - entityGraphic.gp.tileSize - 1;
        }
        if (x < 0) {
            x = 0;
        }
        if (x > (entityGraphic.gp.screenWidth - entityGraphic.gp.tileSize - 1)) {
            x = entityGraphic.gp.screenWidth - entityGraphic.gp.tileSize - 1;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Draw image with each .direction

        switch (direction) {
            case "up":
                if (entityGraphic.spriteNum == 1) {
                    image = entityGraphic.up1;
                }
                if (entityGraphic.spriteNum == 2) {
                    image = entityGraphic.up2;
                }
                break;
            case "down":
                if (entityGraphic.spriteNum == 1) {
                    image = entityGraphic.down1;
                }
                if (entityGraphic.spriteNum == 2) {
                    image = entityGraphic.down2;
                }
                break;
            case "left":
                if (entityGraphic.spriteNum == 1) {
                    image = entityGraphic.left1;
                }
                if (entityGraphic.spriteNum == 2) {
                    image = entityGraphic.left2;
                }
                break;
            case "right":
                if (entityGraphic.spriteNum == 1) {
                    image = entityGraphic.right1;
                }
                if (entityGraphic.spriteNum == 2) {
                    image = entityGraphic.right2;
                }
                break;
        }

        // Monster HP bar

        if (type == 2 && hpBarOn == true) {

            double oneScale = (double) entityGraphic.gp.tileSize / maxLife;
            double hpBarValue = oneScale * life;

            g2.setColor(new Color(35, 35, 35));
            g2.fillRect(x - 1, y - 16, entityGraphic.gp.tileSize + 2, 12);

            g2.setColor(new Color(255, 0, 30));
            g2.fillRect(x, y - 15, (int) hpBarValue, 10);

            hpBarCounter++;

            if (hpBarCounter > 600) {
                hpBarCounter = 0;
                hpBarOn = false;
            }
        }

        // if invincible image, turn on the HP bar animation

        if (invincible == true) {
            hpBarOn = true;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        if (dying == true) {
            dyingAnimation(g2);
        }

        g2.drawImage(image, x, y, entityGraphic.gp.tileSize, entityGraphic.gp.tileSize, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void dyingAnimation(Graphics2D g2) {

        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 8) {
            dying = false;
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public void setAction() {
        // Set action of entity
    }

    public void damageReaction() {
        // How does entity animated when get damage
    }

    public void updateAnimation() {
        /*
         * Count frame to change image in 1 direction and invincibe latency
         */
        entityGraphic.spriteCounter++;
        if (entityGraphic.spriteCounter > 12) {
            if (entityGraphic.spriteNum == 1) {
                entityGraphic.spriteNum = 2;
            } else if (entityGraphic.spriteNum == 2) {
                entityGraphic.spriteNum = 1;
            }
            entityGraphic.spriteCounter = 0;
        }

        if (invincible == true) {
            entityGraphic.invincibleCounter++;
            if (entityGraphic.invincibleCounter > 40) {
                invincible = false;
                entityGraphic.invincibleCounter = 0;
            }
        }
    }

    public void update() {
        setAction();

        entityGraphic.collisionOn = false;
        entityGraphic.gp.cChecker.checkTile(this);
        entityGraphic.gp.cChecker.checkObject(this, false);

        boolean contactPlayer = entityGraphic.gp.cChecker.checkPlayer(this);

        if (this.type == 2 && contactPlayer == true) {
            if (entityGraphic.gp.player.invincible == false) {
                entityGraphic.gp.player.life -= 1;
                entityGraphic.gp.player.invincible = true;
            }
        }

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
            checkDimension();
        }

        updateAnimation();

    }

}
