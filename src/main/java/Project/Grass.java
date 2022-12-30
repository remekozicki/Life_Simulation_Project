package Project;

import java.util.Objects;

public class Grass implements IMapElement {
    private Vector2d position;
    public final float nutrition;

    public Grass(Vector2d position, float nutrition){
        this.position = position;
        this.nutrition = nutrition;
    }
    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public int hashCode(){
        return Objects.hash(position);
    }

//    used for deleting from arrays using remove()
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Grass)) return false;
        Grass grass = (Grass) other;
        return Objects.equals(position, grass.position);
    }

    public String toString(){
        return "*";
    }
}
