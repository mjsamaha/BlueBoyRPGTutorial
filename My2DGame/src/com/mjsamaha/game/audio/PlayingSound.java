package com.mjsamaha.game.audio;

import javax.sound.sampled.*;
import java.net.URL;

public class PlayingSound {
	private final Sound sound;
	private Clip clip;

	public PlayingSound(Sound sound) {
		this.sound = sound;
	}

	public void start() {
		try {
			URL url = getClass().getResource(sound.getResource());
			if (url == null) {
			    System.out.println("Sound not found: " + sound.getResource());
			    return;
			}

			AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(ais);

			FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float) (20 * Math.log10(sound.getVolume() <= 0 ? 0.0001 : sound.getVolume()));
			gain.setValue(dB);

			if (sound.isLooping()) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				clip.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isPlaying() {
		return clip != null && clip.isActive();
	}

	public void stop() {
		if (clip == null)
			return;
		clip.stop();
		clip.setFramePosition(0);
	}

	public Sound getSound() {
		return sound; // return the Sound object itself
	}
}