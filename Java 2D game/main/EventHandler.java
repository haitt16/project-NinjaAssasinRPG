package main;

import java.awt.Rectangle;

public class EventHandler {
	GamePanel gp;
	EventRect eventRect[][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;

		eventRect = new EventRect[gp.maxScreenCol][gp.maxScreenRow];
		int col = 0;
		int row = 0;
		while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

			col++;
			if(col == gp.maxScreenCol) {
				col = 0;
				row++;
			}
		}
	}
	public void checkEvent() {
		
		// check if the player character is more than 1 tile away from the last event
		
		int xDistance = Math.abs(gp.player.x - previousEventX);
		int yDistance = Math.abs(gp.player.y - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		if(distance > gp.tileSize) {
			canTouchEvent = true;
		}

		if(canTouchEvent == true) {
			if (hit(6, 10, "right") == true) {
				damagePit(6, 10);
			}
			/*if (hit(6, 5, "right") == true) {// Có đoạn code thay thế trong Method pickUpObject, class Player.java  
				healingPool();
			}*/
			
			if (hit(9, 11, "down") == true) {
				teleport();
			}
		}
		
	}
	public boolean hit(int col, int row, String reqDirection) {
		boolean hit = false;
		gp.player.solidArea.x += gp.player.x;
		gp.player.solidArea.y += gp.player.y;
		eventRect[col][row].x += col*gp.tileSize;
		eventRect[col][row].y += row*gp.tileSize;
		
		if(gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
			if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;

				previousEventX = gp.player.x;
				previousEventY = gp.player.y;
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
		
		return hit;
	}
	
	public void damagePit(int col, int row) {//Bẫy gây dame
		gp.ui.showMessage("You fall into a pit");
		gp.player.life -- ;
		//eventRect[col][row].eventDone = true;
		canTouchEvent = false;
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
