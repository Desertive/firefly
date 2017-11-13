package com.desertive.firefly.core.data.builders;

import java.awt.Color;
import java.util.List;

import com.desertive.firefly.core.data.models.TransitionStep;

public class TransitionStepBuilder {
	
	TransitionStep transitionStep;
	
	public TransitionStepBuilder() {
		transitionStep = new TransitionStep();
	}
	
	public TransitionStepBuilder setLeds(List<Color> leds) {
		transitionStep.setLeds(leds);
		return this;
	}
	
	public TransitionStepBuilder setTransitionTime(Integer transitionTime) {
		transitionStep.setTransitionTime(transitionTime);
		return this;
	}
	
	public TransitionStepBuilder setSleep(Integer sleep) {
		transitionStep.setSleep(sleep);
		return this;
	}
	
	public TransitionStep build() {
		return transitionStep;
	}
	
}
