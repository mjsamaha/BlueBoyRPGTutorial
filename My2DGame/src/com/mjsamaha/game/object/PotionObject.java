package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.audio.SoundEvent;
import com.mjsamaha.game.entity.Player;
import com.mjsamaha.game.entity.common.Entity;
import com.mjsamaha.game.object.common.Usable;

public class PotionObject extends Entity implements Usable {
	
	int value = 5;
	GamePanel gp;
	
	public PotionObject(GamePanel gp) {
		super(gp);
		
		this.gp = gp; // Store reference to GamePanel for use in the use() method

		type = type_consumable;
		name = "Health Potion";
		
		down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
		
		description = "{" + name + "}\nRestores health by " + value + ".";
	}
	
	@Override
    public void use(Player player) {
        // Change to dialogue state using the state manager
        gp.stateManager.toDialogueState();
        
        gp.ui.currentDialogue = "You drink the " + name + "!\n" + "Your life has been recovered by " + value + ".";
        
        player.health += value;
        if (player.health > player.maxHealth) {
            player.health = player.maxHealth;
        }
        
        gp.playSE(SoundEvent.SFX_POWERUP);
    }

}
