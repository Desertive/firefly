package com.desertive.firefly.core.services.actions;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest.Section;

public class StaticActionService extends ActionService {

    /*
     * Static action presents only static color(s) without animation
     */
    public List<TransitionStep> generateTransitionSteps(Section section) {
        // Get desired colors
        List<Color> reqColors = super.getColorsOrThrow(section.getColors());

        // Construct color mask
        List<Integer> maskList = super.generateRunningNumbers(section.getStart(), section.getEnd());

        // Init color array
        List<Color> colors = maskList.stream()
            .map(mask -> reqColors.get(mask % reqColors.size())) // Set color based to mask
            .collect(Collectors.toList());

        // Because there are no animation, build one step and return it
        return Arrays.asList(new TransitionStep(colors));
    }

}
