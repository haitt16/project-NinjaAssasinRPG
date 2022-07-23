package Object;

import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Heart extends SuperObject {
	GamePanel gp;
	public BufferedImage blank, half, full;

	public OBJ_Heart(GamePanel gp) {
		this.gp = gp;

		name = "Heart";
		try {
			this.full = ImageIO.read(getClass().getResourceAsStream("/animation/objects/heart_full.png"));
			this.half = ImageIO.read(getClass().getResourceAsStream("/animation/objects/heart_half.png"));
			this.blank = ImageIO.read(getClass().getResourceAsStream("/animation/objects/heart_blank.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		// collision = true;
	}
}
