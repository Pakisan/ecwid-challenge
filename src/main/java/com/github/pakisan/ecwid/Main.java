package com.github.pakisan.ecwid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Scanner;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws InterruptedException, UnknownHostException {
        int mb = 1024 * 1024;
        // get Runtime instance
        Runtime instance = Runtime.getRuntime();

        var count = 0;
        var startedAt = Date.from(Instant.now());
        System.out.printf("Started at: %s\n", startedAt);
//        var ips = new TreeSet<String>();
//        var ipsAsInt = new TreeSet<Integer>();

        try {
//            var file = new File("/Users/pavel/Documents/git/ecwid/ips/ips.txt");
//            var file = new File("/Users/pavel/Documents/git/ecwid/ips/ips 2GB.txt");
//            var file = new File("/Users/pavel/Documents/git/ecwid/ips/ips 14GB.txt");
            var file = new File("/Users/pavel/Downloads/ip_addresses");
            System.out.printf("file size: %s MB\n", Files.size(file.toPath()) / mb);
            var scanner = new Scanner(file);
//            var scanner = new Scanner(new File("/Users/pavel/Downloads/ip_addresses"));
//            while (scanner.hasNextLine()) {
//                var ip = scanner.nextLine();
////                System.out.println(ip);
//                InetAddress bar = InetAddress.getByName(ip);
//                int value = ByteBuffer.wrap(bar.getAddress()).getInt();
//                System.out.println(value);
////                ips.add(ip);
//                ipsAsInt.add(value);
//            }
//            scanner.close();
//            count = new UniqueIpCounter().count(scanner);
            count = new UniqueIpCounter().count(file);
//            count = new UniqueIpCounter().count(file.toPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        Thread.sleep(10_000L);
        var endedAt = Date.from(Instant.now());
        System.out.printf("Ended at:   %s\n", endedAt);

//        System.out.printf("Consumed time: %s", startedAt.toInstant().getNano() - startedAt.toInstant().getNano());
        System.out.printf("Consumed time: %s\n", Duration.between(startedAt.toInstant(), endedAt.toInstant()));
        System.out.printf("Unique ips: %s\n", count);
        System.out.printf("Used Memory: %s MB\n", ((instance.totalMemory() - instance.freeMemory()) / mb));

        ///////////////

        // Convert from an IPv4 address to an integer
//        InetAddress bar = InetAddress.getByName("199.199.192.31");
//        short value = ByteBuffer.wrap(bar.getAddress()).getShort();
//        System.out.println(value);
//
//        InetAddress foo = InetAddress.getByName(String.valueOf(value));
//        String address = foo.getHostAddress();
//        System.out.println(address);

//        var ipToBytes = ipToBytes("199.199.192.31");
//        System.out.printf("ipToBytes: %s\n", Arrays.toString(ipToBytes));
//        var ipToInt = ipBytesToInt(ipToBytes);
//        System.out.printf("ipToInt: %s\n", ipToInt);
//
//        var bytesToIp = bytesToIp(ipToBytes);
//        System.out.printf("bytesToIp: %s\n", bytesToIp);
//        var intToIp = ipIntToIp(ipToBytes);
//        System.out.printf("intToIp: %s\n", intToIp);
    }
    
    public static byte[] ipToBytes(String ip) {
        byte[] ret = new byte[4];

        String[] ipParts = ip.split("\\.");
        ret[0] = (byte) (Integer.parseInt(ipParts[0]) & 0xFF);
        ret[1] = (byte) (Integer.parseInt(ipParts[1]) & 0xFF);
        ret[2] = (byte) (Integer.parseInt(ipParts[2]) & 0xFF);
        ret[3] = (byte) (Integer.parseInt(ipParts[3]) & 0xFF);

        return ret;
    }

    public static int ipBytesToInt(byte[] ipBytes) {
        return ByteBuffer.wrap(ipBytes).getInt();
    }

    public static String bytesToIp(byte[] ipBytes) {
        int[] ints = new int[4];
        for (int index = 0; index != 4; index++) {
            ints[index] = Byte.toUnsignedInt(ipBytes[index]);
        }

        return Arrays.toString(ints);
    }

    public static String ipIntToIp(byte[] ipBytes) throws UnknownHostException {
        return InetAddress.getByAddress(ipBytes).getHostAddress();
    }
}