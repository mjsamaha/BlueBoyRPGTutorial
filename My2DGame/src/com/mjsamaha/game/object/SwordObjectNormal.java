package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.common.Entity;

public class SwordObjectNormal extends Entity {
	
	public SwordObjectNormal(GamePanel gp) {
		super(gp);
		
		type = type_sword;
		name = "Normal Sword";
		
		down1 = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
		
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
		
		description = "{" + name + "}\nAn old ordinary sword.";
	}

}
