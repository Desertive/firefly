package com.desertive.firefly.core.services.actions;

import com.desertive.firefly.core.data.models.ActionType;

public class ActionServiceFactory {

    StaticActionService staticActionService = new StaticActionService();
    BlinkActionService blinkActionService = new BlinkActionService();
    StarrySkyActionService starrySkyActionService = new StarrySkyActionService();

    public ActionService getInstance(ActionType actionType) {
        switch (actionType) {
            case STATIC:
                return staticActionService;
            case BLINK:
                return blinkActionService;
            case STARRY_SKY:
                return starrySkyActionService;
            default:
                throw new IllegalArgumentException(String.format("Cannot match section type %s", actionType));
        }
    }

}
