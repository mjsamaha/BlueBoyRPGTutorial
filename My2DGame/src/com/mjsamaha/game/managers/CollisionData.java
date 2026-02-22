package com.mjsamaha.game.managers;

import com.mjsamaha.game.entity.Entity;

public class CollisionData {
    /** Whether the proposed movement collides with something. */
    public boolean collision = false;

    /** Index in the target array where the contact happened, -1 if none. */
    public int entityIndex = -1;

    /** Reference to the contacted entity (null if none). */
    public Entity contactEntity = null;

    /** Convenience flag if the contact entity is the player. */
    public boolean contactPlayer = false;

    public CollisionData() {}
}