package com.fluxsample.ping.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class PingLimitControl {
    @Autowired
    private FileLockHelper fileLockHelper;
    //每秒最多2个send pong 请求
    @Value("${limit.rps:2}")
    private static  int RPS = 2;

    //保存时间的长度
    private static final int TIME_LEN = 10;

    //共用锁文件
    @Value("${limit.file.name:ping.lock}")
    private static final String LOCK_FILE = "ping.lock";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public boolean isCanSendPing() {
        boolean canSendPing = false;
        try {
            fileLockHelper.open();
            for (long i = 0; i < RPS; i++) {
                if (fileLockHelper.lock(i)) {
                    boolean oneCanSendPing = checkLimit(fileLockHelper.getFile(), i);
                    fileLockHelper.unlock();
                    if (oneCanSendPing) {
                        canSendPing = true;
                        break;
                    }
                }
            }
            fileLockHelper.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Error: {}" + e.getMessage());
        }
        return canSendPing;
    }

    private long readBucketTime(RandomAccessFile file, long bucketIndex) throws IOException {
        file.seek(bucketIndex * TIME_LEN);
        byte[] timeBytes = new byte[TIME_LEN];
        file.read(timeBytes);
        String timeStr = new String(timeBytes);
        if (timeStr.trim().isEmpty()) {
            return 0;
        }
        return Long.parseLong(timeStr.trim());
    }

    private boolean checkLimit(RandomAccessFile file, long bucketIndex) {
        try {
            long nowSecond = System.currentTimeMillis() / 1000;
            long timeInBucket = readBucketTime(file, bucketIndex);
          //  log.info(dtf.format(LocalDateTime.now()) + " bucketIndex " + bucketIndex + " timeInBucket:" + timeInBucket + " nowSecond:" + nowSecond);
            if (timeInBucket >= nowSecond) {
                log.info(dtf.format(LocalDateTime.now()) + " bucketIndex " + bucketIndex + " block!");
                return false;
            }
            log.info(dtf.format(LocalDateTime.now()) + " bucketIndex " + bucketIndex + " Send!");
            file.seek(bucketIndex * TIME_LEN);
            file.writeBytes(String.valueOf(nowSecond));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
