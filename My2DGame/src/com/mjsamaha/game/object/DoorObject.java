package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.Player;
import com.mjsamaha.game.object.common.GameObject;
import com.mjsamaha.game.object.common.Usable;

public class DoorObject extends GameObject implements Usable {

    public DoorObject(GamePanel gp) {
        super(gp);

        name = "Door";
        image = setup("/objects/door", gp.tileSize, gp.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = gp.tileSize;
        solidArea.height = 32;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void use(Player player) {
        if (player.keys > 0) {
            player.keys--;
            collision = false;
        }
    }
}