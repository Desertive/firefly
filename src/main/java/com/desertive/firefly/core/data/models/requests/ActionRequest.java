package com.desertive.firefly.core.data.models.requests;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.desertive.firefly.core.data.models.ActionType;

public class ActionRequest {
	
	@NotNull(message="There should be sections Array present")
	@Size(min=1)
	@Valid
	private List<LedStripSection> ledStripSections;
	
    public ActionRequest() {}

	public List<LedStripSection> getLedStripSections() {
		return ledStripSections;
	}

	public void setLedStripSections(List<LedStripSection> ledStripSections) {
		this.ledStripSections = ledStripSections;
	}

	public static class LedStripSection {

		@NotNull(message="Section should have starting point")
		@Min(0)
		private Integer start;
		@NotNull(message="Section should have ending point")
		@Min(0)
		private Integer end;
		@NotNull(message="Section should have type of action declared")
		private ActionType type;
		private HashMap<String, String> properties;
		
		public LedStripSection() {}

		public Integer getStart() {
			return start;
		}

		public void setStart(Integer start) {
			this.start = start;
		}

		public Integer getEnd() {
			return end;
		}

		public void setEnd(Integer end) {
			this.end = end;
		}

		public ActionType getType() {
			return type;
		}

		public void setType(ActionType type) {
			this.type = type;
		}

		public HashMap<String, String> getProperties() {
			return properties;
		}

		public void setProperties(HashMap<String, String> properties) {
			this.properties = properties;
		}

	}

}
