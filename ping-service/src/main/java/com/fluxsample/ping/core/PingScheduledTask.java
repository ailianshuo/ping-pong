package com.fluxsample.ping.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PingScheduledTask {


    private final PingClient pingClient;


    private final PingLimitControl pingLimitControl;

    public PingScheduledTask(PingClient pingClient, PingLimitControl pingLimitControl) {
        this.pingClient = pingClient;
        this.pingLimitControl = pingLimitControl;
    }

    @Scheduled(fixedRate = 1000)
    public void excecutePing() {
        log.info("start ping");
        if (pingLimitControl.isCanSendPing()) {
            pingClient.sayHello();
        }else{
            log.warn("Request not send as being “rate limited”.");
        }
    }
}
