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
        List<Color> colors = super.getColorsOrThrow(section.getColors());

        // Background color
        Color fillColor = super.getColorOrDefault(section.getProperties(), new Color(0, 0, 0));

        // Blinkers count
        int blinkerCount = ActionRequestUtil.getIntPropertyOrDefault(section.getProperties(), "blinkers", section.getEnd() - section.getStart());

        // Transition time
        int transition = ActionRequestUtil.getIntPropertyOrDefault(section.getProperties(), "transition", 20) - 1;

        // Step count
        int steps = ActionRequestUtil.getIntPropertyOrDefault(section.getProperties(), "steps", 20);

        return IntStream.range(0, steps)
            .mapToObj(i -> new TransitionStep(
                super.generateRandomRunningNumbers(section.getStart(), section.getEnd(), blinkerCount).stream()
                    .map(mask -> mask >= 1 ? colors.get(mask % colors.size()) : fillColor )
                    .collect(Collectors.toList()),
                transition
            ))
            .collect(Collectors.toList());
    }

}
