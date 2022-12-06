package com.city.builder.game;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMain {
    @Test
    void startServer() throws InterruptedException {
        String [] args = new String[1];
        args[0] = "http://localhost:4444/";
        assertNotNull(Main.startServer(args[0]));

    }
}
