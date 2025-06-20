package com.qrlogi.domain.orderitem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;

@Component
@Slf4j
public class SnowflakeIdGenerator {

    private static final long DATACENTER_ID_BITS = 10L;
    private static final long SEQUENCE_BITS = 12L;

    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS;
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + DATACENTER_ID_BITS;

    private static final long TWEPOCH = 1288834974657L; // 기준 시간

    private final long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator() {
        this.datacenterId = resolveDatacenterId();
    }

    public synchronized long nextId() {
        long timestamp = currentTimeMillis();

        if (timestamp < lastTimestamp) {
            log.warn("Clock moved backwards. Waiting {} ms", (lastTimestamp - timestamp));
            timestamp = waitUntilNextMillis(lastTimestamp);
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = waitUntilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - TWEPOCH) << TIMESTAMP_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | sequence;
    }

    private long resolveDatacenterId() {
        try {
            NetworkInterface network = null;
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (!iface.isLoopback() && iface.getHardwareAddress() != null) {
                    network = iface;
                    break;
                }
            }

            if (network == null) throw new NullPointerException("No valid network interface");

            byte[] mac = network.getHardwareAddress();
            byte randomByte = (byte) (new Random().nextInt() & 0xFF);
            return ((0xFF & (long) mac[mac.length - 1]) | (0xFF00 & (((long) randomByte) << 8))) >> 6;
        } catch (SocketException | NullPointerException e) {
            log.warn("Failed to get datacenter ID. Using random fallback.");
            return new Random().nextInt((int) MAX_DATACENTER_ID) + 1;
        }
    }

    private long waitUntilNextMillis(long lastTimestamp) {
        long timestamp = currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTimeMillis();
        }
        return timestamp;
    }

    private long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
