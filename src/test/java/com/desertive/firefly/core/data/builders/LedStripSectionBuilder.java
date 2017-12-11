package com.desertive.firefly.core.data.builders;

import java.util.HashMap;

import com.desertive.firefly.core.data.models.ActionType;
import com.desertive.firefly.core.data.models.requests.ActionRequest.Section;

public class LedStripSectionBuilder {

    Section ledStripSection;

    public LedStripSectionBuilder() {
        ledStripSection = new Section();
    }

    public LedStripSectionBuilder setStart(Integer start) {
        ledStripSection.setStart(start);
        return this;
    }

    public LedStripSectionBuilder setEnd(Integer end) {
        ledStripSection.setEnd(end);
        return this;
    }

    public LedStripSectionBuilder setType(ActionType type) {
        ledStripSection.setType(type);
        return this;
    }

    public LedStripSectionBuilder setProperties(HashMap<String, Integer> properties) {
        ledStripSection.setProperties(properties);
        return this;
    }

    public LedStripSectionBuilder setProperty(String key, Integer value) {
        if (ledStripSection.getProperties() == null) {
            ledStripSection.setProperties(new HashMap<String, Integer>() {{
                put(key, value);
            }});
        } else {
            ledStripSection.getProperties().put(key, value);
        }
        return this;
    }

    public Section build() {
        return ledStripSection;
    }

}
