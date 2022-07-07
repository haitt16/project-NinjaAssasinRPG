package entity;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "up";
        speed = 0;
        interacted = false;

        getImage();

    }

    public void getImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/oldman/oldman_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/oldman/oldman_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/oldman/oldman_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/oldman/oldman_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/oldman/oldman_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/oldman/oldman_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/oldman/oldman_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/oldman/oldman_right_2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter > 60 && interacted == false) {
            direction = "up";
        } else if (interacted == true) {
            direction = "down";
        }

    }

}
