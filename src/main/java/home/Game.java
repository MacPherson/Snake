package main.java.home;

import main.java.home.meal.Meal;
import main.java.home.snake.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.util.*;

public class Game extends Frame {
    final private byte FPS = 10;
    final private short width;
    final private short height;
    final private String name;
    final private Matrix matrix;
    final private UserSnake userSnake;
    final private BotSnake botSnake;
    final private Meal meal;
    final private Random RANDOM = new Random();
    private boolean isRunning = true;

    public Game(Game.Builder config) {
        super(config.name);

        this.name = config.name;

        setSize(config.width, config.height);

        Dimension gameSize = getSize();
        this.width = (short) gameSize.width;
        this.height = (short) gameSize.height;

        matrix = new Matrix.Builder()
                .setHeight(this.height)
                .setWidth(this.width)
                .build();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });


        this.userSnake = new UserSnake(new byte[][]{{14,10}, {13,10}, {12,10}, {11,10}, {10,10}}, matrix);
        this.botSnake = new BotSnake(new byte[][]{{14, 11}, {13, 11}, {12, 11}, {11, 11}, {10, 11}}, matrix);
        this.meal = new Meal(matrix);

        this.userSnake.updateMatrixBySnake();
        this.botSnake.updateMatrixBySnake();

        setVisible(true);
        run();
    }

    private void run() {
        while(isRunning) {
            try{
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException e) {};

            this.userSnake.removeSnakeTails();
            this.userSnake.gain();
            this.userSnake.updateMatrixBySnake();
            if (!this.userSnake.validate()) {
                isRunning = false;
            }

            this.botSnake.removeSnakeTails();
            this.botSnake.gain();
            this.botSnake.updateMatrixBySnake();
            if (!this.botSnake.validate()) {
                isRunning = false;
            }

            if (isRunning) {
                if (RANDOM.nextInt(100) < 20) {
                    meal.add();
                }
                removeAll();
                revalidate();
            }
            repaint();
        }
    }

    @Override
    public void paint(final Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        drawGrid(g2);
        userSnake.draw(g2);
        botSnake.draw(g2);
        meal.draw(g2);
    }

    private void drawGrid(final Graphics2D g) {
        for(int i = 0; i < height; i += matrix.getCellSize()) {
            Line2D.Float aFloat = new Line2D.Float(0, i, width, i);
            g.draw(aFloat);
        }

        for(int i = 0; i < width; i += matrix.getCellSize()) {
            Line2D.Float aFloat = new Line2D.Float(i, 0, i, height);
            g.draw(aFloat);
        }
    }

    public static class Builder {
        private short width;
        private short height;
        private String name;

        public Builder setWidth(short width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(short height) {
            this.height = height;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Game build() {
            if (width <= 0) {throw new IllegalArgumentException("Width should be more 0");}
            if (height <= 0) {throw new IllegalArgumentException("Height should be more 0");}

            return new Game(this);
        }
    }
}
