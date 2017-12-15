package com.desertive.firefly.core.services.actions;

import com.desertive.firefly.core.data.builders.SectionBuilder;
import com.desertive.firefly.core.data.models.ActionType;
import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest;
import com.greghaskins.spectrum.Spectrum;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.greghaskins.spectrum.dsl.specification.Specification.beforeEach;
import static com.greghaskins.spectrum.dsl.specification.Specification.describe;
import static com.greghaskins.spectrum.dsl.specification.Specification.it;
import static org.junit.Assert.assertEquals;

@RunWith(Spectrum.class)
@SpringBootTest
@ActiveProfiles("test")
public class RandomBlinkActionServiceTest {

    ActionServiceFactory actionServiceFactory = new ActionServiceFactory();
    ActionService actionService;

    /* * * * * * * * * *
     *      Tests      *
     * * * * * * * * * */

    {
        describe("Random blink action", () -> {

            beforeEach(() -> actionService = actionServiceFactory.getInstance(ActionType.RANDOM_BLINK));

            it("should return four transition steps with requested colors", () -> {
                List<TransitionStep> steps = actionService.generateTransitionSteps(buildSection());
                assertEquals(steps.size(), 4);

                List<TransitionStep> stepsContainingFirstNull = steps
                    .stream()
                    .filter(step -> step.getColors().get(0) == null)
                    .collect(Collectors.toList());
                assertEquals(stepsContainingFirstNull.size(), 4);

                List<Color> flatMapColors = steps
                    .stream()
                    .map(step -> step.getColors())
                    .flatMap(colors -> colors.stream())
                    .collect(Collectors.toList());

                assertEquals(
                    flatMapColors.stream()
                        .filter(color -> color != null && color.equals(new Color(2, 2, 2)))
                        .collect(Collectors.toList()).size(), 4);

                assertEquals(
                    flatMapColors.stream()
                        .filter(color -> color != null && color.equals(new Color(1, 1, 1)))
                        .collect(Collectors.toList()).size(), 8);
            });

        });
    }

    private ActionRequest.Section buildSection() {
        return new SectionBuilder()
            .setStart(1)
            .setEnd(3)
            .setType(ActionType.RANDOM_BLINK)
            .addColor(2, 2, 2)
            .addProperty("blinkers", 1)
            .addProperty("transition", 2)
            .addProperty("steps", 4)
            .addProperty("r", 1)
            .addProperty("g", 1)
            .addProperty("b", 1)
            .build();
    }
}
