package com.desertive.firefly.core.services.calculation;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.desertive.firefly.core.data.builders.TransitionStepBuilder;
import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest.LedStripSection;
import com.desertive.firefly.core.data.utils.ActionRequestUtil;

public class StaticActionService implements ActionService {

	/*
	 * Static action presents only one static color without animation
	 */
	public List<TransitionStep> generateTransitionSteps(LedStripSection ledStripSection) {
		// Get base color
		Color baseColor = new Color(
				ActionRequestUtil.getIntPropertyOrThrow(ledStripSection.getProperties(), "r"),
				ActionRequestUtil.getIntPropertyOrThrow(ledStripSection.getProperties(), "g"),
				ActionRequestUtil.getIntPropertyOrThrow(ledStripSection.getProperties(), "b")
				);


		// Init led array
		List<Color> leds = IntStream
								 .rangeClosed(0, ledStripSection.getEnd())
								 .map(i -> i >= ledStripSection.getStart() ? 1 : 0 ) // Mask for the led array.
								 													 // 0 = set null, 1 = set base color
								 .boxed()
								 .map(i -> i == 1 ? baseColor : null) // Set base color based to mask
								 .collect(Collectors.toList());
		
		// Because there are no animation, build one step and return it
		return Arrays.asList(new TransitionStepBuilder().setLeds(leds).build());
	}
	
}
