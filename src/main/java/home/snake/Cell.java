package main.java.home.snake;

public class Cell {
    private int x;
    private int y;
    private Cell link;

    public Cell(final short x, final short y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setY(final int y) {
       this.y = y;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public Cell getLink() {
        return link;
    }

    public void setLink(final Cell link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + this.x + "," +
                "y=" + this.y + "," +
                "link=" + this.link +
                "}";
    }
}
