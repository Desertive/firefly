package com.desertive.firefly.core.managers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.desertive.firefly.core.data.utils.ColorUtil;
import com.desertive.firefly.core.services.FrameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desertive.firefly.core.data.models.Frame;
import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest;
import com.desertive.firefly.core.data.models.requests.ActionRequest.Section;
import com.desertive.firefly.core.services.actions.ActionServiceFactory;

@Service
public class CalculationManager {

    @Autowired
    FrameService frameService;

    ActionServiceFactory actionServiceFactory;

    public CalculationManager() {
        actionServiceFactory = new ActionServiceFactory();
    }

    public List<Frame> processActionRequest(ActionRequest actionRequest) {
        return actionRequest.getSections().stream()
            .map(this::convertSectionsIntoSteps) // 1. Convert each section into list of transition steps
            .map(frameService::convertStepsIntoFrames) // 2. Convert each transition step list into list of frames
            .reduce(new ArrayList<>(), frameService::mergeFrameLists); // 3. Merge frame lists
    }

    List<TransitionStep> convertSectionsIntoSteps(Section section) {
        return actionServiceFactory.getInstance(section.getType())
            .generateTransitionSteps(section).stream()
            .map(step -> passEveryRequestedColor(step, section.getStart(), section.getEvery()))
            .map(step -> addStartingNulls(step, section.getStart(), section.getEnd()))
            .map(step -> passSubsectionColors(step, section.getSubsections()))
            .collect(Collectors.toList());
    }

    TransitionStep passEveryRequestedColor(TransitionStep step, Integer start, Integer every) {
        if (every == null)
            return step;

        final List<Color> colors = step.getColors();

        return new TransitionStep(IntStream.range(0, colors.size())
            .mapToObj(i -> i % every == 0 ? colors.get(i) : null)
            .collect(Collectors.toList()), step.getTransitionTime(), step.getSleep());
    }

    TransitionStep addStartingNulls(TransitionStep step, int start, int end) {
        final List<Color> colors = new ArrayList<Color>() {{
            addAll(Collections.nCopies(start, null));
            addAll(step.getColors());
        }};
        // Let's check just in case that color array doesn't go beyond section's ending point
        if (colors.size() - 1 > end) {
            throw new RuntimeException("Color array goes beyond section ending point");
        }
        return new TransitionStep(colors, step.getTransitionTime(), step.getSleep());
    }

    TransitionStep passSubsectionColors(TransitionStep step, List<Section.Subsection> subsections) {
        if (subsections == null)
            return step;

        final List<Color> colors = step.getColors();

        return new TransitionStep(subsections.stream()
            .reduce(new ArrayList<>(), (List<Color> output, Section.Subsection subsection) ->
                    IntStream.range(0, colors.size())
                        .mapToObj(i -> i >= subsection.getStart() && i <= subsection.getEnd() &&
                            ColorUtil.colorExists(colors, i) ? colors.get(i) :
                            ColorUtil.colorExists(output, i) ? output.get(i) : null)
                        .collect(Collectors.toList())
                , (a, b) -> a), step.getTransitionTime(), step.getSleep());
    }

}
