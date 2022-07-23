package Object;

import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Pit extends SuperObject {
    GamePanel gp;

    public OBJ_Pit(GamePanel gp) {
        this.gp = gp;

        name = "Pit";
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/animation/objects/pit.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // collision = true;
    }

}
