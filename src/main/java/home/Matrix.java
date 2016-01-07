package main.java.home;

public class Matrix {
    final private byte CELL_SIZE = 15;
    final private short width;
    final private short height;
    final private byte[][] matrix;
    final public static byte EMPTY_VALUE = 0;

    public static enum Directions {
        X, Y;
    }

    public Matrix(final Matrix.Builder config) {
        this.width = (short) (config.width / CELL_SIZE);
        this.height = (short) (config.height / CELL_SIZE);

        matrix = new byte[height][width];
    }

    public short getHeight() {
        return height;
    }

    public short getWidth() {
        return width;
    }

    public byte getCellSize() {
        return CELL_SIZE;
    }

    public void set(final int height, final int width, final byte value) {
        matrix[height][width] = value;
    }

    public byte get(final short height, final short width) {
        return matrix[height][width];
    }

    public static class Builder {
        private short width;
        private short height;

        public Matrix.Builder setWidth(final short width) {
            this.width = width;
            return this;
        }

        public Matrix.Builder setHeight(final short height) {
            this.height = height;
            return this;
        }

        public Matrix build() {
            return new Matrix(this);
        }
    }
}
