package com.mjsamaha.game.audio;

import java.util.HashSet;
import java.util.Set;

public class SoundManager {
	private final Set<PlayingSound> activeSounds = new HashSet<>();

	public void playSound(SoundEvent event) {
		Sound sound = SoundRegistry.get(event.getId());
		if (sound != null) {
			// Stop any existing instance of this sound first (for music switching)
			stopSound(event);

			// Start new sound
			PlayingSound ps = new PlayingSound(sound);
			ps.start();
			activeSounds.add(ps);
		}
	}

	public void stopSound(SoundEvent event) {
		activeSounds.stream().filter(ps -> ps.getSound().getId().equals(event.getId())).forEach(ps -> {
			ps.stop(); // PlayingSound should stop & reset its clip
		});

		// Also remove stopped sounds from active set
		activeSounds.removeIf(ps -> !ps.isPlaying());
	}

	public void tick() {
		activeSounds.removeIf(ps -> !ps.isPlaying());
	}
}