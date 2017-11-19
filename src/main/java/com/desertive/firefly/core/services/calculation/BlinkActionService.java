package com.desertive.firefly.core.services.calculation;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest.LedStripSection;
import com.desertive.firefly.core.data.utils.ActionRequestUtil;

public class BlinkActionService extends ActionService {

	/*
	 * Thoughts for the future: service could handle n+1 colors, so that blink
	 * effect could be created between multiple colors.
	 */

	public List<TransitionStep> generateTransitionSteps(LedStripSection ledStripSection) {
		// Black
		Color blackColor = new Color(0, 0, 0);

		// Get base color
		Color baseColor = super.getColor(ledStripSection.getProperties());

		// Transition time calculated from the interval property. Interval represents
		// the whole blink animation so one transition equals to half of the interval's
		// time.
		Integer transitionTime = ActionRequestUtil.getIntPropertyOrThrow(ledStripSection.getProperties(), "interval") / 2;

		// Construct led mask
		List<Integer> mask = super.generateLedMask(ledStripSection.getStart(), ledStripSection.getEnd());

		// Construct first array
		List<Color> blackColorList = mask.stream()
				.map(i -> i == 1 ? blackColor : null) // Set base color based to mask
				.collect(Collectors.toList());

		// Construct second array
		List<Color> baseColorList = mask.stream()
				.map(i -> i == 1 ? baseColor : null) // Set base color based to mask
				.collect(Collectors.toList());

		// Build a list containing two transition steps
		return Arrays.asList(
				new TransitionStep(blackColorList, transitionTime),
				new TransitionStep(baseColorList, transitionTime));
	}

}
