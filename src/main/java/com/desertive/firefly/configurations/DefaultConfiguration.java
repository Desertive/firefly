package com.desertive.firefly.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.desertive.firefly.core.client.FireflyClientImpl;
import com.desertive.firefly.core.services.TimerService;
import com.desertive.firefly.serialPort.SerialPortService;
import com.desertive.firefly.server.SocketIOServer;
import com.desertive.firefly.server.SocketIOServerImpl;
import com.desertive.firefly.validator.ValidatorFactory;

@Configuration
@Profile({"development", "release"})
public class DefaultConfiguration {

    @Value("${firefly.core.fps}")
    private Integer fps;
    
    @Value("${firefly.core.heartbeat}")
    private Integer heartbeat;

    @Value("${firefly.core.timerThreads}")
    private Integer timerThreads;

    @Value("${firefly.serialPort.name}")
    private String serialPortName;
    
    @Value("${firefly.server.hostname}")
    private String hostname;

    @Value("${firefly.server.port}")
    private Integer port;
    
    @Bean
    public SocketIOServer SocketIOServer() {
        return new SocketIOServerImpl()
            .setHostname(hostname)
            .setPort(port)
            .setValidator(new ValidatorFactory());
    }

    @Bean
    public FireflyClientImpl FireflyClient() {
        return new FireflyClientImpl();
    }
    
    @Bean
    public TimerService TimerService() {
        return new TimerService(fps, heartbeat, timerThreads);
    }
    
    @Bean
    public SerialPortService SerialPortService() {
        return new SerialPortService(serialPortName);
    }
}
