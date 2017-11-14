package com.desertive.firefly.core.service.calculation;

import static org.junit.Assert.assertEquals;

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
		ledStripSection = new LedStripSectionBuilder()
							.setStart(0)
							.setEnd(5)
							.setType(ActionType.BLINK)
							.setProperties(new HashMap<String, Object>()
								{{
									put("r", 1);
									put("g", 1);
									put("b", 1);
								}}
							)
							.build();
	}
	
	@Test
	public void itShouldReturnOneTransitionStep() {
		//assertEquals(1, 1);
	}
}
