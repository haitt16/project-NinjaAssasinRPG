package Object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile {
	GamePanel gp;
	public OBJ_Rock(GamePanel gp) {
		super(gp);
		this.gp=gp;
		
		name="Rock";
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
			up1=ImageIO.read(getClass().getResourceAsStream("/tiles/rock_down_1.png"));
			up2=ImageIO.read(getClass().getResourceAsStream("/tiles/rock_down_1.png"));
			down1=ImageIO.read(getClass().getResourceAsStream("/tiles/rock_down_1.png"));
			down2=ImageIO.read(getClass().getResourceAsStream("/tiles/rock_down_1.png"));
			left1=ImageIO.read(getClass().getResourceAsStream("/tiles/rock_down_1.png"));
			left2=ImageIO.read(getClass().getResourceAsStream("/tiles/rock_down_1.png"));
			right1=ImageIO.read(getClass().getResourceAsStream("/tiles/rock_down_1.png"));
			right2=ImageIO.read(getClass().getResourceAsStream("/tiles/rock_down_1.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
}

}