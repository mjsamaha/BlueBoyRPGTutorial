package com.mjsamaha.game.entity;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.common.Entity;

public class Projectile extends Entity {
	
	Entity user;
	
	public Projectile(GamePanel gp) {
		super(gp);
	}
	
	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
		
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		this.health = this.maxHealth;
		
	}
	
	public void update() {
		
		if (user == gp.player) {
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			if (monsterIndex != 999) {
			    gp.monster[monsterIndex].takeDamage(user, attack);
			    alive = false;
			}
		}
		if (user != gp.player) {
			boolean contactPlayer = gp.cChecker.checkPlayer(this);
			if (gp.player.invincible == false && contactPlayer == true) {
				gp.player.takeDamage(user, attack);
			    alive = false;
			}
		}
		
		switch(direction) {
		case "up":
			worldY -= speed;
			break;
		case "down":
			worldY += speed;
			break;
		case "left":
			worldX -= speed;
			break;
		case "right":
			worldX += speed;
			break;
		}
		
		health--;
		if (health <= 0) {
			alive = false;
		}
		
		// delegate animation to the controller from Entity
		animationController.update();
	}
	
	
	

}
