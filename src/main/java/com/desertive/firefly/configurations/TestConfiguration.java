package com.desertive.firefly.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.desertive.firefly.server.SocketIOServerImpl;
import com.desertive.firefly.core.client.FireflyClientImpl;
import com.desertive.firefly.server.SocketIOServer;

@Configuration
@Profile("test")
public class TestConfiguration {

	@Bean
	public SocketIOServer SocketIOServer() {
		return new SocketIOServerImpl().createServer();
	}
	
	@Bean
	public FireflyClientImpl FireflyClient() {
		return new FireflyClientImpl();
	}

}
