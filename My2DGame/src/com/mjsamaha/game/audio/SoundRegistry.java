package com.mjsamaha.game.audio;

import java.util.HashMap;
import java.util.Map;

public class SoundRegistry {
    private static final Map<String, Sound> sounds = new HashMap<>();

    public static void register(Sound sound) {
        sounds.put(sound.getId(), sound);
    }

    public static Sound get(String id) {
        return sounds.get(id);
    }
}