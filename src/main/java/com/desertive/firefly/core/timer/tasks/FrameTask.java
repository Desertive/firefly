package com.desertive.firefly.core.timer.tasks;

import java.awt.Color;
import java.util.List;
import java.util.TimerTask;

import com.desertive.firefly.core.timer.state.TimerState;

public class FrameTask extends TimerTask {
    
    private TimerState state;
    
    public FrameTask() {
        state = TimerState.getInstance();
    }
    
    @Override
    public void run() {
        List<Color> colors = state.getNextFrame().getColors();
        state.getSubscriptions().stream().forEach(subscriber ->
            subscriber.accept(colors));
    }

}
