package main;

import Object.OBJ_Chest;
import Object.OBJ_Door;
import Object.OBJ_Key;
import Object.Recuperate;

public class AssetSetter {
	GamePanel gp;
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject(){//Vật phẩm và các đối tượng đặc biệt như Door, Recuperate
		gp.obj[0] = new OBJ_Key();
		gp.obj[0].x = 3 * gp.tileSize;
		gp.obj[0].y = 9 * gp.tileSize;
		
		gp.obj[1] = new OBJ_Key();
		gp.obj[1].x = 4 * gp.tileSize;
		gp.obj[1].y = 9 * gp.tileSize;
		
		gp.obj[2] = new OBJ_Key();
		gp.obj[2].x = 5 * gp.tileSize;
		gp.obj[2].y = 9 * gp.tileSize;	
		
		gp.obj[3] = new OBJ_Door();
		gp.obj[3].x = 13 * gp.tileSize;
		gp.obj[3].y = 3 * gp.tileSize;
		
		gp.obj[4] = new OBJ_Door();
		gp.obj[4].x = 9 * gp.tileSize;
		gp.obj[4].y = 7 * gp.tileSize;
		
		gp.obj[5] = new OBJ_Door();
		gp.obj[5].x = 13 * gp.tileSize;
		gp.obj[5].y = 7 * gp.tileSize;
		
		gp.obj[6] = new OBJ_Chest();
		gp.obj[6].x = 13 * gp.tileSize;
		gp.obj[6].y = 5 * gp.tileSize;
		
		gp.obj[7] = new OBJ_Chest();
		gp.obj[7].x = 9 * gp.tileSize;
		gp.obj[7].y = 9 * gp.tileSize;
		
		gp.obj[8] = new OBJ_Chest();
		gp.obj[8].x = 13 * gp.tileSize;
		gp.obj[8].y = 9 * gp.tileSize;
		
		gp.obj[9] = new Recuperate();
		gp.obj[9].x = 6 * gp.tileSize;
		gp.obj[9].y = 5 * gp.tileSize;
	}
}
