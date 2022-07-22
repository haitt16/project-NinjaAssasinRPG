package entity;

import main.GamePanel;

public class Projectile extends Entity {
	/*
	 * Throwing object from entity
	 */
	Entity user;

	public Projectile(EntityGraphic entityGraphic) {
		super(entityGraphic);
		solidArea.x = 16;
		solidArea.y = 16;
		solidArea.width = 16;
		solidArea.height = 16;
	}

	public void set(int x, int y, int speed, String direction, boolean alive, Entity user) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		this.life = this.maxLife;
		this.speed = speed;
	}

	public void update() {
		if (user == entityGraphic.gp.player) {
			// If .user is player, player cause a damage to monster

			int monsterIndex = entityGraphic.gp.cChecker.checkEntity(this, entityGraphic.gp.monster);
			if (monsterIndex != 999) {
				entityGraphic.gp.player.damageMonster(monsterIndex, attack);
				alive = false; // ?
			}
		} else if (user != entityGraphic.gp.player) {
			// else others entity cause damage to player
			boolean contactPlayer = entityGraphic.gp.cChecker.checkPlayer(this);
			if (entityGraphic.gp.player.invincible == false && contactPlayer == true) {
				// if player is not invincible, he got a damage by a entity
				damagePlayer(attack);
				alive = false; // ?
			}
		}
		entityGraphic.collisionOn = false;
		entityGraphic.gp.cChecker.checkTile(this);
		// gp.cChecker.checkObject(this, false);

		if (entityGraphic.collisionOn == false) {
			switch (direction) {
				case "up":
					y -= speed;
					break;
				case "down":
					y += speed;
					break;
				case "left":
					x -= speed;
					break;
				case "right":
					x += speed;
					break;
			}
		}
		else {
			alive=false;
		}

		// ?
		life--;
		if (life <= 0) {
			alive = false;
		}
		entityGraphic.spriteCounter++;
		if (entityGraphic.spriteCounter > 12) {
			if (entityGraphic.spriteNum == 1) {
				entityGraphic.spriteNum = 2;

			} else if (entityGraphic.spriteNum == 2) {
				entityGraphic.spriteNum = 1;
			}
			entityGraphic.spriteCounter = 0;
		}
	}
}
