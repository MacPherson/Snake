package main.java.home.snake;

import main.java.home.Matrix;

import java.awt.*;
import java.util.HashMap;

public class UserSnake extends Snake {
    final private byte headType = Type.USER_HEAD.getNumber();
    final private byte bodyType = Type.USER_BODY.getNumber();

    public UserSnake (final byte[][] initialPosition, Matrix matrix) {
        super(initialPosition, matrix);
        super.setHeadType(headType);
        super.setBodyType(bodyType);

        activateControl();
    }

    public void activateControl() {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new EventDispatcher(this));
    }

    public void gain() {
        super.gain(getNewPosition());
    }

    private HashMap<Matrix.Directions, Integer> getNewPosition() {
        int headYPos = getHead().getY();
        int headXPos = getHead().getX();

        getMatrix().set((short) headYPos, (short) headXPos, Matrix.EMPTY_VALUE);

        return super.getNewPositionByDirection();
    }

    public void removeSnakeTails() {
        super.removeSnakeTails(Type.USER_BODY);
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
