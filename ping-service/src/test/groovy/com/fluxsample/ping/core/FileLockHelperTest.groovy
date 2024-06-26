package com.fluxsample.ping.core

import spock.lang.Specification

import spock.lang.*
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.channels.FileChannel
import java.nio.channels.FileLock


class FileLockHelperTest extends Specification {

    def fileLockHelper
    def mockFile
    def mockChannel
    def mockLock

    void setup() {
        // 初始化mock对象
        mockFile = Mock(RandomAccessFile)
        mockChannel = Mock(FileChannel)
        mockLock = Mock(FileLock)

        // 构造FileLockHelper实例，并注入mock对象

    }

    void "test Lock true "() {
        given:
        fileLockHelper = new FileLockHelper( )
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
        fileLockHelper = new FileLockHelper( )
        when:
        def boolLock = fileLockHelper.lock(0)
        fileLockHelper.unlock()
        fileLockHelper.close()
        then:
        boolLock == false
    }

    def "Lock return with exception"() {
        given:
        fileLockHelper = new FileLockHelper( )
        when:
        fileLockHelper.open()
        fileLockHelper.lock() >> {throw new IOException()}
        def boolLock = fileLockHelper.lock(0)
        fileLockHelper.unlock()
        fileLockHelper.close()
        then:
        print boolLock
    }

}
