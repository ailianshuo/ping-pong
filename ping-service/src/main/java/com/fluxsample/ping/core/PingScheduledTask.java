package com.fluxsample.ping.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PingScheduledTask {

    @Autowired
    private PingClient pingClient;

    @Autowired
    private PingLimitControl pingLimitControl;
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
