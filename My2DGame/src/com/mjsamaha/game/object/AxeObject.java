package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.Player;
import com.mjsamaha.game.object.common.Collectible;
import com.mjsamaha.game.object.common.GameObject;

public class AxeObject extends GameObject {
	
	public AxeObject(GamePanel gp) {
		super(gp);
		type = type_axe;
		name = "Axe";
		image = setup("/objects/axe", gp.tileSize, gp.tileSize);
		attackValue = 2;
		attackArea.width = 30;
		attackArea.height = 30;
		description = "[" + name + "]\nA tool used for chopping \nwood.";

	}


}
