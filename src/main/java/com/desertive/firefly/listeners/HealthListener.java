package com.desertive.firefly.listeners;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.desertive.firefly.server.SocketIOServer;
import com.desertive.firefly.server.SocketIOServer.Event;

@Component
public class HealthListener {

	private static final Logger LOG = LoggerFactory.getLogger(HealthListener.class);

	@Autowired
	private SocketIOServer server;

	@PostConstruct
	public void init() {

		/*
		 * Bind methods to socket.io listeners. Binding could also be done via
		 * annotations. Feel free to refactor!
		 */
		server.addEventListener(Event.HEALTH_CHECK, String.class, this::healthCheckEvent);

		LOG.debug("HealthListener initialized");
	}

	void healthCheckEvent() {
		LOG.info("Up and running!");
	}

}
