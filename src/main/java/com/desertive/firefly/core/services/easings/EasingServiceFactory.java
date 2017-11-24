package com.desertive.firefly.core.services.easings;

import com.desertive.firefly.core.data.models.EasingType;

public class EasingServiceFactory {
    LinearEasingService linearEasingService = new LinearEasingService();

    public EasingService getInstance(EasingType easingType) {
        switch (easingType) {
            case LINEAR:
                return linearEasingService;
            default:
                throw new IllegalArgumentException(String.format("Cannot match easing type %s", easingType));
        }
    }
}
