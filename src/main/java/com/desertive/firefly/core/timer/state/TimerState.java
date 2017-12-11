package com.desertive.firefly.core.timer.state;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.desertive.firefly.core.data.models.Frame;
import com.desertive.firefly.core.data.models.requests.ActionRequest.Options;

public class TimerState {
    private static TimerState instance = null;

    private List<Frame> frames;
    private int index = -1;
    private boolean runOnce = false;
    private Frame currentFrame = new Frame();
    private List<Consumer<List<Color>>> subscriptions;

    // Singleton pattern
    private TimerState() {
        subscriptions = new ArrayList<Consumer<List<Color>>>();
    }

    public static TimerState getInstance() {
        if (instance == null) {
            synchronized (TimerState.class) {
                // double checked locking
                if (instance == null) {
                    instance = new TimerState();
                }
            }
        }
        return instance;
    }

    public void setFrames(List<Frame> frames, Options options) {
        index = options.getResetAnimation() ? -1 : this.index;
        currentFrame = options.getResetState() ? new Frame() : this.currentFrame;
        runOnce = options.getRunOnce();
        this.frames = frames;
    }

    public Frame getNextFrame() {
        if (frames.size() > index + 1) {
            index++;
            return frames.get(index);
        } else if (frames.size() == index + 1 && runOnce) {
            return frames.get(index);
        } else if (frames.size() <= index + 1) {
            index = 0;
            return frames.get(index);
        } else {
            return new Frame();
        }
    }

    public Frame getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(Frame frame) {
        currentFrame = frame != null ? frame : new Frame();
    }

    public List<Consumer<List<Color>>> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscription(Consumer<List<Color>> subscription) {
        this.subscriptions.add(subscription);
    }
}
