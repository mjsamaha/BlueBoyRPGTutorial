package com.mjsamaha.game.entity;

import com.mjsamaha.game.GamePanel;

public class MovementController {
    
    private final GamePanel gp;
    
    public MovementController(GamePanel gp) {
        this.gp = gp;
    }
    
    /**
     * Moves an entity in the given direction if no collision is detected.
     * @param entity The entity to move
     */
    public void moveEntity(Entity entity) {
        if (!entity.collisionOn) {
            switch(entity.direction) {
                case "up":
                    entity.worldY -= entity.speed;
                    break;
                case "down":
                    entity.worldY += entity.speed;
                    break;
                case "left":
                    entity.worldX -= entity.speed;
                    break;
                case "right":
                    entity.worldX += entity.speed;
                    break;
            }
        }
    }
    
    /**
     * Performs collision detection for an entity.
     * Checks tiles, objects, NPCs, monsters, and player.
     * @param entity The entity to check
     * @param isPlayer Whether this entity is the player
     */
    public void checkCollisions(Entity entity, boolean isPlayer) {
        entity.collisionOn = false;
        
        gp.cChecker.checkTile(entity);
        gp.cChecker.checkObject(entity, isPlayer);
        gp.cChecker.checkEntity(entity, gp.npc);
        gp.cChecker.checkEntity(entity, gp.monster);
        
        if (!isPlayer) {
            gp.cChecker.checkPlayer(entity);
        }
    }
    
    /**
     * Moves entity with diagonal speed adjustment.
     * Used primarily for player with keyboard input.
     * @param entity The entity to move
     * @param moveX Movement in X direction
     * @param moveY Movement in Y direction
     * @param diagonalMultiplier Speed multiplier for diagonal movement (typically 0.707)
     */
    public void moveWithDiagonalAdjustment(Entity entity, boolean moveX, boolean moveY, double diagonalMultiplier) {
        int moveSpeedX = entity.speed;
        int moveSpeedY = entity.speed;
        
        // Apply diagonal speed adjustment if moving in both directions
        if (moveX && moveY) {
            moveSpeedX = (int)(entity.speed * diagonalMultiplier);
            moveSpeedY = (int)(entity.speed * diagonalMultiplier);
        }
        
        // Apply movement based on direction
        if (!entity.collisionOn) {
            switch(entity.direction) {
                case "up":
                    entity.worldY -= moveSpeedY;
                    break;
                case "down":
                    entity.worldY += moveSpeedY;
                    break;
                case "left":
                    entity.worldX -= moveSpeedX;
                    break;
                case "right":
                    entity.worldX += moveSpeedX;
                    break;
            }
        }
    }
}
