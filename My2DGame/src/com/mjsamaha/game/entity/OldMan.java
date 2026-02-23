package com.mjsamaha.game.entity;

import java.util.Random;

import com.mjsamaha.game.Constants;
import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.common.Entity;
import com.mjsamaha.game.entity.common.Interactive;

public class OldMan extends Entity implements Interactive {
	
	private Random random;
	
	public OldMan(GamePanel gp) {
		super(gp);
		
		this.type = 1; // NPC type
		this.direction = "down";
		this.speed = Constants.EntityArrays.DEFAULT_SPEED;
		this.random = new Random();
		
		getImage();
		setDialogue();
	}
	
	/**
	 * Loads all sprite images for the old man NPC
	 */
	private void getImage() {
		up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
	}
	
	/**
	 * Sets up the dialogue lines for this NPC
	 */
	private void setDialogue() {
		dialogue[0] = "Hello, young one.";
		dialogue[1] = "I used to be a great wizard \nbut now....";
		dialogue[2] = "If you're health is ever low, \ngo drink the water north of here.";
		dialogue[3] = "Good luck to you.";
	}
	
	/**
	 * AI behavior - randomly changes direction every 2 seconds
	 */
	@Override
	public void setAction() {
		actionLockCounter++;
		
		if (actionLockCounter >= Constants.NPC.ACTION_LOCK_DURATION) {
			int directionChoice = random.nextInt(100) + 1;
			
			if (directionChoice <= 25) {
				direction = "up";
			}
			else if (directionChoice <= 50) {
				direction = "down";
			}
			else if (directionChoice <= 75) {
				direction = "left";
			}
			else {
				direction = "right";
			}
			
			actionLockCounter = 0;
		}
	}
	
	/**
	 * Handles interaction with the player
	 */
	@Override
	public void speak() {
		// Character-specific behavior can go here
		// For now, just call the parent implementation
		super.speak();
	}
	
	// Interactive interface implementation
	@Override
	public String getCurrentDialogue() {
		if (dialogueIndex > 0 && dialogueIndex <= dialogue.length) {
			return dialogue[dialogueIndex - 1];
		}
		return dialogue[0];
	}
}