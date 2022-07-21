package Object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_POKEBALL extends SuperObject {
    public OBJ_POKEBALL() {
        name = "pokeball";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/tiles/pokeball.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        solidArea.x = 10;
    }

}
