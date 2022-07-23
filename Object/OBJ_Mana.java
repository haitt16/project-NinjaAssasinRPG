package Object;

import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Mana extends SuperObject {

    GamePanel gp;
    public BufferedImage blank, full;

    public OBJ_Mana(GamePanel gp) {
        this.gp = gp;

        name = "Mana";
        try {
            this.full = ImageIO.read(getClass().getResourceAsStream("/animation/objects/mana_full.png"));
            this.blank = ImageIO.read(getClass().getResourceAsStream("/animation/objects/mana_blank.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        // collision = true;
    }

}
