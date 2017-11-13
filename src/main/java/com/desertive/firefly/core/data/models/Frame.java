package com.desertive.firefly.core.data.models;

import java.awt.Color;
import java.util.List;

public class Frame {
	
	/*
	 * Indicates frame's color state
	 */
	private List<Color> leds;

	public List<Color> getLeds() {
		return leds;
	}

	public void setLeds(List<Color> leds) {
		this.leds = leds;
	}

}
