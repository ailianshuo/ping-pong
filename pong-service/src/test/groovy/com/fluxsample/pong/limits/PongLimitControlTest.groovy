package com.fluxsample.pong.limits

import spock.lang.Specification

class PongLimitControlTest extends Specification {
    def "IsCanPong"() {
        given:
        def control = new PongLimitControl()

        when:
        control.scheduleWindow()
        def result1 = control.isCanPong()
        int windowNum = 10
        while (windowNum--) {
            control.scheduleWindow()
            control.isCanPong()
        }

        def result2 = control.isCanPong()
        then:
        result1 == true
        result2 == false
    }

}
