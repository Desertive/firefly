package com.desertive.firefly.core.services.actions;

import com.desertive.firefly.core.data.models.ActionType;

public class ActionServiceFactory {

    StaticActionService staticActionService = new StaticActionService();
    BlinkActionService blinkActionService = new BlinkActionService();
    RandomBlinkActionService randomBlinkActionService = new RandomBlinkActionService();
    WaveActionService waveActionService = new WaveActionService();

    public ActionService getInstance(ActionType actionType) {
        switch (actionType) {
            case STATIC:
                return staticActionService;
            case BLINK:
                return blinkActionService;
            case RANDOM_BLINK:
                return randomBlinkActionService;
            case WAVE:
                return waveActionService;
            default:
                throw new IllegalArgumentException(String.format("Cannot match section type %s", actionType));
        }
    }

}
