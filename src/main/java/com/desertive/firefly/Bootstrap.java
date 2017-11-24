package com.desertive.firefly;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.desertive.firefly.server.SocketIOServer;

@Component
@Profile({"development", "release"})
public class Bootstrap {

    private static final Logger LOG = LoggerFactory.getLogger(FireflyApplication.class);

    @Autowired
    private SocketIOServer server;

    @PostConstruct
    public void start() {
        LOG.info("Starting server");
        server.createServer().start();
    }

    @PreDestroy
    public void stop() {
        LOG.info("Stopping server");
        server.stop();
    }

}
