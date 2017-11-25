package com.desertive.firefly.core.services.easings;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
 * Linear easing
 *
 * Description:
 * Represents easing as x = y parabel (straight line)
 *
 * Example:
 * If the task is to make transition between color 0 and color 10 in 4 steps,
 * the resulting color values should equal to 2, 4, 6 and 8.
 */
public class LinearEasingService implements EasingService {

    public List<Color> easeTransition(Color current, Color next, int steps) {
        int segments = steps + 1;
        return IntStream.rangeClosed(1, steps)
            .mapToObj(step -> current == null ? null : new Color(
                calculateColor(current.getRed(), next.getRed(), step, segments),
                calculateColor(current.getGreen(), next.getGreen(), step, segments),
                calculateColor(current.getBlue(), next.getBlue(), step, segments)
            ))
            .collect(Collectors.toList());
    }

    int calculateColor(int currentColor, int nextColor, int step, int segments) {
        Double result = currentColor + (nextColor - currentColor) / (double) segments * (double) step;
        return result.intValue();
    }
}
