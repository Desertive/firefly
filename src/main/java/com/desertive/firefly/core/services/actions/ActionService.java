package com.desertive.firefly.core.services.actions;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest.Section;
import com.desertive.firefly.core.data.utils.ActionRequestUtil;
import com.desertive.firefly.core.data.utils.ColorUtil;

public abstract class ActionService {

    protected List<Color> getColorsOrThrow(List<HashMap<String, Integer>> colors) {
        return IntStream.range(0, colors.size())
                .mapToObj(i -> getColorOrThrow(colors.get(i)))
                .collect(Collectors.toList());
    }

    protected Color getColorOrThrow(HashMap<String, Integer> properties) {
        return new Color(
            ActionRequestUtil.getIntPropertyOrThrow(properties, "r"),
            ActionRequestUtil.getIntPropertyOrThrow(properties, "g"),
            ActionRequestUtil.getIntPropertyOrThrow(properties, "b"));
    }

    protected Color getColorOrDefault(HashMap<String, Integer> properties, Color color) {
        return new Color(
            ActionRequestUtil.getIntPropertyOrDefault(properties, "r", color.getRed()),
            ActionRequestUtil.getIntPropertyOrDefault(properties, "g", color.getGreen()),
            ActionRequestUtil.getIntPropertyOrDefault(properties, "b", color.getBlue()));
    }

    protected List<Integer> generateLedMask(Integer start, Integer end) {
        return IntStream.rangeClosed(0, end)
            .mapToObj(i -> i >= start ? i - start + 1 : 0) // Mask for the color array.
                                                           // 0 = set null, > 0 = set color
            .collect(Collectors.toList());
    }

    protected List<Integer> generateRandomLedMask(Integer start, Integer end, Integer count) {
        List<Integer> randomNumbers = IntStream.range(start, end)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(randomNumbers);
        
        List<Integer> maskedIndexes = randomNumbers.subList(0, randomNumbers.size() >= count ? count : randomNumbers.size());
        
        return IntStream.rangeClosed(0, end)
            .mapToObj(i -> i >= start ? maskedIndexes.contains(i) ? i + 2 : 1 : 0) // Mask for the color array.
                                                                                   // >= 2 = primary color, 1 = background color, 0 = null
            .collect(Collectors.toList());
    }

    protected List<Color> passEveryDesiredColors(List<Color> colors, Integer start, Integer every) {
        if (every == null)
            return colors;

        return IntStream.range(0, colors.size())
            .mapToObj(i -> i >= start && (i-start) % every == 0 ? colors.get(i) : null)
            .collect(Collectors.toList());
    }

    protected List<Color> passSubsectionColors(List<Color> colors, List<Section.Subsection> subsections) {
        if (subsections == null)
            return colors;

        return subsections.stream()
            .reduce(new ArrayList<>(), (List<Color> output, Section.Subsection subsection) ->
                IntStream.range(0, colors.size())
                    .mapToObj(i -> i >= subsection.getStart() && i <= subsection.getEnd() &&
                        ColorUtil.colorExists(colors, i) ? colors.get(i) :
                        ColorUtil.colorExists(output, i) ? output.get(i) : null)
                    .collect(Collectors.toList())
            , (a, b) -> a);
    }

    public abstract List<TransitionStep> generateTransitionSteps(Section section);

}
