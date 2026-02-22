package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.Player;

public class OBJ_Chest extends GameObject implements Usable {

    private boolean opened = false;

    public OBJ_Chest(GamePanel gp) {
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
            System.out.println("Chest opened!");
        }
    }
}