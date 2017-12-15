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

public class StaticActionServiceTest {

    ActionServiceFactory actionServiceFactory = new ActionServiceFactory();
    ActionService actionService;

    @Before
    public void initalizeTestEnvironment() {
        actionService = actionServiceFactory.getInstance(ActionType.STATIC);
    }

    @Test
    public void itShouldReturnOneTransitionStep() {
        assertEquals(actionService.generateTransitionSteps(buildSection()).size(), 1);
    }

    @Test
    public void transitionStepSleepShouldBeOne() {
        assertEquals(actionService.generateTransitionSteps(buildSection()).get(0).getSleep(), 1);
    }

    @Test
    public void transitionStepTransitionTimeShouldBeZero() {
        assertEquals(actionService.generateTransitionSteps(buildSection()).get(0).getTransitionTime(), 0);
    }

    @Test
    public void transitionStepLedListShouldContainThreeNullsAndFourColors() {
        List<TransitionStep> transitionSteps = actionService.generateTransitionSteps(buildSection());

        List<Color> colors = transitionSteps.get(0).getColors();

        List<Color> nullColors = colors
            .stream()
            .filter(item -> item == null)
            .collect(Collectors.toList());
        assertEquals(nullColors.size(), 3);

        List<Color> containsColor = colors
            .stream()
            .filter(item -> item != null && item.equals(new Color(1, 1, 1)))
            .collect(Collectors.toList());
        assertEquals(containsColor.size(), 4);
    }

    @Test
    public void transitionStepLedListShouldContainFourColorsAndLoopThem() {
        List<TransitionStep> transitionSteps = actionService.generateTransitionSteps(buildSectionWithFourColors());

        List<Color> colors = transitionSteps.get(0).getColors();

        assertEquals(colors.get(0), new Color(1, 1, 1));
        assertEquals(colors.get(1), new Color(3, 3, 3));
        assertEquals(colors.get(2), new Color(2, 2, 2));
        assertEquals(colors.get(3), new Color(4, 4, 4));
        assertEquals(colors.get(4), new Color(1, 1, 1));
        assertEquals(colors.get(5), new Color(3, 3, 3));
        assertEquals(colors.get(6), new Color(2, 2, 2));
        assertEquals(colors.get(7), new Color(4, 4, 4));
        assertEquals(colors.get(8), new Color(1, 1, 1));
    }

    private Section buildSection() {
        return new SectionBuilder()
            .setStart(3)
            .setEnd(6)
            .setType(ActionType.STATIC)
            .addColor(1, 1, 1)
            .build();
    }

    private Section buildSectionWithFourColors() {
        return new SectionBuilder()
            .setStart(0)
            .setEnd(9)
            .setType(ActionType.STATIC)
            .addColor(1, 1, 1)
            .addColor(3, 3, 3)
            .addColor(2, 2, 2)
            .addColor(4, 4, 4)
            .build();
    }

}
