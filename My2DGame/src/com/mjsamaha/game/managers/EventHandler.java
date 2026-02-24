package com.mjsamaha.game.managers;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.util.EventRect;

public class EventHandler {

	public GamePanel gp;
	public EventRect eventRect[][];

	public EventHandler(GamePanel gp) {
		this.gp = gp;

		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

		int col = 0;
		int row = 0;
		while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

			col++;
			if (col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}

	}

	public void checkEvent() {
	    // Debug: print when player is near the healing pool
	    if (Math.abs(gp.player.worldX / gp.tileSize - 23) <= 1 && 
	        Math.abs(gp.player.worldY / gp.tileSize - 12) <= 1) {
	        
	    }
	    
	    if(hit(23, 12, "up") == true) {
	        healingPool();
	    }
	}

	public boolean hit(int col, int row, String reqDirection) {

		boolean hit = false;

		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

		if (gp.player.solidArea.intersects(eventRect[col][row])) {
			if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
			}
		}

		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

		return hit;

	}

	private void healingPool() {
	    if (gp.keyH.confirmPressed) {
	        gp.stateManager.toDialogueState(); // Switch to dialogue state
	        gp.player.attackCancelled = true;
	        gp.ui.currentDialogue = "You drink the water.\nYour life is restored.";
	        gp.player.health = gp.player.maxHealth;

	        gp.aManager.setMonster();

	        gp.keyH.confirmPressed = false; // Reset confirm key
	    }
	}
}
