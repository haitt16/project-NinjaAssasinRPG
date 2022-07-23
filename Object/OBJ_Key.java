package Object;

import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class OBJ_Key extends SuperObject {
	public OBJ_Key() {
		name = "Key";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/animation/objects/key.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2, GamePanel gp) {
		g2.drawImage(image, x, y, gp.tileSize - 10, gp.tileSize, null);
	}
}
