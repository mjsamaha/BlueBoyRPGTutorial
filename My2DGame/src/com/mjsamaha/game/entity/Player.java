package com.mjsamaha.game.entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.mjsamaha.game.Constants;
import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.audio.SoundEvent;
import com.mjsamaha.game.managers.KeyHandler;
import com.mjsamaha.game.object.Collectible;
import com.mjsamaha.game.object.OBJ_Heart;
import com.mjsamaha.game.object.Usable;


public class Player extends Entity {
	
	private KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public OBJ_Heart life;
	public OBJ_Heart maxLife;
	public int keys = 0;
	
	// Attack animation controller (separate from normal animation)
	private AnimationController attackAnimController;
	
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
		attackArea.width = 36;
		attackArea.height = 36;
		
		// Create separate animation controller for attacks
		attackAnimController = new AnimationController(5); // Faster attack animation
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
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

	    if (attacking) {
	        attack();
	    }
	    else if (isMoving()) {
	        handleMovement();
	        animationController.update();
	    }

	    int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
	    interactNPC(npcIndex);

	    // Unified monster collision (movement + damage)
	    int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);

	    if (monsterIndex != 999 && !invincible) {
	        health -= Constants.Monster.SLIME_DAMAGE;
	        
	        // Play damage sound
	        gp.playSE(SoundEvent.SFX_RECEIVE_DAMAGE);
	        
	        invincible = true;
	    }

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
				gp.monster[i].health -= 1;
				gp.monster[i].invincible = true;
				gp.monster[i].damageReaction();
				
				// Play hit sound
				gp.playSE(SoundEvent.SFX_HIT_MONSTER);
			}
			if (gp.monster[i].health <= 0) {
				gp.monster[i].dying = true;
			}
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
	    if (gp.keyH.confirmPressed) {
	        if (i != 999) {
	            gp.gameState = gp.dialogueState;
	            gp.npc[i].speak();
	            gp.ui.getState().currentDialogue = gp.npc[i].getDialogue();
	            gp.ui.getState().dialogueActive = true;
	            gp.keyH.confirmPressed = false;
	        } else {
	        	// play swing sound effect
	        	gp.playSE(SoundEvent.SFX_SWORD_SWING);
	            attacking = true;
	        }
	    }
	}
	
	/**
	 * Handles object pickup (to be implemented)
	 */
	private void pickupObject(int i) {

	    if (i == 999) return;

	    if (gp.obj[i] instanceof Collectible) {

	        Collectible item = (Collectible) gp.obj[i];
	        item.collect(this);

	        gp.obj[i] = null; // remove from world
	    }
	    else if (gp.obj[i] instanceof Usable && gp.keyH.confirmPressed) {
	        Usable usable = (Usable) gp.obj[i];
	        usable.use(this);
	    }
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

