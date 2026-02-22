package com.mjsamaha.game.tile;

import java.util.function.BiConsumer;

import com.mjsamaha.game.GamePanel;

/**
 * Represents the definition of a tile type.
 * This is where pluggable behavior is stored.
 * Example: water tiles can animate, lava can damage player, etc.
 */
public class TileDefinition {

    private final int id;                     // Unique ID of this tile type
    private final String textureId;           // ID of the texture region in the atlas (e.g. "grass00")
    private final boolean collision;          // Whether this tile is solid
    private final BiConsumer<GamePanel, Tile> behavior; // Optional per-tile behavior

    public TileDefinition(int id, String textureId, boolean collision, BiConsumer<GamePanel, Tile> behavior) {
        this.id = id;
        this.textureId = textureId;
        this.collision = collision;
        this.behavior = behavior;
    }

    public int getId() { return id; }
    public String getTextureId() { return textureId; }
    public boolean hasCollision() { return collision; }

    /**
     * Executes the optional behavior of this tile.
     * Called every frame for visible tiles, or on specific events.
     */
    public void executeBehavior(GamePanel gp, Tile tile) {
        if (behavior != null) behavior.accept(gp, tile);
    }
}