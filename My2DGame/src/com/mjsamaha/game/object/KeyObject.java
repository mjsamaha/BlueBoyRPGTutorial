package com.mjsamaha.game.object;

import java.awt.Rectangle;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.audio.SoundEvent;
import com.mjsamaha.game.entity.Player;
import com.mjsamaha.game.object.common.Collectible;
import com.mjsamaha.game.object.common.GameObject;

public class KeyObject extends GameObject implements Collectible {

	public KeyObject(GamePanel gp) {
        super(gp);
        name = "Key";
        image = setup("/objects/key", gp.tileSize, gp.tileSize);
        
		description = "{" + name + "}\nIt opens a door.";

        
        System.out.println("KeyObject created!");
        System.out.println("  - Image loaded: " + (image != null));
        System.out.println("  - Type: " + type);
        
        pickupable = true;
        
        // Set collision box
        solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        collision = false;
    }
    
    @Override
    public void collect(Player player) {
    	gp.playSE(SoundEvent.SFX_COIN);
        player.keys++;
        System.out.println("Key collected! Total keys: " + player.keys);
    }
}