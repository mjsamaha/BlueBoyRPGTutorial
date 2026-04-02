package com.mjsamaha.game.entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.mjsamaha.game.Constants;
import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.audio.SoundEvent;
import com.mjsamaha.game.entity.common.AnimationController;
import com.mjsamaha.game.entity.common.Entity;
import com.mjsamaha.game.managers.KeyHandler;
import com.mjsamaha.game.object.HeartObject;
import com.mjsamaha.game.object.KeyObject;
import com.mjsamaha.game.object.ShieldObjectWood;
import com.mjsamaha.game.object.SwordObjectNormal;
import com.mjsamaha.game.object.common.Collectible;
import com.mjsamaha.game.object.common.Usable;


public class Player extends Entity {
	private static final Logger LOGGER = Logger.getLogger(Player.class.getSimpleName());
	
	private KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public HeartObject life;
	public HeartObject maxLife;
	public int keys = 0;
	
	public ArrayList<Entity> inventory = new ArrayList<>(); // Player inventory
	public final int maxInventorySize = 20; // Max inventory size
	
	// Attack animation controller (separate from normal animation)
	private AnimationController attackAnimController;
	
	public boolean attackCancelled = false; // Flag to allow attack cancellation
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;
		
		// Calculate screen center position
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
		
		// Set collision box using constants
		solidArea = new Rectangle();
		solidArea.x = Constants.Player.COLLISION_BOX_X;
		solidArea.y = Constants.Player.COLLISION_BOX_Y;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = Constants.Player.COLLISION_BOX_WIDTH;
		solidArea.height = Constants.Player.COLLISION_BOX_HEIGHT;
		
		// Attack area
//		attackArea.width = 36;
//		attackArea.height = 36;
		
