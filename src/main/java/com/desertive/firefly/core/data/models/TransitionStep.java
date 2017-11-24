package com.desertive.firefly.core.data.models;

import java.awt.Color;
import java.util.List;

public class TransitionStep {

	/*
	 * Indicates step's color state
	 */
	private final List<Color> leds;
	/*
	 * Indicates how many frames it should take to make the transition to the next step
	 */
	private final int transitionTime;
	/*
	 * Indicates how long (in frames) timer engine should freeze this step's state
	 */
	private final int sleep;
	
	public TransitionStep(List<Color> leds) {
		this.leds = leds;
		this.transitionTime = 0;
		this.sleep = 1;
	}
	
	public TransitionStep(List<Color> leds,
			Integer transitionTime) {
		this.leds = leds;
		this.transitionTime = transitionTime;
		this.sleep = 1;
	}
	
	public TransitionStep(List<Color> leds,
			Integer transitionTime,
			Integer sleep) {
		this.leds = leds;
		this.transitionTime = transitionTime;
		this.sleep = sleep;
	}

	public List<Color> getLeds() {
		return leds;
	}

	public int getTransitionTime() {
		return transitionTime;
	}

	public int getSleep() {
		return sleep;
	}
}
