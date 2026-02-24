package com.mjsamaha.game.util;

import java.io.IOException;
import java.util.logging.*;

import com.mjsamaha.game.Renderer;

public class GameLogger {
	
	/**
	 * 	import java.util.logging.Level;

	 * Use:
	 * private static final Logger LOGGER =
            Logger.getLogger(NameClass.class.getSimpleName());
	 */

	static {
	    try {
	        Logger rootLogger = Logger.getLogger("");

	        // Remove default handlers
	        for (Handler h : rootLogger.getHandlers()) {
	            rootLogger.removeHandler(h);
	        }

	        // Console (normal verbosity)
	        ConsoleHandler consoleHandler = new ConsoleHandler();
	        consoleHandler.setLevel(Level.INFO);
	        consoleHandler.setFormatter(new SimpleFormatter());
	        rootLogger.addHandler(consoleHandler);

	        // File (full debug)
	        FileHandler fileHandler = new FileHandler("game.log", true);
	        fileHandler.setLevel(Level.ALL);
	        fileHandler.setFormatter(new SimpleFormatter());
	        rootLogger.addHandler(fileHandler);

	        // Root only logs INFO+
	        rootLogger.setLevel(Level.INFO);

	        // 🔥 Your package gets full debug
	        Logger.getLogger("com.mjsamaha").setLevel(Level.ALL);

	    } catch (IOException e) {
	        System.err.println("Failed to initialize logger: " + e.getMessage());
	    }
	}

    // Force static initializer to run
    public static void init() {}
}