		// Create separate animation controller for attacks
		attackAnimController = new AnimationController(5); // Faster attack animation
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
	    LOGGER.info("Player initialized at x=" + worldX + ", y=" + worldY + ", HP=" + health);
	}
	
	/**
	 * Sets default player values using constants
	 */
	private void setDefaultValues() {
		worldX = Constants.Player.START_WORLD_X;
		worldY = Constants.Player.START_WORLD_Y;
		speed = Constants.Player.SPEED;
		direction = "down";
		type = 0; // Player type
		
		maxHealth = Constants.Player.MAX_HEALTH;
		health = maxHealth;
		
		
		level = 1;
		strength = 1;
		dexterity = 1;
		exp = 0;
		nextLevelExp = 5;
		coins = 0;
		currentWeapon = new SwordObjectNormal(gp);
		currentShield = new ShieldObjectWood(gp);
		attack = getAttack();
		defense = getDefense();
		
	}
	
	public void setItems() {
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		
		inventory.add(new KeyObject(gp));

	}
	
	public int getAttack() {
		attackArea = currentWeapon.attackArea; // Update attack area based on current weapon
		return attack = strength * currentWeapon.attackValue;
	}
	
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}
	
	/**
	 * Loads all player movement sprites
	 */
	private void getPlayerImage() {
		up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);

		down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);

		left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);

		right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
	}
	
	/**
	 * Loads all player attack sprites
	 */
	private void getPlayerAttackImage() {
		// Vertical attacks - normal width, double height
		attackUp1 = setup("/player/attacking/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
		attackUp2 = setup("/player/attacking/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
		
		attackDown1 = setup("/player/attacking/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
		attackDown2 = setup("/player/attacking/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
		
		// Horizontal attacks - double width, normal height
		attackLeft1 = setup("/player/attacking/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
		attackLeft2 = setup("/player/attacking/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
		
		attackRight1 = setup("/player/attacking/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
		attackRight2 = setup("/player/attacking/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
	}
	
	@Override
	public void update() {
	    // Check for character screen toggle (handle this first, before other inputs)
		if (keyH.characterStatePressed && !attacking) {
	        gp.ui.getState().characterScreenActive = !gp.ui.getState().characterScreenActive;
	        keyH.characterStatePressed = false;
	        return;
	    }
	    
		// Handle selection inside inventory
	    if (gp.ui.getState().characterScreenActive && keyH.confirmPressed) {
	        selectItem();
	        keyH.confirmPressed = false;
	    }
	    
	    // Handle attack input
	    if (keyH.confirmPressed && !attacking) {
	        attacking = true;
	        attackCancelled = false;
	        gp.playSE(SoundEvent.SFX_SWORD_SWING); // Optional: add swing sound
	    }

	    if (attacking) {
	        attack();
	    }
	    else if (isMoving()) {
	        handleMovement();
	        animationController.update();
	    }
	    
	    gp.eHandler.checkEvent(); // Check for events after movement

	    int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
	    interactNPC(npcIndex);

	    // ADD THIS LINE - Check for monster contact and take damage
	    int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
	    contactMonster(monsterIndex);

	    updateInvincibility();
	}
	
	/**
	 * Checks if any movement keys are pressed
	 */
	private boolean isMoving() {
		return keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;
	}
	
	/**
	 * Handles player movement with diagonal support
	 */
	private void handleMovement() {
		// Determine movement direction(s)
		boolean movingVertically = false;
		boolean movingHorizontally = false;
		
		// Handle vertical movement
		if (keyH.upPressed && !keyH.downPressed) {
			direction = "up";
			movingVertically = true;
		}
		else if (keyH.downPressed && !keyH.upPressed) {
			direction = "down";
			movingVertically = true;
		}
		
		// Handle horizontal movement
		if (keyH.leftPressed && !keyH.rightPressed) {
			if (!movingVertically) {
				direction = "left";
			}
			movingHorizontally = true;
		}
		else if (keyH.rightPressed && !keyH.leftPressed) {
			if (!movingVertically) {
				direction = "right";
			}
			movingHorizontally = true;
		}
		
		// Calculate diagonal speed using constant
		int moveSpeedX = speed;
		int moveSpeedY = speed;
		
		if (movingVertically && movingHorizontally) {
			moveSpeedX = (int)(speed * Constants.Player.DIAGONAL_SPEED_MULTIPLIER);
			moveSpeedY = (int)(speed * Constants.Player.DIAGONAL_SPEED_MULTIPLIER);
		}
		
		// Handle vertical movement
		if (movingVertically) {
			collisionOn = false;
			gp.cChecker.checkTile(this);
			int objIndex = gp.cChecker.checkObject(this, true);
			gp.cChecker.checkEntity(this, gp.npc);
			gp.cChecker.checkEntity(this, gp.monster);
			
			if (!collisionOn) {
				if (keyH.upPressed) {
					worldY -= moveSpeedY;
				} else if (keyH.downPressed) {
					worldY += moveSpeedY;
				}
			}
			
			if (objIndex != 999) {
				pickupObject(objIndex);
			}
		}
		
		// Handle horizontal movement
		if (movingHorizontally) {
			collisionOn = false;
			
			// Temporarily change direction for horizontal collision check
			String originalDirection = direction;
			if (keyH.leftPressed) {
				direction = "left";
			} else if (keyH.rightPressed) {
				direction = "right";
			}
			
			gp.cChecker.checkTile(this);
			int objIndex = gp.cChecker.checkObject(this, true);
			gp.cChecker.checkEntity(this, gp.npc);
			gp.cChecker.checkEntity(this, gp.monster);
			
			// Restore original direction for sprite display
			direction = originalDirection;
			
			if (!collisionOn) {
				if (keyH.leftPressed) {
					worldX -= moveSpeedX;
				} else if (keyH.rightPressed) {
					worldX += moveSpeedX;
				}
			}
			
			if (objIndex != 999) {
				pickupObject(objIndex);
			}
		}
		
		
		
		
	}
	
	private void contactMonster(int i) {
		if (i != 999) { // 999 means no collision
	        if (!invincible && !gp.monster[i].dying) {
	            // Play damage sound
	            gp.playSE(SoundEvent.SFX_RECEIVE_DAMAGE);
	            
	            int damage = gp.monster[i].attack - defense;
	            if (damage < 0) {
	                damage = 1; // Always take at least 1 damage
	            }
	            
	            health -= damage;
	            LOGGER.info("Player took " + damage + " damage, HP now " + health);
	            
	            gp.ui.addMessage(damage + " damage taken!");
	            
	            invincible = true;
	            
	            // Optional: Check if player died
	            if (health <= 0) {
	                health = 0;
	                // Handle game over here
	                gp.ui.getState().dialogueActive = true;
	                gp.ui.currentDialogue = "You died!";
	                LOGGER.info("Player has died!");
	            }
	        }
	    }
	}
	
	/**
	 * Handles attack animation and damage detection
	 */
	private void attack() {
	    attackAnimController.setSpriteCounter(attackAnimController.getSpriteCounter() + 1);
	    
	    if (attackAnimController.getSpriteCounter() <= 5) {
	        attackAnimController.setSpriteNum(1);
	    }
	    if (attackAnimController.getSpriteCounter() > 5 && attackAnimController.getSpriteCounter() <= 25) {
	        attackAnimController.setSpriteNum(2);
	        
	        // Save current position and size
	        int currentWorldX = worldX;
	        int currentWorldY = worldY;
	        int solidAreaWidth = solidArea.width;
	        int solidAreaHeight = solidArea.height;
	        
	        // Extend hitbox in attack direction
	        switch(direction) {
	            case "up":
	                worldY -= attackArea.height;
	                break;
	            case "down":
	                worldY += attackArea.height;
	                break;
	            case "left":
	                worldX -= attackArea.width;
	                break;
	            case "right":
	                worldX += attackArea.width;
	                break;
	        }
	        
	        solidArea.width = attackArea.width;
	        solidArea.height = attackArea.height;
	        
	        // Check for monster hit
	        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
	        
	        // Restore position and size
	        worldX = currentWorldX;
	        worldY = currentWorldY;
	        solidArea.width = solidAreaWidth;
	        solidArea.height = solidAreaHeight;
	        
	        damageMonster(monsterIndex);
	        // contactMonster(monsterIndex);
	    }
	    
	    if (attackAnimController.getSpriteCounter() > 25) {
	        attackAnimController.reset();
	        attacking = false;
	    }
	}
	
	
	
	/**
	 * Applies damage to a monster
	 */
	private void damageMonster(int i) {
		if (i != 999) {
			if (gp.monster[i].invincible == false) {
				
				// Play hit sound
				gp.playSE(SoundEvent.SFX_HIT_MONSTER);
				
				int damage = attack - gp.monster[i].defense;
				if (damage < 0) {
					damage = 0;
				}
				
				gp.monster[i].health -= damage;
				LOGGER.info("Dealt " + damage + " damage to " + gp.monster[i].name + 
	                    ", remaining HP=" + gp.monster[i].health);
				
				gp.ui.addMessage(damage + " damage!");
				
				gp.monster[i].invincible = true;
				gp.monster[i].damageReaction();
				
			}
			if (gp.monster[i].health <= 0 && !gp.monster[i].dying) {
			    gp.monster[i].dying = true;
	            LOGGER.info("Monster " + gp.monster[i].name + " killed, EXP gained=" + gp.monster[i].exp);
			    gp.ui.addMessage("Killed the " + gp.monster[i].name + "!");
			    gp.ui.addMessage("Exp gained: " + gp.monster[i].exp);
			    exp += gp.monster[i].exp;
			    checkLevelUp();
			}
		}
	}
	
	
	public void checkLevelUp() {
		if (exp >= nextLevelExp) {
			level++;
	        LOGGER.info("Player leveled up to level " + level + "!");
			nextLevelExp = nextLevelExp * 2; // Example: double required EXP each level
			maxHealth += 2; // Increase max health each level
			strength++; // Increase strength each level
			dexterity++; // Increase dexterity each level
			attack = getAttack(); // Recalculate attack based on new strength
			defense = getDefense(); // Recalculate defense based on new dexterity
			
			// play level up sound
			gp.playSE(SoundEvent.SFX_LEVEL_UP);
			
			gp.stateManager.toDialogueState();
			gp.ui.currentDialogue = "You leveled up to level " + level + "!";
			
		}
	}
	/**
	 * Updates invincibility timer
	 */
	private void updateInvincibility() {
		if (invincible) {
			invincibleCounter++;
			if (invincibleCounter > Constants.Player.INVINCIBILITY_DURATION) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	
	/**
	 * Handles NPC interaction and attack input
	 */
	private void interactNPC(int i) {
	    if (gp.keyH.confirmPressed && i != 999) {
	        attackCancelled = true;
	        attacking = false;
	        gp.stateManager.toDialogueState();
	        gp.npc[i].speak();
	        gp.ui.getState().currentDialogue = gp.npc[i].getDialogue();
	        gp.ui.getState().dialogueActive = true;
	        gp.keyH.confirmPressed = false; // Only consume if NPC was found
	    }
	}
	
	/**
	 * Handles object pickup (to be implemented)
	 */
	private void pickupObject(int i) {
	    if (i == 999 || gp.obj[i] == null) return; // Nothing to pick up

	    Entity obj = gp.obj[i]; // Temporary reference

	    // First, handle Collectible and Usable effects
	    if (obj instanceof Collectible) {
	        ((Collectible) obj).collect(this);
	    } else if (obj instanceof Usable && gp.keyH.confirmPressed) {
	        ((Usable) obj).use(this);
	        // Remove consumable from world immediately
	        gp.obj[i] = null;
	        return;
	    }

	    // Add to inventory if there's space
	    String text;
	    if (inventory.size() < maxInventorySize) {
	        inventory.add(obj);
	        gp.playSE(SoundEvent.SFX_COIN);
	        text = "Picked up " + obj.name + "!";
	        LOGGER.info("Picked up " + obj.name + ", added to inventory. Inventory size: " + inventory.size());
	    } else {
	        text = "Inventory full! Cannot pick up " + obj.name + ".";
	        LOGGER.info("Failed to pick up " + obj.name + ". Inventory is full at size: " + inventory.size());
	    }

	    gp.ui.addMessage(text);

	    // Remove from world
	    gp.obj[i] = null;
	}
	
	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		if (attacking) {
			// Use attack animation controller and sprites
			image = getAttackSprite();
			
			// Adjust screen position for attack sprites
			switch(direction) {
				case "up":
					tempScreenY = screenY - gp.tileSize;
					break;
				case "left":
					tempScreenX = screenX - gp.tileSize;
					break;
				// down and right don't need adjustment
			}
		}
		else {
			// Use normal animation controller
			image = animationController.getSprite(direction, 
				up1, up2, down1, down2, left1, left2, right1, right2);
		}
		
		// Apply invincibility transparency using constant
		if (invincible) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
				Constants.Player.INVINCIBILITY_ALPHA));
		}
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		// Reset composite
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
	
	public void selectItem() {
	    int itemIndex = gp.ui.inventoryRenderer.getItemIndexOnSlot();
	    
	    if (itemIndex < inventory.size()) {
	        Entity selectedItem = inventory.get(itemIndex);
	        
	        if (selectedItem.type == type_sword || selectedItem.type == type_axe) {
	            currentWeapon = inventory.get(itemIndex); // Use the inventory object
	            attack = getAttack();
	        }
	        else if (selectedItem.type == type_shield) {
	            currentShield = selectedItem;
	            defense = getDefense();
	        }
	        else if (selectedItem.type == type_consumable) {
	            ((Usable)selectedItem).use(this);
	            inventory.remove(itemIndex);
	            
	            // If removing, move cursor back so it doesn't go out of bounds
	            if (itemIndex >= inventory.size()) {
	                gp.ui.inventoryRenderer.slotCol = (inventory.size() - 1) % 5;
	                gp.ui.inventoryRenderer.slotRow = (inventory.size() - 1) / 5;
	            }
	        }
	    }

	    
	}
	
	/**
	 * Gets the appropriate attack sprite based on direction and frame
	 */
	private BufferedImage getAttackSprite() {
		int spriteNum = attackAnimController.getSpriteNum();
		
		switch(direction) {
			case "up":
				return (spriteNum == 1) ? attackUp1 : attackUp2;
			case "down":
				return (spriteNum == 1) ? attackDown1 : attackDown2;
			case "left":
				return (spriteNum == 1) ? attackLeft1 : attackLeft2;
			case "right":
				return (spriteNum == 1) ? attackRight1 : attackRight2;
			default:
				return attackDown1;
		}
	}
}

