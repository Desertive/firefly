package com.desertive.firefly.core.services;

import com.desertive.firefly.core.data.models.EasingType;
import com.desertive.firefly.core.data.models.Frame;
import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.services.easings.EasingService;
import com.desertive.firefly.core.services.easings.EasingServiceFactory;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class FrameService {

    EasingServiceFactory easingServiceFactory;

    public FrameService() {
        easingServiceFactory = new EasingServiceFactory();
    }

    public List<Frame> mergeFrameLists(List<Frame> frames, List<Frame> input) {
        return IntStream.range(0, input.size())
                .mapToObj(i -> mergeFrames(frames.get(i), input.get(i)))
                .collect(Collectors.toList());
    }

    public List<Frame> convertStepsIntoFrames(List<TransitionStep> transitionSteps) {
        // Create a list of frames from each step
        // Also pass the next step for transition calculation
        return IntStream.range(0, transitionSteps.size())
                .mapToObj(i -> createFrames(
                        transitionSteps.get(i),
                        transitionSteps.size() > i + 1 ? transitionSteps.get(i + 1) : transitionSteps.get(0))
                )
                .flatMap(frameList -> frameList.stream())
                .collect(Collectors.toList());
    }

    Frame mergeFrames(Frame frame, Frame input) {
        List<Color> colors = IntStream.range(0, input.getLeds().size())
                .mapToObj(i -> input.getLeds().get(i) != null ? input.getLeds().get(i) : frame.getLeds().get(i))
                .collect(Collectors.toList());
        return new Frame(colors);

    }

    List<Frame> createFrames(TransitionStep current, TransitionStep next) {
        // If there are no other steps, only static color is presented
        if (current.equals(next)) {
            return Arrays.asList(new Frame(current.getLeds()));
        }

        final List<Frame> frames = new ArrayList<>();

        // Add freeze frames if requested
        if (current.getSleep() > 0) {
            frames.addAll(
                    IntStream.range(0, current.getSleep())
                            .mapToObj(i -> new Frame(current.getLeds()))
                            .collect(Collectors.toList())
            );
        }

        // Add transition frames if requested
        if (current.getTransitionTime() > 0) {
            frames.addAll(createTransitionFrames(
                    current.getLeds(),
                    next.getLeds(),
                    current.getTransitionTime(),
                    EasingType.LINEAR) // EasingType hardcoded for now.
                    // In the future it should be possible
                    // to inherit it from the user request
            );
        }

        return frames;
    }

    List<Frame> createTransitionFrames(List<Color> currentLeds,
                                       List<Color> nextLeds,
                                       int transitionTime,
                                       EasingType easingType) {
        EasingService easingService = easingServiceFactory.getInstance(easingType);
        return IntStream.range(0, currentLeds.size())
                .mapToObj(
                        i -> easingService.easeTransition(
                                currentLeds.get(i),
                                nextLeds.get(i),
                                transitionTime))
                .reduce(
                        IntStream.range(0, currentLeds.size())
                                .mapToObj(i -> new Frame())
                                .collect(Collectors.toList()),
                        this::mergeLedTransitions,
                        (a, b) -> a);
    }

    List<Frame> mergeLedTransitions(List<Frame> frames, List<Color> input) {
        IntStream.range(0, input.size())
                .forEach(i -> frames.get(i).getLeds().add(input.get(i))); // TODO: remove side-effect
        return frames;
    }
}
