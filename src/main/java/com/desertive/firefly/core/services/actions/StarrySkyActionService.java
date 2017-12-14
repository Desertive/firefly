package com.desertive.firefly.core.services.actions;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest.Section;
import com.desertive.firefly.core.data.utils.ActionRequestUtil;

public class StarrySkyActionService extends ActionService {
    
    /*
     * Starry sky action shows desired number of randomly blinking lights
     */
    public List<TransitionStep> generateTransitionSteps(Section section) {
        // Desired star colors
        List<Color> colors = super.getColors(section.getColors());
        
        // Background color
        Color backgroundColor = super.getColor(section.getProperties(), true);
        
        // Star count
        int count = ActionRequestUtil.getIntPropertyOrThrow(section.getProperties(), "stars");
        
        // Transition time
        int transition = ActionRequestUtil.getIntPropertyOrThrow(section.getProperties(), "transition") - 1;
        
        return IntStream.range(0, 20)
                .mapToObj(c -> new TransitionStep(
                        super.generateRandomLedMask(section.getStart(), section.getEnd(), count).stream()
                            .map(i -> i == 2 ? colors.get(i % colors.size()) : i == 1 ? backgroundColor : null)
                            .collect(Collectors.toList()),
                            transition
                ))
                .collect(Collectors.toList());
    }

}
