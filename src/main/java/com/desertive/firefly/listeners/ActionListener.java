package com.desertive.firefly.listeners;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.desertive.firefly.core.client.FireflyClientImpl;
import com.desertive.firefly.core.data.models.requests.ActionRequest;
import com.desertive.firefly.server.SocketIOServer;
import com.desertive.firefly.server.SocketIOServer.Event;

@Controller
public class ActionListener {

	private static final Logger LOG = LoggerFactory.getLogger(ActionListener.class);

	@Autowired
	private SocketIOServer server;
	
	@Autowired
	private FireflyClientImpl fireflyClient;

	@PostConstruct
	public void init() {

		/*
		 * Bind methods to socket.io listeners. Binding could also be done via
		 * annotations. Feel free to refactor!
		 */
		server.addEventListener(Event.ACTION, ActionRequest.class, this::actionEvent);

		LOG.debug("ActionListener initialized");
	}

	public void actionEvent(ActionRequest actionRequest) {
		LOG.debug("Action event called");
		
		fireflyClient.processAndApply(actionRequest);
	}

}
