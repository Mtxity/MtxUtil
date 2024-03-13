package com.edavalos.mtx.util.generator;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Random;

public class MtxIpRandom extends MtxRandom {

    public MtxIpRandom() {
        super(getTimeFromIp());
    }

    public MtxIpRandom(InetAddress ip) {
        super(getTimeFromIp(ip));
    }

    private static long getTimeFromIp() {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException uhe) {
            ip = new InetSocketAddress("sample", 3030).getAddress();
        }
        return getTimeFromIp(ip);
    }

    private static long getTimeFromIp(InetAddress ip) {
        byte[] ipv4AsBytes = String.valueOf(ip.toString()).replaceAll("\\.", "").getBytes();
        Random randInt = new Random(ByteBuffer.wrap(ipv4AsBytes).getLong());
        StringBuilder sbr = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sbr.append(randInt.nextInt());
        }
        String sbrS = sbr.toString();
        StringBuilder sbn = new StringBuilder();
        for (int j = 0; j < 18; j++) {
            sbn.append(sbrS.charAt(randInt.nextInt(1, 18)));
        }
        return Long.parseLong(sbn.toString());
    }
}
