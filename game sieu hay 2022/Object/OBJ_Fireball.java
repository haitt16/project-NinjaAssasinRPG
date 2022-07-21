package Object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.EntityGraphic;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile  {
	GamePanel gp;
	public OBJ_Fireball(EntityGraphic entityGraphic) {
		super(entityGraphic);
		name="Fireball";
		speed=5;
		maxLife=80;
		life= maxLife;
		attack=2;
		useCost=1;
		alive=false;
		getImage();
	}
	public void getImage() {
		try {
			entityGraphic.up1=ImageIO.read(getClass().getResourceAsStream("/tiles/fireball_up_1.png"));
			entityGraphic.up2=ImageIO.read(getClass().getResourceAsStream("/tiles/fireball_up_2.png"));
			entityGraphic.down1=ImageIO.read(getClass().getResourceAsStream("/tiles/fireball_down_1.png"));
			entityGraphic.down2=ImageIO.read(getClass().getResourceAsStream("/tiles/fireball_down_2.png"));
			entityGraphic.left1=ImageIO.read(getClass().getResourceAsStream("/tiles/fireball_left_1.png"));
			entityGraphic.left2=ImageIO.read(getClass().getResourceAsStream("/tiles/fireball_left_2.png"));
			entityGraphic.right1=ImageIO.read(getClass().getResourceAsStream("/tiles/fireball_right_1.png"));
			entityGraphic.right2=ImageIO.read(getClass().getResourceAsStream("/tiles/fireball_right_2.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
}

}