package com.desertive.firefly.listeners;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOClient;
import com.desertive.firefly.server.SocketIOServer;

@Component
public class ConnectionListener {

	private static final Logger LOG = LoggerFactory.getLogger(ConnectionListener.class);

	@Autowired
	private SocketIOServer server;

	@PostConstruct
	public void init() {

		/*
		 * Bind methods to socket.io listeners. Binding could also be done via
		 * annotations. Feel free to refactor!
		 */
		server.addConnectListener(this::onConnect);
		server.addDisconnectListener(this::onDisconnect);

		LOG.debug("ConnectionListener initialized");
	}

	private void onConnect(SocketIOClient client) {
		LOG.info("New client connected! " + client.getSessionId());
	}

	private void onDisconnect(SocketIOClient client) {
		LOG.info("Client disconnected. " + client.getSessionId());
	}

}
