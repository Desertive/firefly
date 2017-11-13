package com.desertive.firefly.core.services.calculation;

import java.util.List;

import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest.LedStripSection;

public interface ActionService {

	List<TransitionStep> generateTransitionSteps(LedStripSection ledStripSection);
	
}
