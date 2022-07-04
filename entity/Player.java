package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Graphics2D;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

import background.TilesManage;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth / 2;
        screenY = gp.screenHeight / 2;

        solidArea = new Rectangle(12, 12, 16, 16);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValue();
        setPlayerImage("/animation/player/player_up1.png", "/animation/player/player_up2.png",
                "/animation/player/player_down1.png", "/animation/player/player_down2.png",
                "/animation/player/player_left1.png", "/animation/player/player_left2.png",
                "/animation/player/player_right1.png", "/animation/player/player_right2.png");
    }

    public void setDefaultValue() {
        worldX = gp.titleSize * 10;
        worldY = gp.titleSize * 12;
        HP = 100;
        MP = 100;
        speed = 4;
        direction = "down";
    }

    public void setPlayerImage(String up1_link, String up2_link, String down1_link, String down2_link,
            String left1_link, String left2_link, String right1_link, String right2_link) {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream(up1_link));
            up2 = ImageIO.read(getClass().getResourceAsStream(up2_link));
            down1 = ImageIO.read(getClass().getResourceAsStream(down1_link));
            down2 = ImageIO.read(getClass().getResourceAsStream(down2_link));
            left1 = ImageIO.read(getClass().getResourceAsStream(left1_link));
            left2 = ImageIO.read(getClass().getResourceAsStream(left2_link));
            right1 = ImageIO.read(getClass().getResourceAsStream(right1_link));
            right2 = ImageIO.read(getClass().getResourceAsStream(right2_link));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
                || keyH.rightPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";

            } else if (keyH.leftPressed == true) {
                direction = "left";

            } else if (keyH.rightPressed == true) {
                direction = "right";

            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickupObject(objIndex);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;

                }
            }

            spriteCounter++;
            if (spriteCounter > 20) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else
                    spriteNum = 1;
                spriteCounter = 0;
            }
        }
    }

    public void pickupObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "pokeball":
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMassage("You got a pokeball!");
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                    break;
                } else {
                    image = up2;
                    break;
                }
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                    break;
                } else {
                    image = down2;
                    break;
                }
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                    break;
                } else {
                    image = left2;
                    break;
                }
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                    break;
                } else {
                    image = right2;
                    break;
                }

        }
        g2.drawImage(image, screenX, screenY, gp.titleSize, gp.titleSize, null);
    }
}
