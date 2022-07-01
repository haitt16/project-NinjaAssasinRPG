package main;

import java.awt.Rectangle;

public class EventHandler {
	GamePanel gp;
	Rectangle eventRect;
	int eventRectangleDefaultX, eventRectangleDefaultY;
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		eventRect = new Rectangle();
		eventRect.x = 23;
		eventRect.y = 23;
		eventRect.width = 2;
		eventRect.height = 2;
		eventRectangleDefaultX = eventRect.x;
		eventRectangleDefaultY = eventRect.y;
	}
	public void checkEvent() {
		if (hit(6, 10, "right") == true) {
			damagePit();
		}
		/*if (hit(6, 5, "right") == true) {// Có đoạn code thay thế trong Method pickUpObject, class Player.java  
			healingPool();
		}*/
		
		if (hit(9, 11, "down") == true) {
			teleport();
		}
		
	}
	public boolean hit(int eventCol, int eventRow, String reqDirection) {
		boolean hit = false;
		gp.player.solidArea.x += gp.player.x;
		gp.player.solidArea.y += gp.player.y;
		eventRect.x += eventCol*gp.tileSize;
		eventRect.y += eventRow*gp.tileSize;
		
		if(gp.player.solidArea.intersects(eventRect)) {
			if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect.x = eventRectangleDefaultX;
		eventRect.y = eventRectangleDefaultY;
		
		return hit;
	}
	
	public void damagePit() {//Bẫy gây dame
		gp.ui.showMessage("You fall into a pit");
		gp.player.life -- ;
	}
	
	/*public void healingPool() {
		gp.ui.showMessage("You drink the water.");
		if (gp.player.life != 6) {
			gp.player.life++;
		}
	}*/
	
	public void teleport() {//Dịch chuyển, phát triển lên có thể thành chuyển map
		if (gp.player.countChest == 3) {
			gp.ui.showMessage("You Win");
			gp.player.x -= 7 * gp.tileSize;
			gp.player.y -= 9 * gp.tileSize;
		}
		else {
			gp.ui.showMessage("You don't have enough Chest!");
			gp.player.y -=  gp.tileSize;
		}
	}
}
