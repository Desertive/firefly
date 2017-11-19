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
	private final Integer transitionTime;
	/*
	 * Indicates how long (in frames) timer engine should freeze this step's state
	 */
	private final Integer sleep;
	
	public TransitionStep(List<Color> leds) {
		this.leds = leds;
		this.transitionTime = null;
		this.sleep = null;
	}
	
	public TransitionStep(List<Color> leds,
			Integer transitionTime) {
		this.leds = leds;
		this.transitionTime = transitionTime;
		this.sleep = null;
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

	public Integer getTransitionTime() {
		return transitionTime;
	}

	public Integer getSleep() {
		return sleep;
	}
}
