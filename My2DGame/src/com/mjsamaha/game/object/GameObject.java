package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.Entity;

public abstract class GameObject extends Entity {

    public boolean pickupable = false;

    public GameObject(GamePanel gp) {
        super(gp);
        type = 2; // object type (if you're using type system)
    }

    // Called when player interacts or picks up
    public void onPlayerInteract() {
        // default: do nothing
    }
}