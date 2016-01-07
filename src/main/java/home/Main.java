package main.java.home;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Properties properties = new Properties();
        Logger logger = Logger.getLogger("game");

        short width = 300;
        short height = 300;
        String name = "game";

        FileInputStream loggingProperties = null;

        try {
            loggingProperties = new FileInputStream("logging.properties");
            LogManager.getLogManager().readConfiguration(loggingProperties);
        } catch (SecurityException e) {
            logger.log(Level.WARNING, "It does not have permission of logging.properties");
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failure read of snake.properties");
        } finally {
            try {
                if (loggingProperties != null) {
                    loggingProperties.close();
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "Cannot close logging.properties");
            }
        }

        FileInputStream snakeProperties = null;

        try {
            snakeProperties = new FileInputStream("snake.properties");
            properties.load(snakeProperties);

            width = Short.valueOf(properties.getProperty("game.width"));
            height = Short.valueOf(properties.getProperty("game.height"));
            name = properties.getProperty("game.name");

        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "Snake.properties not found");
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failure read of snake.properties");
        } finally {
            try {
                if (snakeProperties != null) {
                    snakeProperties.close();
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "Cannot close snake.properties");
            }

            Game game = new Game.Builder()
                    .setWidth(width)
                    .setHeight(height)
                    .setName(name)
                    .build();
        }
    }
}
