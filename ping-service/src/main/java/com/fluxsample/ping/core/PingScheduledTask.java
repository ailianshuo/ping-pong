package com.fluxsample.ping.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PingScheduledTask {

    @Autowired
    private PingClient pingClient;

    @Scheduled(fixedRate = 1000)
    public void excecutePing() {
        pingClient.sayHello();
    }
}
