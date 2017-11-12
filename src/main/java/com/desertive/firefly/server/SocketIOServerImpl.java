package com.desertive.firefly.server;

import java.util.function.Consumer;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.desertive.firefly.validator.ValidatorFactory;

/**
 * 
 * Abstraction for netty-socketio server
 * (https://github.com/mrniko/netty-socketio)
 *
 */
public class SocketIOServerImpl implements SocketIOServer {

	private com.corundumstudio.socketio.SocketIOServer server;
	private Configuration config;
	private ValidatorFactory validatorFactory;

	public SocketIOServerImpl() {
		config = new Configuration();
	}

	public SocketIOServerImpl setHostname(String hostname) {
		config.setHostname(hostname);
		return this;
	}

	public SocketIOServerImpl setPort(Integer port) {
		config.setPort(port);
		return this;
	}
	
	public SocketIOServerImpl setValidator(ValidatorFactory validatorFactory) {
		this.validatorFactory = validatorFactory;
		return this;
	}

	public <T> void addEventListener(Event eventName, Class<T> dataType, Runnable lambda) {
		server.addEventListener(eventName.toString(), dataType, new DataListener<T>() {
			@Override
			public void onData(SocketIOClient client, T data, AckRequest ackRequest) {
				// Request data is not handled thus no validation executed
				lambda.run();
			}
		});
	}

	public <T> void addEventListener(Event eventName, Class<T> dataType, Consumer<T> lambda) {
		server.addEventListener(eventName.toString(), dataType, new DataListener<T>() {
			@Override
			public void onData(SocketIOClient client, T data, AckRequest ackRequest) {
				validatorFactory.isValidOrThrow(data);
				lambda.accept(data);
			}
		});
	}

	public <T> void addConnectListener(Consumer<SocketIOClient> lambda) {
		server.addConnectListener(new ConnectListener() {
			@Override
			public void onConnect(SocketIOClient client) {
				lambda.accept(client);
			}
		});
	}

	public <T> void addDisconnectListener(Consumer<SocketIOClient> lambda) {
		server.addDisconnectListener(new DisconnectListener() {
			@Override
			public void onDisconnect(SocketIOClient client) {
				lambda.accept(client);
			}
		});
	}
	
	public SocketIOServerImpl createServer() {
		server = new com.corundumstudio.socketio.SocketIOServer(config);
		return this;
	}

	public void start() {
		server.start();
	}

	public void stop() {
		server.stop();
	}

}
