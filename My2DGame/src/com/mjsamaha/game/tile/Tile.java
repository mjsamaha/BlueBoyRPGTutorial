package com.mjsamaha.game.tile;

import java.awt.image.BufferedImage;

/**
 * Represents a single tile instance in the game world.
 * Stores the image and collision state for this specific tile on the map.
 * Behaviors are not stored here—they are defined in TileDefinition.
 */
public class Tile {
    public BufferedImage image;   // The image currently displayed
    public boolean collision = false; // Whether this tile blocks movement
}
