package com.mjsamaha.game.ui.overlay;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.common.Entity;
import com.mjsamaha.game.ui.FontManager;
import com.mjsamaha.game.ui.UIUtils;

public class InventoryRenderer {
    private final GamePanel gp;
    private final FontManager fm;
    
    private static final int INVENTORY_COLS = 5;
    private static final int INVENTORY_ROWS = 4;
    private static final int SLOT_SPACING = 3;
    
    public int slotCol = 0;
    public int slotRow = 0;
    
    public InventoryRenderer(GamePanel gp) {
        this.gp = gp;
        this.fm = FontManager.getInstance();
    }
    
    public void draw(Graphics2D g2) {
        drawInventoryFrame(g2);
        drawInventoryItems(g2);
        drawCursor(g2);
        drawItemDescription(g2);
    }
    
    private void drawInventoryFrame(Graphics2D g2) {
        int frameX = gp.tileSize * 9;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;
        
        UIUtils.drawSubWindow(g2, gp, frameX, frameY, frameWidth, frameHeight);
    }
    
    private void drawInventoryItems(Graphics2D g2) {
        int frameX = gp.tileSize * 9;
        int frameY = gp.tileSize;
        
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + SLOT_SPACING;
        
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            BufferedImage itemImage = getItemImage(gp.player.inventory.get(i));
            
            if (itemImage != null) {
                g2.drawImage(itemImage, slotX, slotY, null);
            }
            
            slotX += slotSize;
            
            // Move to next row after every 5 items
            if ((i + 1) % INVENTORY_COLS == 0) {
                slotX = slotXstart;
                slotY += gp.tileSize;
            }
        }
    }
    
    private void drawCursor(Graphics2D g2) {
        int frameX = gp.tileSize * 9;
        int frameY = gp.tileSize;
        int slotXstart = frameX + 20;
        int slotYstart = frameY + 20;
        int slotSize = gp.tileSize + SLOT_SPACING;
        
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
        
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
    }
    
    private void drawItemDescription(Graphics2D g2) {
        int frameX = gp.tileSize * 9;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;
        
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize * 3;
        
        UIUtils.drawSubWindow(g2, gp, dFrameX, dFrameY, dFrameWidth, dFrameHeight);
        
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(fm.getSmall());
        
        int itemIndex = getItemIndexOnSlot();
        
        if (itemIndex < gp.player.inventory.size()) {
            Entity item = gp.player.inventory.get(itemIndex);
            
            for (String line : item.description.split("\n")) {
                // Shadow
                g2.setColor(Color.BLACK);
                g2.drawString(line, textX + 2, textY + 2);
                
                // Main text
                g2.setColor(Color.WHITE);
                g2.drawString(line, textX, textY);
                
                textY += 32;
            }
        }
    }
    
    /**
     * Gets the appropriate image for an inventory item.
     * Checks Entity sprites first (down1), then GameObject sprites (image).
     */
    private BufferedImage getItemImage(Entity entity) {
        if (entity.down1 != null) {
            return entity.down1;
        } else if (entity.image != null) {
            return entity.image;
        }
        return null;
    }
    
    /**
     * Calculates the inventory index based on current cursor position
     */
    public int getItemIndexOnSlot() {
        return slotCol + (slotRow * INVENTORY_COLS);
    }
    
    /**
     * Attempts to move cursor up. Returns true if moved, false if at boundary.
     */
    public boolean moveCursorUp() {
        if (slotRow != 0) {
            slotRow--;
            return true;
        }
        return false;
    }
    
    /**
     * Attempts to move cursor down. Returns true if moved, false if at boundary.
     */
    public boolean moveCursorDown() {
        if (slotRow != INVENTORY_ROWS - 1) {
            slotRow++;
            return true;
        }
        return false;
    }
    
    /**
     * Attempts to move cursor left. Returns true if moved, false if at boundary.
     */
    public boolean moveCursorLeft() {
        if (slotCol != 0) {
            slotCol--;
            return true;
        }
        return false;
    }
    
    /**
     * Attempts to move cursor right. Returns true if moved, false if at boundary.
     */
    public boolean moveCursorRight() {
        if (slotCol != INVENTORY_COLS - 1) {
            slotCol++;
            return true;
        }
        return false;
    }
}