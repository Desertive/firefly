package com.desertive.firefly.core.data.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.desertive.firefly.core.data.models.ActionType;
import com.desertive.firefly.core.data.models.requests.ActionRequest.Section;

public class SectionBuilder {

    Section section;

    public SectionBuilder() {
        section = new Section();
    }

    public SectionBuilder setStart(Integer start) {
        section.setStart(start);
        return this;
    }

    public SectionBuilder setEnd(Integer end) {
        section.setEnd(end);
        return this;
    }

    public SectionBuilder setSubsections(List<Section.Subsection> subsections) {
        section.setSubsections(subsections);
        return this;
    }

    public SectionBuilder addSubsection(int start) {
        return addSubsection(start, start);
    }

    public SectionBuilder addSubsection(int start, int end) {
        Section.Subsection subsection = new Section.Subsection() {{
            this.setStart(start);
            this.setEnd(end);
        }};
        if (section.getSubsections() == null) {
            section.setSubsections(new ArrayList<Section.Subsection>() {{
                add(subsection);
            }});
        } else {
            section.getSubsections().add(subsection);
        }
        return this;
    }

    public SectionBuilder setColors(List<HashMap<String, Integer>> colors) {
        section.setColors(colors);
        return this;
    }
    
    public SectionBuilder addColor(int r, int g, int b) {
        HashMap<String, Integer> color = new HashMap<String, Integer>() {{
            put("r", r);
            put("g", g);
            put("b", b);
        }};
        if (section.getColors() == null) {
            section.setColors(new ArrayList<HashMap<String, Integer>>() {{
                add(color);
            }});
        } else {
            section.getColors().add(color);
        }
        return this;
    }
    
    public SectionBuilder setType(ActionType type) {
        section.setType(type);
        return this;
    }

    public SectionBuilder setProperties(HashMap<String, Integer> properties) {
        section.setProperties(properties);
        return this;
    }

    public SectionBuilder addProperty(String key, int value) {
        if (section.getProperties() == null) {
            section.setProperties(new HashMap<String, Integer>() {{
                put(key, value);
            }});
        } else {
            section.getProperties().put(key, value);
        }
        return this;
    }

    public Section build() {
        return section;
    }

}
