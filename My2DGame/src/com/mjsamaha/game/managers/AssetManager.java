package com.mjsamaha.game.managers;

import java.util.logging.Logger;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.GreenSlime;
import com.mjsamaha.game.entity.OldMan;
import com.mjsamaha.game.object.AxeObject;
import com.mjsamaha.game.object.KeyObject;
import com.mjsamaha.game.object.ShieldObjectWood;

public class AssetManager {
	
	private static final Logger LOGGER = Logger.getLogger(AssetManager.class.getSimpleName());

	
	GamePanel gp;
	
	public AssetManager(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void setObject() {

		int i = 0;
		gp.obj[i] = new KeyObject(gp);
		gp.obj[i].worldX = gp.tileSize * 25;
		gp.obj[i].worldY = gp.tileSize * 23;
		i++;
		
		gp.obj[i] = new KeyObject(gp);
		gp.obj[i].worldX = gp.tileSize * 21;
		gp.obj[i].worldY = gp.tileSize * 19;
		i++;
		
		gp.obj[i] = new AxeObject(gp);
		gp.obj[i].worldX = gp.tileSize * 33;
		gp.obj[i].worldY = gp.tileSize * 21;
		i++;
		
		gp.obj[i] = new ShieldObjectWood(gp);
		gp.obj[i].worldX = gp.tileSize * 35;
		gp.obj[i].worldY = gp.tileSize * 21;
		i++;
		
		
	
	}
	
	public void setNPC() {
		
		gp.npc[0] = new OldMan(gp);
		gp.npc[0].worldX = gp.tileSize * 21;
		gp.npc[0].worldY = gp.tileSize * 21;
		LOGGER.info("NPCs set: OldMan at (21, 21)");
		

		
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
