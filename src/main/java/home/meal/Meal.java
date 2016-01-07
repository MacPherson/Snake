package main.java.home.meal;

import main.java.home.Matrix;

import java.awt.*;
import java.util.Random;

public class Meal {
    final private Random RANDOM = new Random();
    final private byte TYPE = 3;
    final private Matrix matrix;

    public Meal(Matrix matrix) {
        this.matrix = matrix;
    }

    public void add() {
        boolean thrown = false;
        short randomY, randomX;

        while(!thrown) {
            randomY = (short) RANDOM.nextInt(matrix.getHeight());
            randomX = (short) RANDOM.nextInt(matrix.getWidth());

            if (randomY < 2) {
                randomY = 2;
            }

            if (matrix.get(randomY, randomX) == Matrix.EMPTY_VALUE) {
                matrix.set(randomY, randomX, (byte) 3);
                thrown = true;
            }
        }
    }

    public void draw(final Graphics2D g) {
        final byte CELL_SIZE = matrix.getCellSize();

        for(short yPos = 0; yPos < matrix.getHeight(); yPos += 1) {
            for(short xPos = 0; xPos < matrix.getWidth(); xPos += 1) {
                if (matrix.get(yPos, xPos) == TYPE) {
                    g.setPaint(Color.green);
                    g.fillRect(xPos * CELL_SIZE, yPos * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }
}
