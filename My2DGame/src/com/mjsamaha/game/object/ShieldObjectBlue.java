package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.common.Entity;

public class ShieldObjectBlue extends Entity {
	
	public ShieldObjectBlue(GamePanel gp) {
		super(gp);
		type = type_shield;
		name = "Blue Shield";
		
		down1 = setup("/objects/shield_blue", gp.tileSize, gp.tileSize);
		
		defenseValue = 2;
		
		description = "{" + name + "}\nA sturdy shield.";
	}

}
