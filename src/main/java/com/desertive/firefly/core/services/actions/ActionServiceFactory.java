package com.desertive.firefly.core.services.actions;

import com.desertive.firefly.core.data.models.ActionType;

public class ActionServiceFactory {

    StaticActionService staticActionService = new StaticActionService();
    BlinkActionService blinkActionService = new BlinkActionService();

    public ActionService getInstance(ActionType actionType) {
        switch (actionType) {
            case STATIC:
                return staticActionService;
            case BLINK:
                return blinkActionService;
            default:
                throw new IllegalArgumentException(String.format("Cannot match section type %s", actionType));
        }
    }

}
