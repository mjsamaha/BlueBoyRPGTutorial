package com.mjsamaha.game.audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	private final String id;
	private final String resource;
	private final float volume;
	private final float pitch;
	private final boolean looping;

	private Clip clip; // inside Sound class

	public Sound(String id, String resource, float volume, float pitch, boolean looping) {
		this.id = id;
		this.resource = resource;
		this.volume = volume;
		this.pitch = pitch;
		this.looping = looping;
		loadClip();
	}

	private void loadClip() {
	    try {
	        System.out.println("Loading audio: " + resource);

	        URL url = getClass().getResource(resource);
	        if (url == null) {
	            System.err.println("Audio resource not found: " + resource);
	            return;
	        }

	        AudioInputStream ais = AudioSystem.getAudioInputStream(url);
	        clip = AudioSystem.getClip();
	        clip.open(ais);

	        System.out.println("Loaded successfully: " + resource);

	    } catch (UnsupportedAudioFileException e) {
	        System.err.println("Unsupported audio format: " + resource);
	        e.printStackTrace();
	    } catch (IOException | LineUnavailableException e) {
	        e.printStackTrace();
	    }
	}

	public String getId() {
		return id;
	}

	public String getResource() {
		return resource;
	}

	public float getVolume() {
		return volume;
	}

	public float getPitch() {
		return pitch;
	}

	public boolean isLooping() {
		return looping;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}

	public Clip getClip() {
		return clip;
	}
}