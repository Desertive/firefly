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
    Section section;

    @Before
    public void initalizeTestEnvironment() {
        actionService = actionServiceFactory.getInstance(ActionType.STATIC);
        section = buildSection();
    }

    @Test
    public void itShouldReturnOneTransitionStep() {
        assertEquals(actionService.generateTransitionSteps(section).size(), 1);
    }

    @Test
    public void transitionStepSleepShouldBeOne() {
        assertEquals(actionService.generateTransitionSteps(section).get(0).getSleep(), 1);
    }

    @Test
    public void transitionStepTransitionTimeShouldBeZero() {
        assertEquals(actionService.generateTransitionSteps(section).get(0).getTransitionTime(), 0);
    }

    @Test
    public void transitionStepLedListShouldContainThreeNullsAndFourColors() {
        List<TransitionStep> transitionSteps = actionService.generateTransitionSteps(section);

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

    private Section buildSection() {
        return new SectionBuilder()
            .setStart(3)
            .setEnd(6)
            .setType(ActionType.STATIC)
            .addColor(1, 1, 1)
            .build();
    }

}
