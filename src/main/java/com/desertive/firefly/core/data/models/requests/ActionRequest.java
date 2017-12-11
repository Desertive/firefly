package com.desertive.firefly.core.data.models.requests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.desertive.firefly.core.data.models.ActionType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ActionRequest {

    @JsonCreator
    public static ActionRequest create(String jsonString) throws IOException {
        return new ObjectMapper().readValue(jsonString, ActionRequest.class);
    }

    @NotNull(message = "There should be sections Array present")
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
        private boolean resetState = false;
        private boolean runOnce = false;

        public boolean getResetAnimation() {
            return resetAnimation;
        }

        public void setResetAnimation(Boolean resetAnimation) {
            this.resetAnimation = Boolean.TRUE.equals(resetAnimation);
        }

        public boolean getResetState() {
            return resetState;
        }

        public void setResetState(Boolean resetState) {
            this.resetState = Boolean.TRUE.equals(resetState);
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

    }

}
