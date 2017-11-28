package com.desertive.firefly.core.services;

import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerService {
    
    final ScheduledExecutorService executor;
    final int fps;
    final int heartbeat;
    
    public TimerService(int fps, int heartbeat) {
        executor = Executors.newSingleThreadScheduledExecutor();
        this.fps = fps;
        this.heartbeat = heartbeat;
    }
    
    public void start() {
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                System.out.println("Task performed on " + new Date());
            }
        };
        long period = 1000L / this.fps;
        executor.scheduleAtFixedRate(repeatedTask, 0, period, TimeUnit.MILLISECONDS);
    }
    
    public void stop() {
        executor.shutdown();
    }
}
