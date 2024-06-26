package com.fluxsample.pong.limits;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class PongLimitControl {
    private final LinkedList<Integer> windowBuffer = new LinkedList<>();

    // The maximum number of requests per second for throttling
    @Value("${limit.totle.num:1}")
    private int totalNum = 1;

    //The number of cells in the sliding time window
    @Value("${limit.window.num:10}")
    private int windowNum = 10 ;

    /**
     * @return false: limit，true: canPong
     */
    public synchronized boolean isCanPong() {

        int sumRequest = windowBuffer.stream()
                .mapToInt(Integer::intValue)
                .sum();
        if ((sumRequest + 1) > totalNum) {

            return false;
        }

        windowBuffer.set(windowBuffer.size() - 1, windowBuffer.peekLast() + 1);
        return true;
    }

    @Scheduled(fixedDelayString = "${limit.window.length}")
    public synchronized void scheduleWindow() {
        windowBuffer.addLast(0);
        if (windowBuffer.size() > windowNum) {
            windowBuffer.removeFirst();
        }
    }
}