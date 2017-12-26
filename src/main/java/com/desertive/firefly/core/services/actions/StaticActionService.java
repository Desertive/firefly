package com.desertive.firefly.core.services.actions;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest.Section;

public class StaticActionService extends ActionService {

    /*
     * Static action presents only one static color without animation
     */
    public List<TransitionStep> generateTransitionSteps(Section section) {
        // Get desired colors
        List<Color> reqColors = super.getColorsOrThrow(section.getColors());

        // Construct color mask
        List<Integer> maskList = super.generateLedMask(section.getStart(), section.getEnd());

        // Init color array
        List<Color> colors = maskList.stream()
            .map(mask -> mask >= 1 ? reqColors.get((mask - 1) % reqColors.size()) : null) // Set color based to mask
            .collect(Collectors.toList());

        // Because there are no animation, build one step and return it
        return Arrays.asList(new TransitionStep(colors));
    }

}
