package com.desertive.firefly.core.client;

import java.awt.Color;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;

import com.desertive.firefly.core.data.models.Frame;
import com.desertive.firefly.core.data.models.requests.ActionRequest;
import com.desertive.firefly.core.managers.CalculationManager;
import com.desertive.firefly.core.managers.TimerManager;

public class FireflyClientImpl implements FireflyClient {

    @Autowired
    CalculationManager calculationManager;

    @Autowired
    TimerManager timerManager;
    
    public void start() {
        timerManager.start();
    }

    public void stop() {
        timerManager.stop();
    }
    
    public void processAndApply(ActionRequest actionRequest) {
        apply(process(actionRequest));
    }

    public List<Frame> process(ActionRequest actionRequest) {
        return calculationManager.processActionRequest(actionRequest);
    }

    public void apply(List<Frame> frames) {
        timerManager.applyState(frames);
    }
    
    public void subscribe(Consumer<List<Color>> method) {
        timerManager.subscribe(method);
    }

}
