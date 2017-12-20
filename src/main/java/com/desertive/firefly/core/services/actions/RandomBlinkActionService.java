package com.desertive.firefly.core.services.actions;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest.Section;
import com.desertive.firefly.core.data.utils.ActionRequestUtil;

public class RandomBlinkActionService extends ActionService {
    
    /*
     * Starry sky action shows desired number of randomly blinking lights
     */
    public List<TransitionStep> generateTransitionSteps(Section section) {
        // Desired colors
        List<Color> colors = super.getColors(section.getColors());

        // Background color
        Color fillColor = super.getColorOrDefault(section.getProperties(), new Color(0, 0, 0));

        // Blinkers count
        int blinkerCount = ActionRequestUtil.getIntProperty(section.getProperties(), "blinkers", section.getEnd() - section.getStart());

        // Transition time
        int transition = ActionRequestUtil.getIntProperty(section.getProperties(), "transition", 20) - 1;

        // Step count
        int steps = ActionRequestUtil.getIntProperty(section.getProperties(), "steps", 20);

        return IntStream.range(0, steps)
            .mapToObj(i -> new TransitionStep(
                super.generateRandomLedMask(section.getStart(), section.getEnd(), blinkerCount).stream()
                    .map(mask -> mask == 2 ? colors.get(i % colors.size()) : mask == 1 ? fillColor : null)
                    .collect(Collectors.toList()),
                transition
            ))
            .collect(Collectors.toList());
    }

}
