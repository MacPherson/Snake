package main.java.home.snake;

import main.java.home.Matrix;

import java.util.HashMap;

public class BotSnake extends Snake {
    final private byte headType = Type.BOT_HEAD.getNumber();
    final private byte bodyType = Type.BOT_BODY.getNumber();

    public BotSnake (final byte[][] initialPosition, Matrix matrix) {
        super(initialPosition, matrix);
        super.setHeadType(headType);
        super.setBodyType(bodyType);
    }

    public void gain() {
        super.gain(getNewPosition());
    }

    public HashMap<Matrix.Directions, Short> getClosestMealPosition() {
        int headYPos = getHead().getY();
        int headXPos = getHead().getX();

        Double saveDistances = 0.0;
        short mealXPos = -1;
        short mealYPos = -1;

        for(short yPos = 0; yPos < getMatrix().getHeight(); yPos += 1) {
            for(short xPos = 0; xPos < getMatrix().getWidth(); xPos += 1) {
                if (getMatrix().get(yPos, xPos) == 3) {
                    int cathetusX = Math.abs(headXPos - xPos);
                    int cathetusY = Math.abs(headYPos - yPos);
                    Double distances = Math.sqrt(cathetusX * cathetusX + cathetusY * cathetusY);
                    if (saveDistances == 0.0 || saveDistances > distances) {
                        saveDistances = distances;
                        mealXPos = xPos;
                        mealYPos = yPos;
                    }
                }
            }
        }

        HashMap<Matrix.Directions, Short> mealPosition = new HashMap<>();
        mealPosition.put(Matrix.Directions.X, mealXPos);
        mealPosition.put(Matrix.Directions.Y, mealYPos);

        return mealPosition;
    }

    private HashMap<Matrix.Directions, Integer> getNewPosition() {
        int headYPos = getHead().getY();
        int headXPos = getHead().getX();

        HashMap<Matrix.Directions, Short> closestMealPosition = getClosestMealPosition();
        int mealXPos = closestMealPosition.get(Matrix.Directions.X);
        int mealYPos = closestMealPosition.get(Matrix.Directions.Y);

        getMatrix().set((short) headYPos, (short) headXPos, Matrix.EMPTY_VALUE);

        boolean foundMeal = mealXPos != -1 && mealYPos != -1;

        int newY;
        int newX;

        if (foundMeal) {
            if (mealXPos != headXPos) {
                if (mealXPos < headXPos) {
                    newX = headXPos - 1;
                } else if (mealXPos > headXPos) {
                    newX = headXPos + 1;
                } else {
                    throw new IllegalStateException("Wrong calculation of new X position of snake");
                }
                newY = headYPos;
            } else {
                if (mealYPos < headYPos) {
                    newY = headYPos - 1;
                } else if (mealYPos > headYPos) {
                    newY = headYPos + 1;
                } else {
                    throw new IllegalStateException("Wrong calculation of new Y position of snake");
                }
                newX = headXPos;
            }

            HashMap<Matrix.Directions, Integer> newPosition = new HashMap<>();
            newPosition.put(Matrix.Directions.X, newX);
            newPosition.put(Matrix.Directions.Y, newY);

            return newPosition;
        } else {
            return super.getNewPositionByDirection();
        }
    }

    public void removeSnakeTails() {
        super.removeSnakeTails(Type.BOT_BODY);
    }

    @Override
    public String toString() {
        return "Snake{" +
                "headType=" + this.headType + "," +
                "bodyType=" + this.bodyType + "," +
                "head=" + this.getHead() +
                "}";
    }
}
