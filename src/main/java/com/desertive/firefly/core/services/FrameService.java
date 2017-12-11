package com.desertive.firefly.core.services;

import com.desertive.firefly.core.data.models.EasingType;
import com.desertive.firefly.core.data.models.Frame;
import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.utils.ColorUtil;
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
        int inputSize = input.size();
        int framesSize = frames.size();

        // Calculate needed multiplication for smooth animation between sections.
        // For example if section 1's animation is 6 frames long and section 2's
        // animation is 4 frames long, we should match the sizes so that both animations
        // loop seamlessly. So 6 and 4 should convert to 12 frames (6*2 and 4*3).
        int inputMultiplication = calculateInputMultiplication(framesSize, inputSize);

        return IntStream.range(0, inputSize * inputMultiplication)
            .mapToObj(i -> mergeFrames(
                framesSize > 0 ? frames.get(i % framesSize) : new Frame(),
                input.get(i % inputSize)))
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

    int calculateInputMultiplication(int framesSize, int inputSize) {
        if (framesSize == 0 || framesSize == inputSize)
            return 1;

        int framesSizeDivided = framesSize;
        int inputSizeDivided = inputSize;
        while (true) {
            if (framesSizeDivided % 2 == 0 && inputSizeDivided % 2 == 0) {
                framesSizeDivided /= 2;
                inputSizeDivided /= 2;
            } else {
                break;
            }
        }

        return framesSizeDivided;
    }

    Frame mergeFrames(Frame frame, Frame input) {
        List<Color> colors = IntStream.range(0, Math.max(input.getColors().size(), frame.getColors().size()))
            .mapToObj(i -> ColorUtil.colorExists(input.getColors(), i) ? input.getColors().get(i) : // If input color is present, return it.
                ColorUtil.colorExists(frame.getColors(), i) ? frame.getColors().get(i) : // Else if existing color is present, return it.
                    null) // Else return null
            .collect(Collectors.toList());
        return new Frame(colors);

    }
    
    List<Frame> createFrames(TransitionStep current, TransitionStep next) {
        // If there are no other steps, only static color is presented
        if (current.equals(next)) {
            return Arrays.asList(new Frame(current.getColors()));
        }

        final List<Frame> frames = new ArrayList<>();

        // Add freeze frames if requested
        if (current.getSleep() > 0) {
            frames.addAll(
                IntStream.range(0, current.getSleep())
                    .mapToObj(i -> new Frame(current.getColors()))
                    .collect(Collectors.toList())
            );
        }

        // Add transition frames if requested
        if (current.getTransitionTime() > 0) {
            frames.addAll(createTransitionFrames(
                current.getColors(),
                next.getColors(),
                current.getTransitionTime(),
                EasingType.LINEAR) // EasingType hardcoded for now.
                // In the future it should be possible
                // to inherit it from the user request
            );
        }

        return frames;
    }

    List<Frame> createTransitionFrames(List<Color> currentColors,
                                       List<Color> nextColors,
                                       int transitionTime,
                                       EasingType easingType) {
        EasingService easingService = easingServiceFactory.getInstance(easingType);
        return IntStream.range(0, currentColors.size())
            .mapToObj(
                i -> easingService.easeTransition(
                    currentColors.get(i),
                    nextColors.get(i),
                    transitionTime))
            .reduce(
                IntStream.range(0, transitionTime)
                    .mapToObj(i -> new Frame())
                    .collect(Collectors.toList()),
                this::mergeColorTransitions,
                (a, b) -> a);
    }

    List<Frame> mergeColorTransitions(List<Frame> frames, List<Color> input) {
        IntStream.range(0, input.size())
            .forEach(i -> frames.get(i).getColors().add(input.get(i))); // TODO: remove side-effect
        return frames;
    }
}
