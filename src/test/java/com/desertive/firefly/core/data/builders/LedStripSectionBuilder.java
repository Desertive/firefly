package com.desertive.firefly.core.data.builders;

import java.util.HashMap;

import com.desertive.firefly.core.data.models.ActionType;
import com.desertive.firefly.core.data.models.requests.ActionRequest.LedStripSection;

public class LedStripSectionBuilder {
	
	LedStripSection ledStripSection;
	
	public LedStripSectionBuilder() {
		ledStripSection = new LedStripSection();
	}
	
	public LedStripSectionBuilder setStart(Integer start) {
		ledStripSection.setStart(start);
		return this;
	}
	
	public LedStripSectionBuilder setEnd(Integer end) {
		ledStripSection.setEnd(end);
		return this;
	}
	
	public LedStripSectionBuilder setType(ActionType type) {
		ledStripSection.setType(type);
		return this;
	}
	
	public LedStripSectionBuilder setProperties(HashMap<String, Object> properties) {
		ledStripSection.setProperties(properties);
		return this;
	}
	
	public LedStripSection build() {
		return ledStripSection;
	}

}
