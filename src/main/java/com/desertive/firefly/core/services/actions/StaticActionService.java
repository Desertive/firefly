package com.desertive.firefly.core.services.actions;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest.LedStripSection;
import com.desertive.firefly.core.data.utils.ActionRequestUtil;

public class StaticActionService extends ActionService {

    /*
     * Static action presents only one static color without animation
     */
    public List<TransitionStep> generateTransitionSteps(LedStripSection ledStripSection) {
        // Get base color
        Color baseColor = super.getColor(ledStripSection.getProperties());

        // Construct led mask
        List<Integer> mask = super.generateLedMask(ledStripSection.getStart(), ledStripSection.getEnd());

        // Init led array
        List<Color> leds = mask.stream()
            .map(i -> i == 1 ? baseColor : null) // Set base color based to mask
            .collect(Collectors.toList());

        // Because there are no animation, build one step and return it
        return Arrays.asList(new TransitionStep(leds));
    }

}
