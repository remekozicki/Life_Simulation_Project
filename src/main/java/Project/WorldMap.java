package Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

public class WorldMap implements IPositionChangeObserver {

    private boolean isGlobe;
    private int width;
    private int height;
//    private Vector2d upperRight;
//    private final Vector2d lowerLeft = new Vector2d(0, 0);
    private final HashMap<Vector2d, PriorityQueue<Animal>> animalHashMap = new HashMap<>();
    private final HashMap<Vector2d, Grass> grassHashMap = new HashMap<>();

    public WorldMap(int width, int height, boolean isGlobe){
        if (width > 0 || height > 0){
            this.width = width;
            this.height = height;
//            this.upperRight = new Vector2d( width -1, height -1);
            this.isGlobe = isGlobe;
        }
    }


//    public boolean canMoveTo(Vector2d position) {
//        return ((position.precedes(this.upperRight) && position.follows(this.lowerLeft)) && !isOccupied(position));
//    }

    public boolean isOccupied(Vector2d position) {
        return animalHashMap.containsKey(position) || grassHashMap.containsKey(position);
    }

    public static int getRandomValue(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public Vector2d randomUnoccupiedPosition(int maxAttempts, int bottomBorder, int upperBorder) {

//        used for generating starting grasses/animals (so that we are sure to place the exact amount which user gave us
//        in parameters)

        int x, y;
        int attempts = 0;
        Vector2d pos;
        boolean found = false;

        do {
            x = getRandomValue(0, this.width);
            y = getRandomValue(bottomBorder, upperBorder + 1);
            pos = new Vector2d(x, y);
            attempts++;

        } while (isOccupied(pos) && attempts < maxAttempts);

        if (attempts == maxAttempts) {

//            searching manually for any free position
            for (int i = bottomBorder; i <= upperBorder; i++) {
                for (int j = 0; j < this.width; j++) {
                    pos = new Vector2d(j, i);
                    if (!isOccupied(pos)) {
                        break;
                    }
                }

                if (!isOccupied(pos)) {
                    found = true;
                    break;
                }
            }

        } else {
            return pos;
        }

        if (found) {
            return pos;

        } else {
//            unable to find free position :(
            return null;
        }

    }


    public Object objectAt(Vector2d position) {
        return this.animalHashMap.get(position);
    }

    public void place(IMapElement element){

        if (element instanceof Animal) {
            Animal animal = (Animal) element;

            if (!animalHashMap.containsKey(animal.getPosition())) {
                animalHashMap.put(animal.getPosition(), new PriorityQueue<Animal>());
            }

            animalHashMap.get(animal.getPosition()).add(animal);

        } else {
            Grass grass = (Grass) element;
            if (!isOccupied(grass.getPosition())) {
                grassHashMap.put(grass.getPosition(), grass);
            }
        }
    }

    public void positionChanged(IMapElement element, Vector2d oldPosition, Vector2d newPosition) {

        if (element instanceof Animal) {
            Animal animal = (Animal) element;

            if (animalHashMap.get(oldPosition).size() == 1) {
//                removing whole priority queue
                animalHashMap.remove(oldPosition);
            } else {
//                removing single animal
                animalHashMap.get(oldPosition).remove(animal);
            }

        } else {
            grassHashMap.remove(oldPosition);
        }

        if (newPosition != null) {
            place(element);
        }
    }

    public ArrayList<Animal> getGrassEaters() {

        ArrayList<Animal> grassEaters = new ArrayList<>();

        for (Vector2d position: animalHashMap.keySet()) {
            if (grassHashMap.containsKey(position)) {
                grassEaters.add(animalHashMap.get(position).peek());
            }
        }

        return grassEaters;
    }

    public ArrayList<PriorityQueue<Animal>> getPotentialPartners(){

        ArrayList<PriorityQueue<Animal>> potentialPartners = new ArrayList<>();

        for( PriorityQueue<Animal> animals: animalHashMap.values()){

            if (animals.size() >= 2){
                potentialPartners.add(animals);
            }
        }
        return potentialPartners;
    }


    public Vector2d outOfBorders(Animal animal, float energyLoss, Vector2d newPosition) {

        Vector2d position = newPosition;
        if (this.isGlobe) {
            if (position.x < 0) {
                position = new Vector2d(this.width - 1, position.y);
            }
            else if (position.x >= this.width) {
                position = new Vector2d(0, position.y);
            }

        } else {
            if (position.x < 0 || position.y < 0 || position.x >= this.width || position.y >= this.height) {
                animal.changeEnergy(-energyLoss);
                position = this.randomPosition(position);
            }
        }

        return position;
    }

    public Vector2d randomPosition(Vector2d position) {

        int x, y;

        do {
            x = (int) (Math.random() * this.width);
            y = (int) (Math.random() * this.height);
        } while (position.x == x && position.y == y);

        return new Vector2d(x, y);
    }




}
