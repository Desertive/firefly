package com.desertive.firefly.core.services.actions;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import com.desertive.firefly.core.data.builders.SectionBuilder;
import org.junit.Before;
import org.junit.Test;

import com.desertive.firefly.core.data.models.ActionType;
import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest.Section;

public class BlinkActionServiceTest {

    ActionServiceFactory actionServiceFactory = new ActionServiceFactory();
    ActionService actionService;
    Section section;

    @Before
    public void initalizeTestEnvironment() {
        actionService = actionServiceFactory.getInstance(ActionType.BLINK);
        section = buildSection();
    }

    @Test
    public void itShouldReturnTwoTransitionSteps() {
        assertEquals(actionService.generateTransitionSteps(section).size(), 2);
    }

    @Test
    public void transitionStepSleepShouldBeOne() {
        assertEquals(actionService.generateTransitionSteps(section).get(0).getSleep(), 1);
    }

    @Test
    public void transitionStepTransitionTimeShouldBeOne() {
        assertEquals(actionService.generateTransitionSteps(section).get(0).getTransitionTime(), 1, 0);
    }

    @Test
    public void firstTransitionStepLedListShouldContainOneNullAndThreeBlackColors() {
        List<TransitionStep> transitionSteps = actionService.generateTransitionSteps(section);

        List<Color> colors = transitionSteps.get(0).getColors();

        List<Color> nullColors = colors
            .stream()
            .filter(item -> item == null)
            .collect(Collectors.toList());
        assertEquals(nullColors.size(), 1);

        List<Color> containsColor = colors
            .stream()
            .filter(item -> item != null && item.equals(new Color(0, 0, 0)))
            .collect(Collectors.toList());
        assertEquals(containsColor.size(), 3);
    }

    @Test
    public void secondTransitionStepLedListShouldContainOneNullAndThreeBaseColors() {
        List<TransitionStep> transitionSteps = actionService.generateTransitionSteps(section);

        List<Color> colors = transitionSteps.get(1).getColors();

        List<Color> nullColors = colors
            .stream()
            .filter(item -> item == null)
            .collect(Collectors.toList());
        assertEquals(nullColors.size(), 1);

        List<Color> containsColor = colors
            .stream()
            .filter(item -> item != null && item.equals(new Color(2, 2, 2)))
            .collect(Collectors.toList());
        assertEquals(containsColor.size(), 3);
    }

    private Section buildSection() {
        return new SectionBuilder()
            .setStart(1)
            .setEnd(3)
            .setType(ActionType.BLINK)
            .addColor(2, 2, 2)
            .addProperty("interval", 5)
            .build();
    }
}
