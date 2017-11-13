package com.desertive.firefly.core.data.models;

import java.awt.Color;
import java.util.List;

public class TransitionStep {

	/*
	 * Indicates step's color state
	 */
	private List<Color> leds;
	/*
	 * Indicates how many frames it should take to make the transition to next step
	 */
	private Integer transitionTime;
	/*
	 * Indicates how long (in frames) timer engine should freeze this step's state
	 */
	private Integer sleep;

	public List<Color> getLeds() {
		return leds;
	}

	public void setLeds(List<Color> leds) {
		this.leds = leds;
	}

	public Integer getTransitionTime() {
		return transitionTime;
	}

	public void setTransitionTime(Integer transitionTime) {
		this.transitionTime = transitionTime;
	}

	public Integer getSleep() {
		return sleep;
	}

	public void setSleep(Integer sleep) {
		this.sleep = sleep;
	}
}
