package com.desertive.firefly.core.data.models;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Frame {

    /*
     * Indicates frame's color state
     */
    private final List<Color> leds;


    public Frame() {
        this.leds = new ArrayList<>();
    }

    public Frame(List<Color> leds) {
        this.leds = leds;
    }

    public List<Color> getLeds() {
        return leds;
    }

}
