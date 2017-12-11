package com.desertive.firefly.core.timer.tasks;

import java.awt.Color;
import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.desertive.firefly.core.data.models.Frame;
import com.desertive.firefly.core.data.utils.ColorUtil;
import com.desertive.firefly.core.timer.state.TimerState;

public class FrameTask extends TimerTask {

    private TimerState state;

    public FrameTask() {
        state = TimerState.getInstance();
    }

    @Override
    public void run() {
        List<Color> currentColors = state.getCurrentFrame().getColors();
        List<Color> nextColors = state.getNextFrame().getColors();

        // Check that there are no nulls in color list
        List<Color> colors = IntStream.range(0, Math.max(currentColors.size(), nextColors.size()))
                .mapToObj(i -> ColorUtil.colorExists(nextColors, i) ? nextColors.get(i) : // If next color is present,
                                                                                          // return it.
                        ColorUtil.colorExists(currentColors, i) ? currentColors.get(i) : // Else if current color is
                                                                                         // present, return it.
                                new Color(0, 0, 0)) // Else return black.
                .collect(Collectors.toList());

        state.setCurrentFrame(new Frame(colors));
        state.getSubscriptions().stream().forEach(subscriber -> subscriber.accept(colors));
    }

}
