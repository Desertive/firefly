package com.desertive.firefly.core.client;

import java.awt.Color;
import java.util.List;
import java.util.function.Consumer;

import com.desertive.firefly.core.data.models.Frame;
import com.desertive.firefly.core.data.models.requests.ActionRequest;

public interface FireflyClient {
    /**
     * Start timer engine
     */
    void start();
    
    /**
     * Stop timer engine
     */
    void stop();
    
    /**
     * Default method for processing request and passing the result to timer engine
     */
    void processAndApply(ActionRequest actionRequest);

    /**
     * Process request
     */
    List<Frame> process(ActionRequest actionRequest);

    /**
     * Pass frames for timer engine
     */
    void apply(List<Frame> frames);
    
    /**
     * Register a callable method for events.
     * 
     * Whenever the timer engine fires a color notify, 
     * the registered method will be called with a list of colors.
     */
    void subscribe(Consumer<List<Color>> method);

}
