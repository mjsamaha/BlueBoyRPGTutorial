package com.mjsamaha.game.audio;

public enum SoundEvent {
	MUSIC_BLUE_BOY("music_blue_boy"), 
	MUSIC_FIRST_SAMPLE("music_first_sample"),
	MUSIC_AMBIENT_LOOP("music_ambient_loop"), 
	SFX_MENU_SELECT("sfx_menu_select"),

	
	SFX_COIN("sfx_coin"), 
	SFX_POWERUP("sfx_powerup"),
	SFX_UNLOCK("sfx_unlock"), 
	SFX_FANFARE("sfx_fanfare"), 
	SFX_LEVEL_UP("sfx_level_up"),
	
	SFX_HIT_MONSTER("hitmonster"),
	SFX_RECEIVE_DAMAGE("receivedamage"),
	SFX_SWORD_SWING("sword_sfx");
	

	private final String id;

	SoundEvent(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}