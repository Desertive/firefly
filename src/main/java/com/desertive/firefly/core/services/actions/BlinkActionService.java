package com.desertive.firefly.core.services.actions;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest.Section;
import com.desertive.firefly.core.data.utils.ActionRequestUtil;

public class BlinkActionService extends ActionService {

	/*
     * Blink action
	 */

    public List<TransitionStep> generateTransitionSteps(Section section) {
        List<Color> colors;
        
        if (section.getColors().size() == 1) {
            colors = new ArrayList<Color>() {{
                add(new Color(0, 0, 0));
                add(getColor(section.getColors().get(0)));
            }};
        } else {
            colors = super.getColors(section.getColors());
        }

        // Transition time calculated from the interval property and the size of the color list.
        // One frame represents the actual color so thats why we will minus one.
        int transitionTime = ActionRequestUtil.getIntPropertyOrThrow(section.getProperties(), "transition") - 1;

        // Construct color mask
        List<Integer> mask = super.generateLedMask(section.getStart(), section.getEnd());

        return colors.stream()
                .map(color -> new TransitionStep(
                        mask.stream()
                            .map(i -> i >= 1 ? color : null) // Set base color based to mask
                            .collect(Collectors.toList()),
                        transitionTime
                ))
                .collect(Collectors.toList());
    }

}
