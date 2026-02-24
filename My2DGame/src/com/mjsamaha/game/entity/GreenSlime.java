package com.mjsamaha.game.entity;

import java.util.Random;

import com.mjsamaha.game.Constants;
import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.common.Entity;

public class GreenSlime extends Entity {
	
	private Random random;
	
	public GreenSlime(GamePanel gp) {
		super(gp);
		
		this.name = "Green Slime";
		this.type = 2; // Monster type
		this.speed = Constants.Monster.SLIME_SPEED;
		this.maxHealth = Constants.Monster.SLIME_MAX_HEALTH;
		this.health = maxHealth;
		this.random = new Random();
		this.attack = 3;
		this.defense = 0;
		this.exp = 2;
		
		// Set collision box using constants
		solidArea.x = Constants.Monster.SLIME_COLLISION_X;
		solidArea.y = Constants.Monster.SLIME_COLLISION_Y;
		solidArea.width = Constants.Monster.SLIME_COLLISION_WIDTH;
		solidArea.height = Constants.Monster.SLIME_COLLISION_HEIGHT;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}
	
	/**
	 * Loads all sprite images for the green slime.
	 * Note: Green slimes use the same sprite for all directions.
	 */
	private void getImage() {
		up1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
	}
	
	/**
	 * AI behavior - randomly changes direction every 2 seconds
	 */
	@Override
	public void setAction() {
		actionLockCounter++;
		
		if (actionLockCounter >= Constants.Monster.ACTION_LOCK_DURATION) {
			int directionChoice = random.nextInt(100) + 1;
			
			if (directionChoice <= 25) {
				direction = "up";
			}
			else if (directionChoice <= 50) {
				direction = "down";
			}
			else if (directionChoice <= 75) {
				direction = "left";
			}
			else {
				direction = "right";
			}
			
			actionLockCounter = 0;
		}
	}
	
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gp.player.direction;
	}
}