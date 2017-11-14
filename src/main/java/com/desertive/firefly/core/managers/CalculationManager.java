package com.desertive.firefly.core.managers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.desertive.firefly.core.data.models.Frame;
import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest;
import com.desertive.firefly.core.data.models.requests.ActionRequest.LedStripSection;
import com.desertive.firefly.core.services.calculation.ActionService;
import com.desertive.firefly.core.services.calculation.ActionServiceFactory;

@Service
public class CalculationManager {

	private static final Logger LOG = LoggerFactory.getLogger(CalculationManager.class);
	
	ActionServiceFactory actionServiceFactory;
	
	public CalculationManager() {
		actionServiceFactory = new ActionServiceFactory();
	}
	
	public List<Frame> processActionRequest(ActionRequest actionRequest) {
		return actionRequest.getLedStripSections()
			.stream()
			.map(this::convertSectionsIntoTransitions) // Convert each section into list of transition steps
			.map(this::convertTransitionsIntoFrames) // Convert each transition step list into list of frames
			.reduce(new ArrayList<Frame>(), this::mergeFrames); // Merge frame lists
	}
	
	public List<TransitionStep> convertSectionsIntoTransitions(LedStripSection ledStripSection) {
		ActionService actionService = actionServiceFactory.getInstance(ledStripSection.getType());
		return actionService.generateTransitionSteps(ledStripSection);
	}
	
	public List<Frame> convertTransitionsIntoFrames(List<TransitionStep> transitionStep) {
		return new ArrayList<Frame>();
	}
	
	public List<Frame> mergeFrames(List<Frame> input, List<Frame> frames) {
		return new ArrayList<Frame>();
	}
	
}