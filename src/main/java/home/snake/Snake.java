package main.java.home.snake;

import main.java.home.Matrix;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Snake {
    private byte headType;
    private byte bodyType;
    final byte[][] initialPosition;
    final private Cell head;
    private short size;
    final private Matrix matrix;
    private Directions direction = Directions.RIGHT;

    public Snake(final byte[][] initialPosition, final Matrix matrix) {
        this.initialPosition = initialPosition;
        this.matrix = matrix;

        this.size = (short) this.initialPosition.length;

        head = new Cell(this.initialPosition[0][0], this.initialPosition[0][1]);
        Cell lastCell = head;

        for(int i = 1; i < this.initialPosition.length; i += 1) {
            lastCell.setLink(new Cell(this.initialPosition[i][0], this.initialPosition[i][1]));
            lastCell = lastCell.getLink();
        }
    }

    public byte getHeadType() {
        return headType;
    }

    public byte getBodyType() {
        return bodyType;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setHeadType(final byte headType) {
        this.headType = headType;
    }

    public void setBodyType(final byte bodyType) {
        this.bodyType = bodyType;
    }

    public enum Type {
        USER_HEAD((byte) 2),
        BOT_HEAD((byte) 4),
        USER_BODY((byte) 5),
        BOT_BODY((byte) 6);

        final private byte number;

        Type(final byte number) {
            this.number = number;
        }

        public byte getNumber() {
            return number;
        }
    }

    public Cell getHead() {
        return head;
    }

    public Directions getDirection() {
        return direction;
    }

    public void setDirection(final Directions direction) {
        this.direction = direction;
    }

    public int size() {
        return size;
    }

    public void removeSnakeTails(Snake.Type type) {
        for(short yPos = 0; yPos < matrix.getHeight(); yPos += 1) {
            for(short xPos = 0; xPos < matrix.getWidth(); xPos += 1) {
                if (matrix.get(yPos, xPos) == type.getNumber()) {
                    matrix.set(yPos, xPos, Matrix.EMPTY_VALUE);
                }
            }
        }
    }

    public void updateMatrixBySnake() {
        int newY = getHead().getY();
        int newX = getHead().getX();

        matrix.set((short) newY, (short) newX, getHeadType());

        for(int i = 0; i < size(); i += 1) {
            if (matrix.get((short) getY(i), (short) getX(i)) == Matrix.EMPTY_VALUE) {
                matrix.set((short) getY(i), (short) getX(i), getBodyType());
            }
        }
    }

    public Cell getCellAt(int index) {
        Cell cell = head;

        while(true) {
            if (index == 0 && cell != null) {
                return cell;
            } else if (index > 0 && cell.getLink() != null) {
                index -= 1;
                cell = cell.getLink();
            } else {
                return null;
            }
        }
    }

    public int getY(final int index) {
        return getCellAt(index).getY();
    }

    public int getX(final int index) {
        return getCellAt(index).getX();
    }

    public boolean validate() {
        ArrayList<String> cellsXY = new ArrayList<>();

        for(int i = 0; i < size(); i += 1) {
            String xy = getCellAt(i).getX() + ":" + getCellAt(i).getY();
            if (cellsXY.indexOf(xy) == -1) {
                cellsXY.add(xy);
            } else {
                return true;
            }
        }

        return true;
    }

    public void raise() {
        Cell lastCell = getCellAt(size() - 1);
        lastCell.setLink(new Cell((short) lastCell.getX(), (short) lastCell.getY()));
        this.size += 1;
    }

    public void gain(final HashMap<Matrix.Directions, Integer> snakePosition) {
        int newX = snakePosition.get(Matrix.Directions.X);
        int newY = snakePosition.get(Matrix.Directions.Y);

        if (matrix.get((short) newY, (short) newX) == 3) {
            raise();
        }

        Cell cell = head;

        int freeX = head.getX();
        int freeY = head.getY();
        int holdX, holdY;

        while(true) {
            if (cell.getLink() != null) {
                holdX = cell.getLink().getX();
                holdY = cell.getLink().getY();

                cell.getLink().setX(freeX);
                cell.getLink().setY(freeY);

                freeX = holdX;
                freeY = holdY;

                cell = cell.getLink();
            } else {
                head.setX(newX);
                head.setY(newY);
                return;
            }
        }
    }

    protected HashMap<Matrix.Directions, Integer> getNewPositionByDirection() {
        int headYPos = getHead().getY();
        int headXPos = getHead().getX();

        int newY;
        int newX;

        switch(getDirection()) {
            case LEFT:
                if (headXPos > 0) {
                    newX = headXPos - 1;
                } else {
                    newX = matrix.getWidth() - 1;
                }

                newY = headYPos;
                break;
            case RIGHT:
                if (headXPos < matrix.getWidth() - 1) {
                    newX = headXPos + 1;
                } else {
                    newX = 0;
                }

                newY = headYPos;
                break;
            case UP:
                if (headYPos > 0) {
                    newY = headYPos - 1;
                } else {
                    newY = matrix.getHeight() - 1;
                }

                newX = headXPos;
                break;
            case DOWN:
                if (headYPos < matrix.getHeight() - 1) {
                    newY = headYPos + 1;
                } else {
                    newY = 0;
                }

                newX = headXPos;
                break;
            default:
                throw new IllegalStateException("Unknown direction of snake");
        }

        HashMap<Matrix.Directions, Integer> newPosition = new HashMap<>();
        newPosition.put(Matrix.Directions.X, newX);
        newPosition.put(Matrix.Directions.Y, newY);

        return newPosition;
    }

    public void draw(final Graphics2D g) {
        final byte CELL_SIZE = matrix.getCellSize();

        for(int yPos = 0; yPos < matrix.getHeight(); yPos += 1) {
            for(int xPos = 0; xPos < matrix.getWidth(); xPos += 1) {
                switch (matrix.get((short) yPos, (short) xPos)) {
                    case 5:
                    case 6:
                        g.setPaint(Color.red);
                        g.fillRect(xPos * CELL_SIZE, yPos * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;
                    case 2:
                    case 4:
                        g.setPaint(Color.yellow);
                        g.fillRect(xPos * CELL_SIZE, yPos * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;
                }
            }
        }
    }
}
