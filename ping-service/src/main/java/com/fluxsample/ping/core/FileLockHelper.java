package com.fluxsample.ping.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

@Component
public class FileLockHelper {
    //保存时间的长度
    private static final int TIME_LEN = 10;

    private RandomAccessFile file = null;
    private FileChannel channel = null;
    private FileLock lock = null;
    @Value("${limit.file.name:ping.lock}")
    private String lockFile = "ping.lock";
    public void close() throws IOException {
        if (channel != null) {
            channel.close();
            channel = null;
        }
        if (file != null) {
            file.close();
            file = null;
        }
    }
    public void open() throws IOException {
        close();
        file = new RandomAccessFile(lockFile, "rw");
        channel = file.getChannel();
    }
    public boolean lock(long bucketIndex) throws IOException {
        if (channel != null) {
            lock = channel.lock(bucketIndex * TIME_LEN, TIME_LEN, false);
            return true;
        }
        return false;
    }
    public void unlock() throws IOException {
        if (lock != null) {
            lock.release();
            lock = null;
        }
    }
    public RandomAccessFile getFile() {
        return file;
    }
}
