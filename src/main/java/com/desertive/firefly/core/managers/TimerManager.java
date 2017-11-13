package com.desertive.firefly.core.managers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.desertive.firefly.core.data.models.Frame;

@Service
public class TimerManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(TimerManager.class);
	
	public void applyState(List<Frame> frames) {
		LOG.info("TimeManager applyState called");
	}

}
