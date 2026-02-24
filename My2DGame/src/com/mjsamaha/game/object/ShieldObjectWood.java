package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.common.Entity;

public class ShieldObjectWood extends Entity {
	
	public ShieldObjectWood(GamePanel gp) {
		super(gp);
		
		name = "Wooden Shield";
		
		down1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
		
		defenseValue = 1;
		
		description = "{" + name + "}\nAn old shield.";

	}

}
