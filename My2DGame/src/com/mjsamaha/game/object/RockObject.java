package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.Projectile;

public class RockObject extends Projectile {
	
	public RockObject(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Rock";
		
		speed = 8;
		
		maxHealth = 80;
		
		health = maxHealth;
		
		attack = 2;
		
		useCost = 1;
		
		alive = false;
		
		getImage();
		
	}
	
	public void getImage() {
		up1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
	}
	
	

}
