package com.desertive.firefly.core.services.actions;

import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest.Section;
import com.desertive.firefly.core.data.utils.ActionRequestUtil;

public abstract class ActionService {

    public List<Color> getColors(List<HashMap<Character, Integer>> colors) {
        return IntStream.range(0, colors.size())
                .mapToObj(i -> getColor(colors.get(i)))
                .collect(Collectors.toList());
    }
    
    public <T> Color getColor(HashMap<T, Integer> properties) {
        return getColor(properties, false);
    }
    
    public <T> Color getColor(HashMap<T, Integer> properties, boolean string) {
        return new Color(
            ActionRequestUtil.getIntPropertyOrThrow(properties, string ? "r" : 'r'),
            ActionRequestUtil.getIntPropertyOrThrow(properties, string ? "g" : 'g'),
            ActionRequestUtil.getIntPropertyOrThrow(properties, string ? "b" : 'b'));
    }

    public <T> Color getColorOrDefault(HashMap<T, Integer> properties, boolean string, Color color) {
        return new Color(
            ActionRequestUtil.getIntProperty(properties, string ? "r" : 'r', color.getRed()),
            ActionRequestUtil.getIntProperty(properties, string ? "g" : 'g', color.getGreen()),
            ActionRequestUtil.getIntProperty(properties, string ? "b" : 'b', color.getBlue()));
    }

    public List<Integer> generateLedMask(Integer start, Integer end) {
        return IntStream.rangeClosed(0, end)
            .mapToObj(i -> i >= start ? i - start + 1 : 0) // Mask for the color array.
                                               // 0 = set null, > 0 = set base color
            .collect(Collectors.toList());
    }
    
    public List<Integer> generateRandomLedMask(Integer start, Integer end, Integer count) {
        List<Integer> randomNumbers = IntStream.range(start, end)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(randomNumbers);
        
        List<Integer> maskedIndexes = randomNumbers.subList(0, randomNumbers.size() >= count ? count : randomNumbers.size());
        
        return IntStream.rangeClosed(0, end)
            .mapToObj(i -> i >= start ? maskedIndexes.contains(i) ? 2 : 1 : 0) // Mask for the color array.
                                                                               // 2 = primary color, 1 = background color, 0 = null
            .collect(Collectors.toList());
    }

    public abstract List<TransitionStep> generateTransitionSteps(Section section);

}
