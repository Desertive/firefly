package com.desertive.firefly.core.services.actions;

import com.desertive.firefly.core.data.models.EasingType;
import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest.Section;
import com.desertive.firefly.core.data.utils.ActionRequestUtil;
import com.desertive.firefly.core.data.utils.ColorUtil;
import com.desertive.firefly.core.services.easings.EasingService;
import com.desertive.firefly.core.services.easings.EasingServiceFactory;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WaveActionService extends ActionService {

    EasingServiceFactory easingServiceFactory;

    public WaveActionService() {
        easingServiceFactory = new EasingServiceFactory();
    }

    /*
     * Wave action presents gradient color with animation
     */
    public List<TransitionStep> generateTransitionSteps(Section section) {
        List<Color> colors;

        if (section.getColors().size() == 1) {
            colors = new ArrayList<Color>() {{
                add(getColorOrThrow(section.getColors().get(0)));
                add(new Color(0, 0, 0));
            }};
        } else {
            colors = super.getColorsOrThrow(section.getColors());
        }

        // How many frames it should take to go through the whole wave for one led
        int speed = ActionRequestUtil.getIntPropertyOrThrow(section.getProperties(), "speed");

        // Gradient length between two colors
        int length = ActionRequestUtil.getIntPropertyOrDefault(section.getProperties(), "length", 20) - 1;

        // Direction of the wave. 0 = ascending order, 1 = descending order
        int direction = ActionRequestUtil.getIntPropertyOrDefault(section.getProperties(), "direction", 0);

        // Construct color mask
        List<Integer> maskList = super.generateRunningNumbers(section.getStart(), section.getEnd());

        // Easing, hard coded for now. Could be changed through the request in the future
        EasingService easingService = easingServiceFactory.getInstance(EasingType.LINEAR);

        // Generate the whole gradient color list
        List<Color> fullGradient = IntStream.range(0, colors.size())
            .mapToObj(i -> new ArrayList<Color>() {{
                add(colors.get(i));
                addAll(easingService.easeTransition(
                    colors.get(i),
                    ColorUtil.colorExists(colors, i+1) ? colors.get(i+1) : colors.get(0),
                    length));
                }}
            )
            .flatMap(list -> list.stream())
            .collect(Collectors.toList());

        List<Color> matchedGradient = matchToSectionLength(fullGradient, section.getEnd() - section.getStart() + 1);

        List<TransitionStep> steps = IntStream.range(0, matchedGradient.size())
            .mapToObj(i -> shiftColors(matchedGradient, i))
            .map(list -> list.subList(0, section.getEnd() - section.getStart() + 1))
            .map(list -> reverseListIfRequested(list, direction))
            .map(list -> new TransitionStep(list, speed / fullGradient.size() - 1, 1))
            .collect(Collectors.toList());

        int skip = Math.max(fullGradient.size() / speed, 1);

        List<TransitionStep> skippedSteps = new ArrayList<>();
        IntStream.range(0, steps.size())
            .forEach(i -> {
                if (i % skip == 0) skippedSteps.add(steps.get(i)); // TODO: remove side-effect
            });
        return skippedSteps;
    }

    List<Color> matchToSectionLength(List<Color> colors, int sectionLength) {
        // If color list is already longer than section, there is no need for multiplying
        if (colors.size() >= sectionLength) return colors;
        // Else multiply color list so that it is longer or equal to the section
        return IntStream.range(0, (int) Math.ceil(sectionLength / (double) colors.size()))
            .mapToObj(i -> colors)
            .flatMap(list -> list.stream())
            .collect(Collectors.toList());
    }

    List<Color> reverseListIfRequested(List<Color> colors, int direction) {
        if (direction == 1) Collections.reverse(colors);
        return colors;
    }

    List<Color> shiftColors(List<Color> colors, int shift) {
        return IntStream.range(0, colors.size())
            .mapToObj(i -> colors.get((i + shift) % colors.size()))
            .collect(Collectors.toList());
    }

}
