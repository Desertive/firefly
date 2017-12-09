package com.desertive.firefly.core.data.models;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Frame {

    /*
     * Indicates frame's color state
     */
    private final List<Color> colors;


    public Frame() {
        this.colors = new ArrayList<>();
    }

    public Frame(List<Color> colors) {
        this.colors = colors;
    }

    public List<Color> getColors() {
        return colors;
    }

}
