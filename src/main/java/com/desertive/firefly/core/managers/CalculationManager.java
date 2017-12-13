package com.desertive.firefly.core.managers;

import java.util.ArrayList;
import java.util.List;

import com.desertive.firefly.core.services.FrameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desertive.firefly.core.data.models.Frame;
import com.desertive.firefly.core.data.models.TransitionStep;
import com.desertive.firefly.core.data.models.requests.ActionRequest;
import com.desertive.firefly.core.data.models.requests.ActionRequest.Section;
import com.desertive.firefly.core.services.actions.ActionServiceFactory;

@Service
public class CalculationManager {

    @Autowired
    FrameService frameService;

    ActionServiceFactory actionServiceFactory;

    public CalculationManager() {
        actionServiceFactory = new ActionServiceFactory();
    }

    public List<Frame> processActionRequest(ActionRequest actionRequest) {
        return actionRequest.getSections().stream()
            .map(this::convertSectionsIntoSteps) // 1. Convert each section into list of transition steps
            .map(frameService::convertStepsIntoFrames) // 2. Convert each transition step list into list of frames
            .reduce(new ArrayList<>(), frameService::mergeFrameLists); // 3. Merge frame lists
    }

    List<TransitionStep> convertSectionsIntoSteps(Section section) {
        return actionServiceFactory.getInstance(section.getType()).generateTransitionSteps(section);
    }

}
