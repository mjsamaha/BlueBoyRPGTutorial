package com.mjsamaha.game.object;


import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.Player;

public class OBJ_Door extends GameObject implements Usable {

    public OBJ_Door(GamePanel gp) {
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
            System.out.println("Door unlocked!");
        } else {
            System.out.println("You need a key.");
        }
    }
}