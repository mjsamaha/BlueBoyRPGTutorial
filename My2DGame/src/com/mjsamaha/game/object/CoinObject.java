package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.audio.SoundEvent;
import com.mjsamaha.game.entity.Player;
import com.mjsamaha.game.object.common.Collectible;
import com.mjsamaha.game.object.common.GameObject;

public class CoinObject extends GameObject implements Collectible {
	
	public CoinObject(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Coin";
		type = type_pickup_only;
		image = setup("/objects/coin_bronze", gp.tileSize, gp.tileSize);
		down1 = image;
		value = 1;
		
	}
	
	@Override
    public void collect(Player player) {
        
		gp.playSE(SoundEvent.SFX_COIN);
		player.coins += value;
		gp.ui.addMessage("You picked up a coin! Total coins: " + player.coins);
    }

}

