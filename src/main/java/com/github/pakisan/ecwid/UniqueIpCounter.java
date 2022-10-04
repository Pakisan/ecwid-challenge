package com.github.pakisan.ecwid;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Scanner;
import java.util.TreeSet;

public class UniqueIpCounter {

    private final Scanner scanner;
    private final BitSet bitSet = new BitSet();
    private final TreeSet<Integer> treeSet = new TreeSet<>();

    public UniqueIpCounter(Scanner scanner) {
        this.scanner = scanner;
    }

    public int count() throws UnknownHostException {
        while (scanner.hasNext()) {
            var ip = scanner.next();
            var ipBytes = ipToBytes(ip);
            var ipInt = ipBytesToInt(ipBytes);
//            var ipRestored = ipBytesToIp(ipBytes);

//            InetAddress.getByName(ip).getAddress()
//            System.out.printf(
//                    """
//                    ip: %s
//                    ip bytes: %s
//                    ip int: %s
//                    restored ip: %s
//
//                    """, ip, Arrays.toString(ipBytes), ipInt, ipRestored
//            );
//            bitSet.set(ipInt);
            if (ipInt > 0) {
                treeSet.add(ipInt);
            }
        }

//        return bitSet.size();
        return treeSet.size();
    }

    private byte[] ipToBytes(String ip) {
        byte[] ret = new byte[4];

        String[] ipParts = ip.split("\\.");
        ret[0] = (byte) (Integer.parseInt(ipParts[0]) & 0xFF);
        ret[1] = (byte) (Integer.parseInt(ipParts[1]) & 0xFF);
        ret[2] = (byte) (Integer.parseInt(ipParts[2]) & 0xFF);
        ret[3] = (byte) (Integer.parseInt(ipParts[3]) & 0xFF);

        return ret;
    }

    private int ipBytesToInt(byte[] ipBytes) {
        return ByteBuffer.wrap(ipBytes).getInt();
    }

    private String ipBytesToIp(byte[] ipBytes) throws UnknownHostException {
        return InetAddress.getByAddress(ipBytes).getHostAddress();
    }

}
