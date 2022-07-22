package main;

import entity.Entity;

public class CollisionChecker {// Kiểm tra va chạm với nước, với tường, ...

	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Entity entity) {
		int entityLeftX = entity.x + entity.solidArea.x;
		int entityRightX = entity.x + entity.solidArea.x + entity.solidArea.width;
		int entityTopY = entity.y + entity.solidArea.y;
		int entityBottomY = entity.y + entity.solidArea.y + entity.solidArea.height;

		int entityLeftCol = entityLeftX / gp.tileSize;
		int entityRightCol = entityRightX / gp.tileSize;
		int entityTopRow = entityTopY / gp.tileSize;
		int entityBottomRow = entityBottomY / gp.tileSize;

		int tileNum1, tileNum2;

		switch (entity.direction) {
			case "up":
				entityTopRow = (entityTopY - entity.speed) / gp.tileSize;
				try {
					tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
					tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
					if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
						entity.entityGraphic.collisionOn = true;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
				}
				break;
			case "down":
				entityBottomRow = (entityBottomY + entity.speed) / gp.tileSize;
				try {
					tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
					tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
					if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
						entity.entityGraphic.collisionOn = true;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
				}

				break;
			case "left":
				entityLeftCol = (entityLeftX - entity.speed) / gp.tileSize;
				try {
					tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
					tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
					if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
						entity.entityGraphic.collisionOn = true;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
				}
				break;
			case "right":
				entityRightCol = (entityRightX + entity.speed) / gp.tileSize;
				try {
					tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
					tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
					if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
						entity.entityGraphic.collisionOn = true;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
				}
				break;
		}
	}

	public int checkObject(Entity entity, boolean player) {
		int index = 999;
		for (int i = 0; i < gp.obj.length; i++) {
			if (gp.obj[i] != null) {
				entity.solidArea.x += entity.x;
				entity.solidArea.y += entity.y;

				gp.obj[i].solidArea.x += gp.obj[i].x;
				gp.obj[i].solidArea.y += gp.obj[i].y;

				switch (entity.direction) {
					case "up":
						entity.solidArea.y -= entity.speed;
						break;
					case "down":
						entity.solidArea.y += entity.speed;
						break;
					case "left":
						entity.solidArea.x -= entity.speed;
						break;
					case "right":
						entity.solidArea.x += entity.speed;
						break;
				}

				if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
					if (gp.obj[i].collision == true) {
						entity.entityGraphic.collisionOn = true;
					}
					if (player == true) {
						index = i;
					}
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
			}
		}
		return index;
	}

	// Npc or monster
	public int checkEntity(Entity entity, Entity[] target) {
		int index = 999;
		for (int i = 0; i < target.length; i++) {
			if (target[i] != null) {
				entity.solidArea.x += entity.x;
				entity.solidArea.y += entity.y;

				target[i].solidArea.x += target[i].x;
				target[i].solidArea.y += target[i].y;

				switch (entity.direction) {
					case "up":
						entity.solidArea.y -= entity.speed;
						break;
					case "down":
						entity.solidArea.y += entity.speed;
						break;
					case "left":
						entity.solidArea.x -= entity.speed;
						break;
					case "right":
						entity.solidArea.x += entity.speed;
						break;
				}
				if (entity.solidArea.intersects(target[i].solidArea)) {
					if (target[i] != entity) {
						entity.entityGraphic.collisionOn = true;
						index = i;
					}

				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
			}
		}
		return index;
	}

	public boolean checkPlayer(Entity entity) {
		boolean contactPlayer = false;
		entity.solidArea.x += entity.x;
		entity.solidArea.y += entity.y;

		gp.player.solidArea.x += gp.player.x;
		gp.player.solidArea.y += gp.player.y;

		switch (entity.direction) {
			case "up":
				entity.solidArea.y -= entity.speed;
				break;
			case "down":
				entity.solidArea.y += entity.speed;
				break;
			case "left":
				entity.solidArea.x -= entity.speed;
				break;
			case "right":
				entity.solidArea.x += entity.speed;
				break;
		}
		if (entity.solidArea.intersects(gp.player.solidArea)) {
			entity.entityGraphic.collisionOn = true;
			contactPlayer = true;
		}
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;

		return contactPlayer;
	}

}
