package com.desertive.firefly.core.data.utils;

import java.awt.Color;
import java.util.List;

public class ColorUtil {

    public static boolean colorExists(List<Color> colors, int i) {
        if (colors.size() <= i || colors.get(i) == null) {
            return false;
        }
        return true;
    }

}
