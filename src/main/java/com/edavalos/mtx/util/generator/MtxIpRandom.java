package com.edavalos.mtx.util.generator;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Random;

public class MtxIpRandom extends MtxRandom {

    public MtxIpRandom() {
        super(getTimeFromIp());
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
        int ipv4 = Integer.parseInt(String.valueOf(ip.toString()).replaceAll("\\.", ""));
        Random randInt = new Random(ipv4);
        StringBuilder sbr = new StringBuilder();
        for (int i = 0; i < ipv4; i++) {
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
