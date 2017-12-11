package com.desertive.firefly;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.desertive.firefly.core.client.FireflyClientImpl;
import com.desertive.firefly.serialPort.SerialPortService;
import com.desertive.firefly.server.SocketIOServer;

@Component
@Profile({"development", "release"})
public class Bootstrap {

    private static final Logger LOG = LoggerFactory.getLogger(FireflyApplication.class);

    @Autowired
    SerialPortService serialPortService;
    
    @Autowired
    SocketIOServer server;
    
    @Autowired
    FireflyClientImpl fireflyClient;

    @PostConstruct
    public void start() {
        LOG.info("Starting socket.io server");
        server.create().start();
        
        LOG.info("Subscribing serialport to timer engine");
        fireflyClient.subscribe(serialPortService::write);
    }

    @PreDestroy
    public void stop() {
        LOG.info("Stop timer engine");
        fireflyClient.stop();
        
        LOG.info("Stop socket.io server");
        server.stop();
    }

}
