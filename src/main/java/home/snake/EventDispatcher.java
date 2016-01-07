package main.java.home.snake;

import java.awt.*;
import java.awt.event.KeyEvent;

public class EventDispatcher implements KeyEventDispatcher {
    private Snake snake;

    public EventDispatcher(final Snake snake) {
        this.snake = snake;
    }

    @Override
    public boolean dispatchKeyEvent(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (snake.getDirection() != Directions.RIGHT) {
                snake.setDirection(Directions.LEFT);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (snake.getDirection() != Directions.LEFT) {
                snake.setDirection(Directions.RIGHT);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (snake.getDirection() != Directions.DOWN) {
                snake.setDirection(Directions.UP);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (snake.getDirection() != Directions.UP) {
                snake.setDirection(Directions.DOWN);
            }
        }

        return false;
    }
}
