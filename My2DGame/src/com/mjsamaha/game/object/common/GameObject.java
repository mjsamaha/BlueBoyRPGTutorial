package com.mjsamaha.game.object.common;

import java.awt.Graphics2D;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.common.Entity;

public abstract class GameObject extends Entity {

    public boolean pickupable = false;

    public GameObject(GamePanel gp) {
        super(gp);
        type = 3;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (image == null) {
            System.err.println("Cannot draw " + name + " - image is null!");
            return;
        }
        
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        
        // Only draw if on screen
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
    
    // Called when player interacts or picks up
    public void onPlayerInteract() {
        // default: do nothing
    }
}