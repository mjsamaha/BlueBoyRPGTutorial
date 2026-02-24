package com.mjsamaha.game.managers;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.GreenSlime;
import com.mjsamaha.game.entity.OldMan;
import com.mjsamaha.game.object.KeyObject;

public class AssetManager {
	
	GamePanel gp;
	
	public AssetManager(GamePanel gp) {
		
		this.gp = gp;
		
	}
	
	public void setObject() {

		gp.obj[0] = new KeyObject(gp);
		gp.obj[0].worldX = gp.tileSize*25;
		gp.obj[0].worldY = gp.tileSize*19;
	
	}
	
	public void setNPC() {
		
		gp.npc[0] = new OldMan(gp);
		gp.npc[0].worldX = gp.tileSize * 21;
		gp.npc[0].worldY = gp.tileSize * 21;
		
	    System.out.println("✅ Key placed at tile (24, 21) - should be visible immediately");

		
	}
	
	public void setMonster() {
		
		int i = 0;
		gp.monster[i] = new GreenSlime(gp);
		gp.monster[i].worldX = gp.tileSize * 23;
		gp.monster[i].worldY = gp.tileSize * 36;
		
		i++;
		
		gp.monster[i] = new GreenSlime(gp);
		gp.monster[i].worldX = gp.tileSize * 23;
		gp.monster[i].worldY = gp.tileSize * 42;
		i++;
		
		gp.monster[i] = new GreenSlime(gp);
		gp.monster[i].worldX = gp.tileSize * 24;
		gp.monster[i].worldY = gp.tileSize * 37;
		i++;
		
		gp.monster[i] = new GreenSlime(gp);
		gp.monster[i].worldX = gp.tileSize * 34;
		gp.monster[i].worldY = gp.tileSize * 42;
		i++;
		
		gp.monster[i] = new GreenSlime(gp);
		gp.monster[i].worldX = gp.tileSize * 38;
		gp.monster[i].worldY = gp.tileSize * 42;
		i++;
	}

}
