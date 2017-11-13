package com.desertive.firefly.core.services.calculation;

import java.util.ArrayList;
import java.util.List;

import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest.LedStripSection;

public class BlinkActionService implements ActionService {

	public List<TransitionStep> generateTransitionSteps(LedStripSection ledStripSection) {
		return new ArrayList<TransitionStep>(); // WIP
	}
	
}
