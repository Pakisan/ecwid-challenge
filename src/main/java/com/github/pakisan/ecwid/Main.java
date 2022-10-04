package com.github.pakisan.ecwid;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        int mb = 1024 * 1024;
        var runtime = Runtime.getRuntime();

        var count = 0;
        var startedAt = Date.from(Instant.now());
        System.out.printf("Started at: %s\n", startedAt);

        var file = new File(Main.class.getResource("/ips.txt").toURI());
        System.out.printf("file size: %s MB\n", Files.size(file.toPath()) / mb);
        count = new UniqueIpCounter().count(file);

        var endedAt = Date.from(Instant.now());
        System.out.printf("Ended at:   %s\n", endedAt);

        System.out.printf("Consumed time: %s\n", Duration.between(startedAt.toInstant(), endedAt.toInstant()));
        System.out.printf("Unique ips: %s\n", count);
        System.out.printf("Used Memory: %s MB\n", ((runtime.totalMemory() - runtime.freeMemory()) / mb));
    }

}