package com.desertive.firefly.server;

import java.util.function.Consumer;

import com.corundumstudio.socketio.SocketIOClient;

/**
 * 
 * Socket.io server interface
 *
 */
public interface SocketIOServer {
	/**
	 * Socket.io event names
	 */
	public enum Event {
		HEALTH_CHECK, ACTION
	}

	/**
	 * Set hostname of the socket.io server
	 * 
	 * @return SocketIOServer
	 */
	SocketIOServerImpl setHostname(String hostname);

	/**
	 * Set port of the socket.io server
	 * 
	 * @return SocketIOServer
	 */
	SocketIOServerImpl setPort(Integer port);
	
	/**
	 * Creates socket.io server instance with existing configurations
	 * 
	 * @return SocketIOServer
	 */
	SocketIOServerImpl createServer();

	/**
	 * Starts the socket.io server
	 */
	void start();

	/**
	 * Stops the socket.io server
	 */
	void stop();

	/**
	 * Subscribe for event without passable parameters to callback lambda
	 * 
	 * @param eventName
	 *            Enum value of the event name
	 * @param dataType
	 *            Request data model
	 * @param lambda
	 *            Callback function for the event
	 */
	<T> void addEventListener(Event eventName, Class<T> dataType, Runnable lambda);

	/**
	 * Subscribe for event with request data passed to callback lambda
	 * 
	 * @param eventName
	 *            Enum value of the event name
	 * @param dataType
	 *            Request data model
	 * @param lambda
	 *            Callback function for the event. Request object will be passed as
	 *            a parameter
	 */
	<T> void addEventListener(Event eventName, Class<T> dataType, Consumer<T> lambda);

	/**
	 * Subscribe for connection establishing events
	 * 
	 * @param lambda
	 *            Callback function for the event
	 */
	<T> void addConnectListener(Consumer<SocketIOClient> lambda);

	/**
	 * Subscribe for disconnection events
	 * 
	 * @param lambda
	 *            Callback function for the event
	 */
	<T> void addDisconnectListener(Consumer<SocketIOClient> lambda);
}
