package com.mjsamaha.game.managers;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.MON_GreenSlime;
import com.mjsamaha.game.entity.NPC_Oldman;

public class AssetManager {
	
	GamePanel gp;
	
	public AssetManager(GamePanel gp) {
		
		this.gp = gp;
		
	}
	
	public void setObject() {

		
	
	}
	
	public void setNPC() {
		
		gp.npc[0] = new NPC_Oldman(gp);
		gp.npc[0].worldX = gp.tileSize * 21;
		gp.npc[0].worldY = gp.tileSize * 21;
		
	}
	
	public void setMonster() {
		
		gp.monster[0] = new MON_GreenSlime(gp);
		gp.monster[0].worldX = gp.tileSize * 23;
		gp.monster[0].worldY = gp.tileSize * 36;
		
		gp.monster[1] = new MON_GreenSlime(gp);
		gp.monster[1].worldX = gp.tileSize * 23;
		gp.monster[1].worldY = gp.tileSize * 37;
	}

}
