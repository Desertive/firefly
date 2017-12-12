package com.desertive.firefly.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import com.desertive.firefly.core.timer.tasks.FrameTask;

public class TimerService {

    private final ScheduledExecutorService executor;
    private final int fps;
    private final int heartbeat;
    private final int timerThreads;

    private List<ScheduledFuture<?>> futureTask = new ArrayList<>();

    public TimerService(int fps, int heartbeat, int timerThreads) {
        executor = Executors.newScheduledThreadPool(timerThreads);
        this.fps = fps;
        this.heartbeat = heartbeat;
        this.timerThreads = timerThreads;
    }

    public void applyTimer(Boolean moreThanOneFrame) {
        Long interval;

        if (moreThanOneFrame) {
            interval = 1000L / fps;
        } else {
            interval = (long) heartbeat;
        }

        if (futureTask != null) {
            futureTask.stream().forEach(task -> task.cancel(true));
            futureTask.clear();
        }
        
        IntStream.range(0, timerThreads)
            .forEach(i -> 
                futureTask.add(i, 
                        executor.scheduleAtFixedRate(
                                new FrameTask(), 
                                interval * i, 
                                interval * timerThreads, 
                                TimeUnit.MILLISECONDS))
            );
    }

    public void stop() {
        executor.shutdown();
    }
}
