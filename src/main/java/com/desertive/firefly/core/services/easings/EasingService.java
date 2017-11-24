package com.desertive.firefly.core.services.easings;

import java.awt.Color;
import java.util.List;

public interface EasingService {

    /*
     * Calculate one LED's transition
     */
    List<Color> easeTransition(Color current, Color next, int steps);

}
