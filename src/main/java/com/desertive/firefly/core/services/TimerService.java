package com.desertive.firefly.core.services;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.desertive.firefly.core.timer.tasks.FrameTask;

public class TimerService {

    private final ScheduledExecutorService executor;
    private final int fps;
    private final int heartbeat;

    private ScheduledFuture<?> futureTask;

    public TimerService(int fps, int heartbeat) {
        executor = Executors.newSingleThreadScheduledExecutor();
        this.fps = fps;
        this.heartbeat = heartbeat;
    }

    public void applyTimer(Boolean moreThanOneFrame) {
        Long interval;

        if (moreThanOneFrame) {
            interval = 1000L / this.fps;
        } else {
            interval = (long) this.heartbeat;
        }

        if (this.futureTask != null) {
            this.futureTask.cancel(true);
        }

        this.futureTask = executor.scheduleAtFixedRate(new FrameTask(), 0L, interval, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        executor.shutdown();
    }
}
