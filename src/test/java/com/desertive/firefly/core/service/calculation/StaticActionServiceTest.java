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

public class StaticActionServiceTest {
	
	ActionServiceFactory actionServiceFactory = new ActionServiceFactory();
	ActionService actionService;
	LedStripSection ledStripSection;
	
	@Before
	public void initalizeTestEnvironment() {
		actionService = actionServiceFactory.getInstance(ActionType.STATIC);
		ledStripSection = buildLedStripSection();
	}
	
	@Test
	public void itShouldReturnOneTransitionStep() {
		assertEquals(actionService.generateTransitionSteps(ledStripSection).size(), 1);
	}
	
	@Test
	public void transitionStepSleepShouldBeNull() {
		assertEquals(actionService.generateTransitionSteps(ledStripSection).get(0).getSleep(), null);
	}
	
	@Test
	public void transitionStepTransitionTimeShouldBeNull() {
		assertEquals(actionService.generateTransitionSteps(ledStripSection).get(0).getTransitionTime(), null);
	}
	
	@Test
	public void transitionStepLedListShouldContainThreeNullsAndFourColors() {
		List<TransitionStep> transitionSteps = actionService.generateTransitionSteps(ledStripSection);
		
		List<Color> colors = transitionSteps.get(0).getLeds();
		
		List<Color> nullColors = colors
			.stream()
			.filter(item -> item == null)
			.collect(Collectors.toList());
		assertEquals(nullColors.size(), 3);

		List<Color> containsColor = colors
			.stream()
			.filter(item -> item != null)
			.collect(Collectors.toList());
		assertEquals(containsColor.size(), 4);
	}
	
	private LedStripSection buildLedStripSection() {
		return new LedStripSectionBuilder()
				.setStart(3)
				.setEnd(6)
				.setType(ActionType.BLINK)
				.setProperties(new HashMap<String, Object>()
					{{
						put("r", 1);
						put("g", 1);
						put("b", 1);
						put("interval", 5);
					}}
				)
				.build();
	}

}
