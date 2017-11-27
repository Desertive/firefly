package com.desertive.firefly.core.client;

import java.util.List;

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

    public void processAndApply(ActionRequest actionRequest) {
        apply(process(actionRequest));
    }

    public List<Frame> process(ActionRequest actionRequest) {
        return calculationManager.processActionRequest(actionRequest);
    }

    public void apply(List<Frame> frames) {
        timerManager.applyState(frames);
    }

}
