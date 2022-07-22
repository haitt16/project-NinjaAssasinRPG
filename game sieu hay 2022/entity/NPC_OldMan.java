package entity;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(EntityGraphic entityGraphic) {
        super(entityGraphic);

        direction = "right";
        speed = 0;

        getImage();

    }

    public void getImage() {
        try {
            entityGraphic.up1 = ImageIO.read(getClass().getResourceAsStream("/animation/oldman/oldman_up_1.png"));
            entityGraphic.up2 = ImageIO.read(getClass().getResourceAsStream("/animation/oldman/oldman_up_2.png"));
            entityGraphic.down1 = ImageIO.read(getClass().getResourceAsStream("/animation/oldman/oldman_down_1.png"));
            entityGraphic.down2 = ImageIO.read(getClass().getResourceAsStream("/animation/oldman/oldman_down_2.png"));
            entityGraphic.left1 = ImageIO.read(getClass().getResourceAsStream("/animation/oldman/oldman_left_1.png"));
            entityGraphic.left2 = ImageIO.read(getClass().getResourceAsStream("/animation/oldman/oldman_left_2.png"));
            entityGraphic.right1 = ImageIO.read(getClass().getResourceAsStream("/animation/oldman/oldman_right_1.png"));
            entityGraphic.right2 = ImageIO.read(getClass().getResourceAsStream("/animation/oldman/oldman_right_2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAction() {
        /*
         * .actionLockCounter count to change direction of monster randomly
         */
        entityGraphic.actionLockCounter++;
        if (entityGraphic.actionLockCounter == 70) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            entityGraphic.actionLockCounter = 0;
        }

    }

}
