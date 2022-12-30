package Project;

import java.util.ArrayList;
import java.util.Comparator;

public class Animal implements IMapElement, Comparable<Animal> {

    private Vector2d previousPosition;
    private Vector2d position;
    private MapDirection direction;
    private float energy;
    private int age = 0;

    private final WorldMap map;

    private Genes genes;
    private int currentGene;

    private ArrayList<Animal> children = new ArrayList<>();
    private ArrayList<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(WorldMap map){
        this.map = map;
    }

    public Animal(WorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.position = initialPosition;
    }

    public Animal(WorldMap map, Vector2d initialPosition, float startEnergy, Genes genes) {
        this.map = map;
        this.position = initialPosition;
        this.energy = startEnergy;
        this.genes = genes;

//        this sets random direction on spawn
        this.direction = MapDirection.values()[(int) (Math.random() * MapDirection.values().length)];
//        this sets random gene on spawn
        this.currentGene = (int) (Math.random() * genes.getSize());
    }

    public void move(float energyLoss) {
        //    energyLoss is a variable that tells us how much the animal's energy will be reduced after
        //    it comes out of the map

        int gene = this.genes.getGenes()[currentGene];

        MapDirection newDirection = this.direction.rotateBy(gene);
//        boje sie
        Vector2d newPosition = this.position.add(newDirection.toUnitVector());
        newPosition = this.map.outOfBorders(this, energyLoss);

//        rotating when the animal reaches a pole
        if (newPosition.equals(this.position)) {
            newDirection = newDirection.rotateBy(4);
        }

        for (IPositionChangeObserver observer: observers) {
            observer.positionChanged(this, this.position, newPosition);
        }

        this.position = newPosition;
        this.direction = newDirection;
    }

    public boolean isAt(Vector2d position) {
        return position.equals(this.position);
    }

    public void changeEnergy(float value) {
//        value can be positive or negative
        this.energy += value;

        if (this.energy <= 0) {
            this.die();
        }
    }

    private void die() {

        for (IPositionChangeObserver observer: observers) {
            observer.positionChanged(this, this.position, null);
        }

    }

    public void makeOlder() {
        this.age++;
    }

    public void nextGene(boolean isRandom) {
//        isRandom variable tells us about simulation variant, if it is true,
//        then there is 20% chance for next gene to be random
        if (isRandom) {
            int random = (int) (Math.random() * 10);

            if (random < 8) {
                this.currentGene++;
            } else {
                this.currentGene = (int) (Math.random() * genes.getSize());
            }
        } else {
            this.currentGene++;
        }

        this.currentGene = this.currentGene % this.genes.getSize();
    }

    public void addChild(Animal child) {
        this.children.add(child);
    }

    public void addObserver(IPositionChangeObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        this.observers.remove(observer);
    }


//    getters
    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    public float getEnergy() {
        return this.energy;
    }

    public Vector2d getPreviousPosition() {
        return this.previousPosition;
    }

    public MapDirection getDirection() {
        return this.direction;
    }

    public int getCurrentGene() {
        return this.currentGene;
    }

    public WorldMap getMap() {
        return map;
    }

    public Genes getGenes() {
        return this.genes;
    }

    public int getAge() {
        return this.age;
    }

    public ArrayList<Animal> getChildren() {
        return this.children;
    }

    @Override
    public String toString() {

        return switch (this.direction) {
            case NORTH -> "N";
            case NORTHEAST -> "NE";
            case EAST -> "E";
            case SOUTHEAST -> "SE";
            case SOUTH -> "S";
            case SOUTHWEST -> "SW";
            case WEST -> "W";
            case NORTHWEST -> "NW";
        };
    }

//    we can compare animals, useful in for example priority queues when we have to decide which animal will eat
    public int compareTo(Animal a) {

//        sorting by energy
        if (this.energy != a.getEnergy()) {
            return (int) (this.energy - a.getEnergy());
        }

//        sorting by age
        if (this.age != a.getAge()) {
            return this.age - a.getAge();
        }

//        sorting by amount of children
        if (this.children.size() != a.getChildren().size()) {
            return this.children.size() - a.getChildren().size();
        }

//        random
        return 1;
    }

}
