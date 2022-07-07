package Object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Hut extends SuperObject {
    public OBJ_Hut() {
        name = "Hut";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/tiles/hut.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
