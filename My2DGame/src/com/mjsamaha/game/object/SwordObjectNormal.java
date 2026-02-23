package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.common.Entity;

public class SwordObjectNormal extends Entity {
	
	public SwordObjectNormal(GamePanel gp) {
		super(gp);
		
		name = "Normal Sword";
		
		down1 = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
		
		attackValue = 1;
	}

}
