package com.mjsamaha.game.entity.common;

import java.awt.Rectangle;

public interface Collidable {
    
    /**
     * Gets the collision area (hitbox)
     */
    Rectangle getSolidArea();
    
    /**
     * Gets the default X offset for collision area
     */
    int getSolidAreaDefaultX();
    
    /**
     * Gets the default Y offset for collision area
     */
    int getSolidAreaDefaultY();
    
    /**
     * Checks if collision is currently active
     */
    boolean isCollisionOn();
    
    /**
     * Sets collision state
     */
    void setCollisionOn(boolean collisionOn);
}
