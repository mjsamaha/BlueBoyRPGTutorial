package com.mjsamaha.game.managers;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.mjsamaha.game.Constants;
import com.mjsamaha.game.entity.Entity;
import com.mjsamaha.game.entity.Player;

public class EntityManager {
	
	// Entity arrays
	public Entity[] objects;
	public Entity[] npcs;
	public Entity[] monsters;
	private ArrayList<Entity> entityList;
	
	// Reference to player
	private Player player;
	
	public EntityManager() {
		this.objects = new Entity[Constants.EntityArrays.MAX_OBJECTS];
		this.npcs = new Entity[Constants.EntityArrays.MAX_NPC];
		this.monsters = new Entity[Constants.EntityArrays.MAX_MONSTERS];
		this.entityList = new ArrayList<>();
	}
	
	/**
	 * Set the player reference
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * Update all entities (NPCs and monsters)
	 */
	public void updateEntities() {
		// Update NPCs
		for (int i = 0; i < npcs.length; i++) {
			if (npcs[i] != null) {
				npcs[i].update();
			}
		}
		
		// Update monsters
		for (int i = 0; i < monsters.length; i++) {
			if (monsters[i] != null) {
				monsters[i].update();
			}
		}
	}
	
	/**
	 * Draw all entities sorted by Y position
	 */
	public void drawEntities(Graphics2D g2) {
		entityList.clear();
		
		// Add player
		if (player != null) {
			entityList.add(player);
		}
		
		// Add NPCs
		for (int i = 0; i < npcs.length; i++) {
			if (npcs[i] != null) {
				entityList.add(npcs[i]);
			}
		}
		
		// Add objects
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] != null) {
				entityList.add(objects[i]);
			}
		}
		
		// Add monsters
		for (int i = 0; i < monsters.length; i++) {
			if (monsters[i] != null) {
				entityList.add(monsters[i]);
			}
		}
		
		// Sort by Y position for proper rendering
		Collections.sort(entityList, new Comparator<Entity>() {
			@Override
			public int compare(Entity e1, Entity e2) {
				return Integer.compare(e1.worldY, e2.worldY);
			}
		});
		
		// Draw all entities
		for (Entity entity : entityList) {
			entity.draw(g2);
		}
	}
	
	/**
	 * Get objects array
	 */
	public Entity[] getObjects() {
		return objects;
	}
	
	/**
	 * Get NPCs array
	 */
	public Entity[] getNpcs() {
		return npcs;
	}
	
	/**
	 * Get monsters array
	 */
	public Entity[] getMonsters() {
		return monsters;
	}
}
