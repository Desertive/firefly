package com.desertive.firefly.core.data.utils;

import java.util.HashMap;

public class ActionRequestUtil {

    public static <T> Integer getIntPropertyOrThrow(HashMap<T, Integer> properties, Object key) {
        if (properties == null) {
            throw new IllegalArgumentException("Section didn't contain properties, although it should");
        } else if (!properties.containsKey(key)) {
            throw new IllegalArgumentException(String.format("Section didn't contain property %s", key));
        } else if (!(properties.get(key) instanceof java.lang.Integer)) {
            throw new IllegalArgumentException(String.format("Couldn't cast property's %s value as Integer", key));
        } else {
            return (int) properties.get(key);
        }
    }

}
