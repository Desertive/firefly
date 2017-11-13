package com.desertive.firefly.core.client;

import java.util.List;

import com.desertive.firefly.core.data.models.Frame;
import com.desertive.firefly.core.data.models.requests.ActionRequest;

public interface FireflyClient {
	/*
	 * Default method for processing request and passing the result to timer engine
	 */
	void processAndApply(ActionRequest actionRequest);
	/*
	 * Method for processing request
	 */
	List<Frame> process(ActionRequest actionRequest);
	/*
	 * Method for passing frames to timer engine
	 */
	void apply(List<Frame> frames);
	
}
