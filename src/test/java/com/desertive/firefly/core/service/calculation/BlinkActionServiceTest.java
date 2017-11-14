package com.desertive.firefly.core.service.calculation;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.desertive.firefly.core.data.builders.LedStripSectionBuilder;
import com.desertive.firefly.core.data.models.ActionType;
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
		//assertEquals(x, 2);
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
