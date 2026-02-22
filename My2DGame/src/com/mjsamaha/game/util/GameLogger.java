package com.mjsamaha.game.util;

import java.io.IOException;
import java.util.logging.*;

public class GameLogger {

    private static final Logger LOGGER = Logger.getLogger("GameLogger");

    static {
        try {
            // Remove default console handler
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            for (Handler h : handlers) {
                rootLogger.removeHandler(h);
            }

            // Console handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            consoleHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(consoleHandler);

            // Optional: file handler
            FileHandler fileHandler = new FileHandler("game.log", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            LOGGER.setLevel(Level.ALL); // set global log level

        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}