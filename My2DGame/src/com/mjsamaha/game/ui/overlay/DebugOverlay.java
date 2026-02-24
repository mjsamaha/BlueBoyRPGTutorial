package com.mjsamaha.game.ui.overlay;

import java.awt.Color;
import java.awt.Graphics2D;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.ui.FontManager;

public class DebugOverlay {
	
	private final GamePanel gp;
	private final FontManager fm = FontManager.getInstance();
	
	// Overlay dimensions
	private static final int OVERLAY_WIDTH = 175;
	private static final int OVERLAY_HEIGHT = 100;
	private static final int MARGIN = 10; // Distance from screen edge
	
	public DebugOverlay(GamePanel gp) {
		this.gp = gp;
	}
	
	public void draw(Graphics2D g2) {
		// Calculate top-right position
        int overlayX = gp.screenWidth - OVERLAY_WIDTH - MARGIN;
        int overlayY = MARGIN;
        
        // Semi-transparent background
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(overlayX, overlayY, OVERLAY_WIDTH, OVERLAY_HEIGHT);
        
        // Border
        g2.setColor(Color.GREEN);
        g2.drawRect(overlayX, overlayY, OVERLAY_WIDTH, OVERLAY_HEIGHT);
        
     // Text
        g2.setFont(fm.getSmall());
        g2.setColor(Color.WHITE);
        
        int x = overlayX + 10;  // 10 pixels padding from left edge of overlay
        int y = overlayY + 25;  // Start text a bit down from top
        int lineHeight = 20;
        
        // Player world position
        g2.drawString("X: " + gp.player.worldX, x, y);
        y += lineHeight;
        g2.drawString("Y: " + gp.player.worldY, x, y);
        y += lineHeight;
        
        // Player tile position
        int playerCol = gp.player.worldX / gp.tileSize;
        int playerRow = gp.player.worldY / gp.tileSize;
        g2.drawString("Tile: " + playerCol + ", " + playerRow, x, y);
        y += lineHeight;
        
        // Player stats
//        g2.drawString("HP: " + gp.player.health + "/" + gp.player.maxHealth, x, y);
//        y += lineHeight;
//        
//        g2.drawString("Level: " + gp.player.level + " EXP: " + gp.player.exp, x, y);
    }


}
