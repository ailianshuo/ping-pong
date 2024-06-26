package com.fluxsample.ping.core

import spock.lang.Specification

class FileLockHelperTest extends Specification {

    void "test Lock true "() {
        given:
        def fileLockHelper = new FileLockHelper( )
        when:
        fileLockHelper.open()
        def boolLock = fileLockHelper.lock(0)
        fileLockHelper.getFile()
        fileLockHelper.unlock()
        fileLockHelper.close()
        then:
        boolLock == true
    }
    void "test Lock false  "() {
        given:
        def fileLockHelper = new FileLockHelper( )
        when:
        def boolLock = fileLockHelper.lock(0)
        fileLockHelper.unlock()
        fileLockHelper.close()
        then:
        boolLock == false
    }



}
