package entity;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import Object.*;
import main.GamePanel;

public class MON_Tanuki extends Entity {
    EntityGraphic projectileGraphic;

    public MON_Tanuki(EntityGraphic entityGraphic) {
        super(entityGraphic);
        type = 2;
        direction = "right";
        speed = 1;
        maxLife = 2;
        life = maxLife;
        projectileGraphic = new EntityGraphic(entityGraphic.gp);
        projectile = new PROJ_ROCK(projectileGraphic);
        getImage();
        attack = 2;
    }

    public void getImage() {
        /*
         * get image of monster
         */
        try {
            entityGraphic.up1 = ImageIO.read(getClass().getResourceAsStream("/animation/monster/tanuki_up_1.png"));
            entityGraphic.up2 = ImageIO.read(getClass().getResourceAsStream("/animation/monster/tanuki_up_2.png"));
            entityGraphic.down1 = ImageIO.read(getClass().getResourceAsStream("/animation/monster/tanuki_down_1.png"));
            entityGraphic.down2 = ImageIO.read(getClass().getResourceAsStream("/animation/monster/tanuki_down_2.png"));
            entityGraphic.left1 = ImageIO.read(getClass().getResourceAsStream("/animation/monster/tanuki_left_1.png"));
            entityGraphic.left2 = ImageIO.read(getClass().getResourceAsStream("/animation/monster/tanuki_left_2.png"));
            entityGraphic.right1 = ImageIO
                    .read(getClass().getResourceAsStream("/animation/monster/tanuki_right_1.png"));
            entityGraphic.right2 = ImageIO
                    .read(getClass().getResourceAsStream("/animation/monster/tanuki_right_2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAction() {
        /*
         * .actionLockCounter count to change direction of monster randomly
         */
        entityGraphic.actionLockCounter++;
        if (entityGraphic.actionLockCounter == 40) {
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
        int i = new Random().nextInt(100) + 1;
        if (i > 99 && projectile.alive == false) {
            projectile.set(x, y, 4, direction, true, this);
            entityGraphic.gp.projectileList.add(projectile);
        }

    }

    public void damageReaction() {
        entityGraphic.actionLockCounter = 0;
        direction = entityGraphic.gp.player.direction;
    }
}
