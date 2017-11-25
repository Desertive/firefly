package com.desertive.firefly.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.desertive.firefly.core.client.FireflyClientImpl;
import com.desertive.firefly.core.data.models.ActionType;
import com.desertive.firefly.core.data.models.Frame;
import com.desertive.firefly.core.data.models.requests.ActionRequest;
import com.desertive.firefly.core.data.models.requests.ActionRequest.LedStripSection;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FireflyClientTest {

    ActionRequest actionRequest;

    @Autowired
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
        ledStripSection.setProperties(new HashMap<String, Object>() {{
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
    public void processShouldReturnOneFrame() {
        List<Frame> frames = fireflyClient.process(actionRequest);
    }

	/*@Test
	public void processAndApply() {
		fireflyClient.processAndApply(actionRequest);
	}*/

	/*@Test
	public void apply() {
		fireflyClient.apply(new ArrayList<>());
	}*/

}
