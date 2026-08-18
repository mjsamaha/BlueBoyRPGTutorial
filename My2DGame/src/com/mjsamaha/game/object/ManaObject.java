package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.object.common.GameObject;

public class ManaObject extends GameObject {
	
	public ManaObject(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Mana Crystal";
		image = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
	}
	
	

}
