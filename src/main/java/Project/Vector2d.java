package Project;

import java.util.Objects;

public class Vector2d {

    final public int x, y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return ("(" + this.x + "," + this.y + ")");
    }

    boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    Vector2d upperRight(Vector2d other) {

        int maxX, maxY;

        maxX = Math.max(this.x, other.x);
        maxY = Math.max(this.y, other.y);

        return new Vector2d(maxX, maxY);
    }

    Vector2d lowerLeft(Vector2d other) {

        int minX, minY;

        minX = Math.min(this.x, other.x);
        minY = Math.min(this.y, other.y);

        return new Vector2d(minX, minY);
    }

    Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    Vector2d substract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;

        return x == that.x && y == that.y;
    }

    Vector2d opposite() {
        return new Vector2d(- this.x, - this.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
