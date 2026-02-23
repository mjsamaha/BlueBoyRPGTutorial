package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.Player;
import com.mjsamaha.game.object.common.Collectible;
import com.mjsamaha.game.object.common.GameObject;

public class KeyObject extends GameObject implements Collectible {

    public KeyObject(GamePanel gp) {
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