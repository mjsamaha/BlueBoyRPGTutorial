package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.Player;

public class OBJ_Key extends GameObject implements Collectible {

    public OBJ_Key(GamePanel gp) {
        super(gp);

        name = "Key";
        image = setup("/objects/key", gp.tileSize, gp.tileSize);

        pickupable = true;
    }

    @Override
    public void collect(Player player) {
        player.keys++;
        System.out.println("Key collected!");
    }
}