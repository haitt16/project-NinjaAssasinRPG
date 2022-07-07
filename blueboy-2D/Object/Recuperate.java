package Object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Recuperate extends SuperObject {
	public Recuperate() {
		name = "Recuperate";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/tiles/shield_blue.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		// collision = true;
	}
}
