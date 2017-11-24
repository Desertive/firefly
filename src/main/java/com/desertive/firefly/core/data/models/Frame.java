package com.desertive.firefly.core.data.models;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Frame {

	/*
	 * Indicates frame's color state
	 */
	private final List<Color> leds;

	/*
	 * How long (in frames) we will keep the color state before starting transition
	 */
	private final int sleep;


	public Frame() {
		this.leds = new ArrayList<>();
		this.sleep = 1;
	}

	public Frame(List<Color> leds) {
		this.leds = leds;
		this.sleep = 1;
	}
	
	public Frame(List<Color> leds, int sleep) {
		this.leds = leds;
		this.sleep = sleep;
	}

	public List<Color> getLeds() {
		return leds;
	}
	
	public int getSleep() {
		return sleep;
	}

}
