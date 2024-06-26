package com.fluxsample.ping.core

import org.springframework.http.HttpStatus
import spock.lang.Specification

class PingScheduledTaskTest extends Specification {
    def "ExcecutePing"() {
        given:
        def pingClient = Mock(PingClient)
        def pingLimitControl = Mock(PingLimitControl)
        def pingScheduledTask = new PingScheduledTask(pingClient:pingClient,pingLimitControl:pingLimitControl)
        when:
        pingLimitControl.isCanSendPing() >> Boolean.valueOf(canPing)
        pingScheduledTask.excecutePing()
        then:
        print pingClient
        where:
        canPing << [ "false","true"]

    }
}
