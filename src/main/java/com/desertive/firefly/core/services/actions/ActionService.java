package com.desertive.firefly.core.services.actions;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest.LedStripSection;
import com.desertive.firefly.core.data.utils.ActionRequestUtil;

public abstract class ActionService {

    public Color getColor(HashMap<String, Integer> properties) {
        return new Color(
            ActionRequestUtil.getIntPropertyOrThrow(properties, "r"),
            ActionRequestUtil.getIntPropertyOrThrow(properties, "g"),
            ActionRequestUtil.getIntPropertyOrThrow(properties, "b"));
    }

    public List<Integer> generateLedMask(Integer start, Integer end) {
        return IntStream.rangeClosed(0, end)
            .map(i -> i >= start ? 1 : 0) // Mask for the led array.
            // 0 = set null, 1 = set base color
            .boxed()
            .collect(Collectors.toList());
    }

    public abstract List<TransitionStep> generateTransitionSteps(LedStripSection ledStripSection);

}
