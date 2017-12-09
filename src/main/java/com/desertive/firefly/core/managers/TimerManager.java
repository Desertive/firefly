package com.desertive.firefly.core.managers;

import java.awt.Color;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desertive.firefly.core.data.models.Frame;
import com.desertive.firefly.core.services.TimerService;
import com.desertive.firefly.core.timer.state.TimerState;

@Service
public class TimerManager {

    private static final Logger LOG = LoggerFactory.getLogger(TimerManager.class);
    
    private TimerState state;
    
    public TimerManager() {
        state = TimerState.getInstance();
    }
    
    @Autowired
    TimerService timerService;
    
    List<Consumer<List<Color>>> subscriptions;

    public void applyState(List<Frame> frames, Boolean runOnce) {
        LOG.info(String.format("Setting a new state with %d frames",frames.size()));
        state.setFrames(frames, false);
        timerService.applyTimer(frames.size() > 1);
    }

    public void stop() {
        timerService.stop();
    }
    
    public void subscribe(Consumer<List<Color>> method) {
        state.setSubscription(method);
    }
}
