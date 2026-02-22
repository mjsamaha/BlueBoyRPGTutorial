package com.mjsamaha.game.entity;

import java.awt.Graphics2D;

public interface Drawable {
    
    /**
     * Draws the entity on screen
     * @param g2 Graphics2D object for drawing
     */
    void draw(Graphics2D g2);
}