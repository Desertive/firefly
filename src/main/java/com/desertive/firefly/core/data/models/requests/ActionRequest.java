package com.desertive.firefly.core.data.models.requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.desertive.firefly.core.data.models.ActionType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ActionRequest {

    @JsonCreator
    public static ActionRequest create(String jsonString) throws IOException {
        return new ObjectMapper()
                .readValue(jsonString, ActionRequest.class);
    }

    @NotNull(message = "There should be sections array present")
    @Size(min = 1)
    @Valid
    private List<Section> sections;

    private Options options = new Options();

    public ActionRequest() {
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options != null ? options : new Options();
    }

    public static class Options {

        private boolean resetAnimation = false;
        private boolean resetColors = false;
        private boolean runOnce = false;

        public boolean getResetAnimation() {
            return resetAnimation;
        }

        public void setResetAnimation(Boolean resetAnimation) {
            this.resetAnimation = Boolean.TRUE.equals(resetAnimation);
        }

        public boolean getResetColors() {
            return resetColors;
        }

        public void setResetColors(Boolean resetColors) {
            this.resetColors = Boolean.TRUE.equals(resetColors);
        }

        public boolean getRunOnce() {
            return runOnce;
        }

        public void setRunOnce(Boolean runOnce) {
            this.runOnce = Boolean.TRUE.equals(runOnce);
            this.resetAnimation = Boolean.TRUE.equals(runOnce) == true ? true : this.resetAnimation;
        }

    }

    public static class Section {

        @NotNull(message = "Section should have starting point")
        @Min(0)
        private Integer start;
        @NotNull(message = "Section should have ending point")
        @Min(0)
        private Integer end;
        private List<Subsection> subsections;
        @NotNull(message = "Section should have at least one color")
        @Size(min = 1)
        private List<HashMap<String, Integer>> colors;
        @NotNull(message = "Section should have type of action declared")
        private ActionType type;
        private HashMap<String, Integer> properties;
        
        public Section() {
        }

        public Integer getStart() {
            return start;
        }

        public void setStart(Integer start) {
            this.start = start;
        }

        public Integer getEnd() {
            return end;
        }

        public void setEnd(Integer end) {
            this.end = end;
        }

        public List<Subsection> getSubsections() {
            return subsections;
        }

        public void setSubsections(List<Subsection> subsections) {
            this.subsections = subsections;
        }

        public List<HashMap<String, Integer>> getColors() {
            return colors;
        }

        public void setColors(List<HashMap<String, Integer>> colors) {
            this.colors = colors;
        }
        
        public void setColor(HashMap<String, Integer> color) {
            this.colors = new ArrayList<HashMap<String, Integer>>() {{
                add(color);
            }};
        }

        public ActionType getType() {
            return type;
        }

        public void setType(ActionType type) {
            this.type = type;
        }

        public HashMap<String, Integer> getProperties() {
            return properties;
        }

        public void setProperties(HashMap<String, Integer> properties) {
            this.properties = properties;
        }

        public static class Subsection {

            @NotNull(message = "Subsection should have starting point")
            @Min(0)
            private Integer start;
            @Min(0)
            private Integer end;

            public Integer getStart() {
                return start;
            }

            public void setStart(Integer start) {
                this.start = start;
            }

            public Integer getEnd() {
                return end != null ? end : start;
            }

            public void setEnd(Integer end) {
                this.end = end;
            }

        }

    }

}
