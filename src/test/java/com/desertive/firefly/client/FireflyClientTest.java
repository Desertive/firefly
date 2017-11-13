package com.desertive.firefly.client;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.desertive.firefly.core.client.FireflyClientImpl;
import com.desertive.firefly.core.data.models.ActionType;
import com.desertive.firefly.core.data.models.Frame;
import com.desertive.firefly.core.data.models.requests.ActionRequest;
import com.desertive.firefly.core.data.models.requests.ActionRequest.LedStripSection;
import com.desertive.firefly.core.managers.CalculationManager;
import com.desertive.firefly.core.managers.TimerManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FireflyClientTest {
	
	private ActionRequest actionRequest;
	
	@Spy
	CalculationManager calculationManager;
	
	@Spy
	TimerManager timerManager;
	
	@InjectMocks
	FireflyClientImpl fireflyClient;
	
	/*
	 * Test setup
	 */
	
	@Before
	public void initActionRequest() {
		actionRequest = generateActionRequest();
	}
	
	ActionRequest generateActionRequest() {
		ActionRequest actionRequest = new ActionRequest();
		
		LedStripSection ledStripSection = new LedStripSection();
		ledStripSection.setStart(0);
		ledStripSection.setEnd(3);
		ledStripSection.setType(ActionType.STATIC);
		ledStripSection.setProperties(new HashMap<String, Object>()
			{{
				put("r", 1);
				put("g", 1);
				put("b", 1);
			}}
		);
		
		actionRequest.setLedStripSections(Arrays.asList(ledStripSection));
		
		return actionRequest;
	}
	
	/*
	 * Actual tests
	 */
	
	@Test
	public void processAndExecuteShouldCallCalculationManager() {
		fireflyClient.processAndApply(actionRequest);
		
		verify(calculationManager).processActionRequest(Mockito.any(ActionRequest.class));
	}
	
	@Test
	public void processShouldCallCalculationManager() {
		fireflyClient.process(actionRequest);
		
		verify(calculationManager).processActionRequest(Mockito.any(ActionRequest.class));
	}

	@Test
	public void executeShouldCallTimerManager() {
		fireflyClient.apply(new ArrayList<Frame>());
		
		verify(timerManager).applyState(Mockito.any());
	}
	
	@Test
	public void staticTypeShouldReturnOneFrame() {
		List<Frame> frames = fireflyClient.process(actionRequest);
		
		// WIP:
		// assertEquals(frames.size(), 1);
		
		verify(calculationManager).processActionRequest(Mockito.any(ActionRequest.class));
	}
	
}
