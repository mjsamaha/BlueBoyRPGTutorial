package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.Player;
import com.mjsamaha.game.object.common.GameObject;
import com.mjsamaha.game.object.common.Usable;

public class ChestObject extends GameObject implements Usable {

    private boolean opened = false;

    public ChestObject(GamePanel gp) {
        super(gp);

        name = "Chest";
        image = setup("/objects/chest", gp.tileSize, gp.tileSize);
        collision = true;
    }

    @Override
    public void use(Player player) {
        if (!opened) {
            opened = true;
            collision = false;
        }
    }
}