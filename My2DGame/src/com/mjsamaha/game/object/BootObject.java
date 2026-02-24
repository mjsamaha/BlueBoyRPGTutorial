package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.Player;
import com.mjsamaha.game.object.common.Collectible;
import com.mjsamaha.game.object.common.GameObject;

public class BootObject extends GameObject implements Collectible {

    public BootObject(GamePanel gp) {
        super(gp);

        name = "Boots";
        image = setup("/objects/boots", gp.tileSize, gp.tileSize);

        pickupable = true;
    }

    @Override
    public void collect(Player player) {
        player.speed += 1;
    }
}