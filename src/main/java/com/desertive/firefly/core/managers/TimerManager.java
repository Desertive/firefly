package com.desertive.firefly.core.managers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desertive.firefly.core.data.models.Frame;
import com.desertive.firefly.core.services.TimerService;

@Service
public class TimerManager {

    private static final Logger LOG = LoggerFactory.getLogger(TimerManager.class);
    
    @Autowired
    TimerService timerService;
    
    List<Consumer<List<Color>>> subscriptions;

    public void applyState(List<Frame> frames) {
        LOG.info("TimeManager applyState called");
    }
    
    public void start() {
        timerService.start();
    }

    public void stop() {
        timerService.stop();
    }
    
    public void subscribe(Consumer<List<Color>> method) {
        subscriptions.add(method);
    }
}
