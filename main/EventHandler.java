package main;

import java.awt.Rectangle;

public class EventHandler {
	GamePanel gp;
	EventRect eventRect[][];

	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	int eventCounter = 0;

	public EventHandler(GamePanel gp) {
		this.gp = gp;

		eventRect = new EventRect[gp.maxScreenCol][gp.maxScreenRow];
		int col = 0;
		int row = 0;
		while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 20;
			eventRect[col][row].y = 20;
			eventRect[col][row].width = 8;
			eventRect[col][row].height = 8;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

			col++;
			if (col == gp.maxScreenCol) {
				col = 0;
				row++;
			}
		}
	}

	public void checkEvent() {

		// check if the player character is more than 1 tile away from the last event

		// int xDistance = Math.abs(gp.player.x - previousEventX);
		// int yDistance = Math.abs(gp.player.y - previousEventY);
		// int distance = Math.max(xDistance, yDistance);
		// if (distance > gp.tileSize) {
		// canTouchEvent = true;
		// }

		if (canTouchEvent == true) {
			if (gp.mapLevel == 0) {

				if (gp.drawPitCounter > 100 && gp.drawPitCounter < 150) {
					if (hit(6, 10) == true) {
						canTouchEvent = false;
						damagePit(6, 10);
					}
					if (hit(10, 5) == true) {
						canTouchEvent = false;
						damagePit(10, 5);
					}
				}
			}
			if (gp.mapLevel == 2) {
				if (gp.drawPitCounter > 100 && gp.drawPitCounter < 150) {
					if (hit(3, 2) == true) {
						canTouchEvent = false;
						damagePit(3, 2);
					}
					if (hit(4, 2) == true) {
						canTouchEvent = false;
						damagePit(4, 2);
					}
					if (hit(5, 2) == true) {
						canTouchEvent = false;
						damagePit(5, 2);
					}
					if (hit(4, 3) == true) {
						canTouchEvent = false;
						damagePit(4, 3);
					}
					if (hit(4, 4) == true) {
						canTouchEvent = false;
						damagePit(4, 4);
					}
					if (hit(4, 5) == true) {
						canTouchEvent = false;
						damagePit(4, 5);
					}
					if (hit(4, 6) == true) {
						canTouchEvent = false;
						damagePit(4, 6);
					}
					if (hit(9, 2) == true) {
						canTouchEvent = false;
						damagePit(9, 2);
					}
					if (hit(10, 2) == true) {
						canTouchEvent = false;
						damagePit(10, 2);
					}
					if (hit(11, 3) == true) {
						canTouchEvent = false;
						damagePit(11, 3);
					}
				}
			}
		} else {
			eventCounter++;
			if (eventCounter > 60) {
				canTouchEvent = true;
				eventCounter = 0;
			}
		}

		if (hit(9, 11) == true) {
			teleport();
		}
	}

	public boolean hit(int col, int row) {
		boolean hit = false;
		gp.player.solidArea.x += gp.player.x;
		gp.player.solidArea.y += gp.player.y;
		eventRect[col][row].x += col * gp.tileSize;
		eventRect[col][row].y += row * gp.tileSize;

		if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
			// if (gp.player.direction.contentEquals(reqDirection) ||
			// reqDirection.contentEquals("any")) {

			hit = true;

			previousEventX = gp.player.x;
			previousEventY = gp.player.y;
			System.out.println(hit);
			// }
		}

		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

		return hit;
	}

	public void damagePit(int col, int row) {// Bẫy gây dame
		gp.ui.showMessage("You fall into a pit");
		gp.player.life--;
		// eventRect[col][row].eventDone = true;
		// canTouchEvent = false;
		if (gp.player.life < 0) {
			gp.gameState = "die";
		}
	}

	/*
	 * public void healingPool() {
	 * gp.ui.showMessage("You drink the water.");
	 * if (gp.player.life != 6) {
	 * gp.player.life++;
	 * }
	 * }
	 */

	public void teleport() {// Dịch chuyển, phát triển lên có thể thành chuyển map
		if (gp.mapLevel == 0) {
			if (gp.player.countChest == 3 && gp.monsterCount == 0) {
				// gp.ui.showMessage("You Win");
				gp.gameState = "finish";
				gp.player.x = 1 * gp.tileSize;
				gp.player.y = 1 * gp.tileSize;
			} else if (gp.player.countChest < 3) {
				gp.ui.showMessage("You don't have enough Chest!");
				gp.player.y -= gp.tileSize;
			} else if (gp.monsterCount > 0) {
				gp.ui.showMessage("You must kill all the monsters!");
				gp.player.y -= gp.tileSize;
			}
		} else if (gp.mapLevel == 1) {
			if (gp.player.countChest == 2 && gp.monsterCount == 0) {
				// gp.ui.showMessage("You Win");
				gp.gameState = "finish";
				gp.player.x = 1 * gp.tileSize;
				gp.player.y = 1 * gp.tileSize;
			} else if (gp.player.countChest < 2) {
				gp.ui.showMessage("You don't have enough Chest!");
				gp.player.y -= gp.tileSize;
			} else if (gp.monsterCount > 0) {
				gp.ui.showMessage("You must kill all the monsters!");
				gp.player.y -= gp.tileSize;
			}
		} else if (gp.mapLevel == 2) {
			if (gp.player.countChest == 3 && gp.monsterCount == 0) {
				// gp.ui.showMessage("You Win");
				gp.gameState = "finish";
				gp.player.x = 1 * gp.tileSize;
				gp.player.y = 1 * gp.tileSize;
			} else if (gp.player.countChest < 3) {
				gp.ui.showMessage("You don't have enough Chest!");
				gp.player.y -= gp.tileSize;
			} else if (gp.monsterCount > 0) {
				gp.ui.showMessage("You must kill all the monsters!");
				gp.player.y -= gp.tileSize;
			}
		}
	}
}
