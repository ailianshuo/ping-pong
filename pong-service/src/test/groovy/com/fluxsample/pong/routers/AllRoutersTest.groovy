package com.fluxsample.pong.routers

import com.fluxsample.pong.handlers.PongHandler
import com.fluxsample.pong.limits.PongLimitControl
import spock.lang.Specification

class AllRoutersTest extends Specification {
    def "test all router"() {
        given:
        def pongLimitControl = Mock(PongLimitControl.class)
        def handler = new PongHandler(pongLimitControl)
        def allRouters = new AllRouters()
        when:
        def responseMono = allRouters.pongRouter(handler)

        then:
        println responseMono

    }
}
