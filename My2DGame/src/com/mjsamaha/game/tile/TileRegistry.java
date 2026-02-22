package com.mjsamaha.game.tile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Central registry for all tile definitions.
 * Allows dynamic lookup of tiles by ID instead of hardcoding.
 * Supports pluggable behaviors and external data loading.
 */
public class TileRegistry {

    private static final Map<Integer, TileDefinition> tiles = new HashMap<>();

    /** Registers a new tile definition */
    public static void register(TileDefinition def) {
        tiles.put(def.getId(), def);
    }

    /** Retrieves a tile definition by ID */
    public static TileDefinition get(int id) {
        return tiles.get(id);
    }

    /** Returns all registered tile definitions */
    public static Collection<TileDefinition> getAll() {
        return tiles.values();
    }
}