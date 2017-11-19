package com.desertive.firefly.core.service.calculation;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.desertive.firefly.core.data.builders.LedStripSectionBuilder;
import com.desertive.firefly.core.data.models.ActionType;
import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest.LedStripSection;
import com.desertive.firefly.core.services.calculation.ActionService;
import com.desertive.firefly.core.services.calculation.ActionServiceFactory;

public class BlinkActionServiceTest {

	ActionServiceFactory actionServiceFactory = new ActionServiceFactory();
	ActionService actionService;
	LedStripSection ledStripSection;
	
	@Before
	public void initalizeTestEnvironment() {
		actionService = actionServiceFactory.getInstance(ActionType.BLINK);
		ledStripSection = buildLedStripSection();
	}
	
	@Test
	public void itShouldReturnTwoTransitionSteps() {
		assertEquals(actionService.generateTransitionSteps(ledStripSection).size(), 2);
	}
	
	@Test
	public void transitionStepSleepShouldBeNull() {
		assertEquals(actionService.generateTransitionSteps(ledStripSection).get(0).getSleep(), null);
	}
	
	@Test
	public void transitionStepTransitionTimeShouldBeTwo() {
		assertEquals(actionService.generateTransitionSteps(ledStripSection).get(0).getTransitionTime(), 2, 0);
	}
	
	@Test
	public void firstTransitionStepLedListShouldContainOneNullAndThreeBlackColors() {
		List<TransitionStep> transitionSteps = actionService.generateTransitionSteps(ledStripSection);
		
		List<Color> colors = transitionSteps.get(0).getLeds();
		
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
		List<TransitionStep> transitionSteps = actionService.generateTransitionSteps(ledStripSection);
		
		List<Color> colors = transitionSteps.get(1).getLeds();
		
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
	
	private LedStripSection buildLedStripSection() {
		return new LedStripSectionBuilder()
				.setStart(1)
				.setEnd(3)
				.setType(ActionType.BLINK)
				.setProperties(new HashMap<String, Object>()
					{{
						put("r", 2);
						put("g", 2);
						put("b", 2);
						put("interval", 5);
					}}
				)
				.build();
	}
}
