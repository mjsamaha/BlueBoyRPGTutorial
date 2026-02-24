package com.mjsamaha.game.audio;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AudioInitializer {
	
    private static final Logger LOGGER = Logger.getLogger(AudioInitializer.class.getSimpleName());
    
    public static void initializeSounds() {
        LOGGER.info("Initializing audio subsystem...");
        registerMusic();
        registerSoundEffects();
        LOGGER.info("Audio subsystem initialization complete.");
    }
    
    private static void registerMusic() {
        SoundRegistry.register(new Sound("music_blue_boy", "/audio/blueBoyAdventure.wav", 1.0f, 1.0f, true));
        SoundRegistry.register(new Sound("music_first_sample", "/audio/FirstSample.wav", 1.0f, 1.0f, true));
        SoundRegistry.register(new Sound("music_ambient_loop", "/audio/AmbientLoopFour.wav", 1.0f, 1.0f, true));
    }
    
    private static void registerSoundEffects() {
        SoundRegistry.register(new Sound("sfx_coin", "/audio/coin.wav", 1.0f, 1.0f, false));
        SoundRegistry.register(new Sound("sfx_powerup", "/audio/powerup.wav", 1.0f, 1.0f, false));
        SoundRegistry.register(new Sound("sfx_unlock", "/audio/unlock.wav", 1.0f, 1.0f, false));
        SoundRegistry.register(new Sound("sfx_fanfare", "/audio/fanfare.wav", 1.0f, 1.0f, false));
        SoundRegistry.register(new Sound("sfx_menu_select", "/audio/menu_selection.wav", 1.0f, 1.0f, false));
        SoundRegistry.register(new Sound("hitmonster", "/audio/hitmonster.wav", 1.0f, 1.0f, false));
        SoundRegistry.register(new Sound("receivedamage", "/audio/receivedamage.wav", 1.0f, 1.0f, false));
        SoundRegistry.register(new Sound("sword_sfx", "/audio/sword_sfx.wav", 1.0f, 1.0f, false));
        SoundRegistry.register(new Sound("sfx_level_up", "/audio/levelup.wav", 1.0f, 1.0f, false));
        SoundRegistry.register(new Sound("sfx_cursor", "/audio/cursor.wav", 1.0f, 1.0f, false));
    }
}