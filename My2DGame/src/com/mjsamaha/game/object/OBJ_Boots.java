package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.Player;

public class OBJ_Boots extends GameObject implements Collectible {

    public OBJ_Boots(GamePanel gp) {
        super(gp);

        name = "Boots";
        image = setup("/objects/boots", gp.tileSize, gp.tileSize);

        pickupable = true;
    }

    @Override
    public void collect(Player player) {
        player.speed += 1;
        System.out.println("Speed increased!");
    }
}