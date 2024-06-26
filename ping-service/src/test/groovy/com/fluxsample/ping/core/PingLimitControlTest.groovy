package com.fluxsample.ping.core

import spock.lang.Specification


class PingLimitControlTest extends Specification {
    def limitControl
    def fileLockHelper
    void setup() {
        fileLockHelper = Spy(FileLockHelper)
        limitControl = new PingLimitControl(fileLockHelper)
        //删除lock文件，重置测试状态
        new File("ping.lock").delete()
    }
    def "IsCanSendPing return true"() {
        given:

        when:
        fileLockHelper.lock(_) >> false >> true
        def result = limitControl.isCanSendPing()
        then:
        result
    }

    def "IsCanSendPing return false"() {
        given:

        when:
        def result1 = limitControl.isCanSendPing()
        def result2 = limitControl.isCanSendPing()
        def result3 = limitControl.isCanSendPing()

        then:
        result1
        result2
        !result3
    }

    def "IsCanSendPing return with exception"() {
        given:
        when:
        fileLockHelper.lock(_) >> {throw new IOException()}
        def result = limitControl.isCanSendPing()
        then:
        !result
    }
    def "IsCanSendPing return with exception2"() {
        given:
        when:
        fileLockHelper.getFile() >> null
        def result = limitControl.isCanSendPing()
        then:
        !result
    }

}
