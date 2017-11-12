package com.desertive.firefly.listeners;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.desertive.firefly.core.data.models.requests.ActionRequest;
import com.desertive.firefly.server.SocketIOServer;
import com.desertive.firefly.server.SocketIOServer.Event;

@Component
public class ActionListener {

	private static final Logger LOG = LoggerFactory.getLogger(ActionListener.class);

	@Autowired
	private SocketIOServer server;

	@PostConstruct
	public void init() {

		/*
		 * Bind methods to socket.io listeners. Binding could also be done via
		 * annotations. Feel free to refactor!
		 */
		server.addEventListener(Event.ACTION, ActionRequest.class, this::actionEvent);

		LOG.debug("ActionListener initialized");
	}

	public void actionEvent(ActionRequest data) {
		LOG.info("Action event called.");
	}

}
