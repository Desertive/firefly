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

    public void stop() {
        timerManager.stop();
    }
    
    public void processAndApply(ActionRequest actionRequest) {
        apply(process(actionRequest), actionRequest.getRunOnce());
    }

    public List<Frame> process(ActionRequest actionRequest) {
        return calculationManager.processActionRequest(actionRequest);
    }

    public void apply(List<Frame> frames, Boolean runOnce) {
        timerManager.applyState(frames, runOnce);
    }
    
    public void subscribe(Consumer<List<Color>> method) {
        timerManager.subscribe(method);
    }

}
