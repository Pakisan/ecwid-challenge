package com.github.pakisan.ecwid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.BitSet;

public class UniqueIpCounter {

    private final BitSet bitSet = new BitSet();

    public int count(File file) throws IOException {
        // Faster than scanner and File Channel with Byte Buffer
        try (FileReader fileReader = new FileReader(file)) {
            var buffReader = new BufferedReader(fileReader);

            buffReader.lines()
                    .parallel()
                    .map(this::ipToBytes)
                    .map(this::ipBytesToInt)
                    .forEach(ipInt -> {
                        // Low memory consumption + fast
                        if (ipInt > 0) bitSet.set(ipInt);
                    });
        }

        return bitSet.cardinality();
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
