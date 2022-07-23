package Object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Recuperate extends SuperObject {

	public Recuperate() {
		name = "Recuperate";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/animation/objects/shield_blue.png"));
			// uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}
}
