package Project;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation {

    private final boolean isGlobe;
    private int currentEra = 0;
    private boolean simulationPaused = false;
    private final int equatorUpperBorder;
    private final int equatorBottomBorder;
    private final Visualizer visualizer;
    private final WorldMap map;
    private final int mapWidth;
    private final int mapHeight;
    private final int plantEnergy;
    private final int startEnergy;
    private final int energyLoss;
    private int deadAnimalsCounter;
    private final int copulationMinEnergy;
    private final AnimalCopulation animalCopulation;
    private final boolean mutationRandomization;
    private final float copulationEnergyLoss;
    private final int dailyGrass;
    private final int startGrass;
    private final boolean behaviourRandomization;
    private final ArrayList<Animal> animals = new ArrayList<>();
    private final ArrayList<Grass> grasses = new ArrayList<>();
    private Animal selectedAnimal = null;

    public Simulation(SimulationParameters parameters) {
        this.mapWidth = parameters.width;
        this.mapHeight = parameters.height;
        this.plantEnergy = parameters.plantEnergy;
        this.startEnergy = parameters.startEnergy;
        this.energyLoss = parameters.energyLoss;
        this.isGlobe = parameters.isGlobe;
        this.copulationMinEnergy = parameters.copulationMinEnergy;
        this.copulationEnergyLoss = parameters.copulationEnergyLoss;
        this.mutationRandomization = parameters.mutationRandomization;
        this.dailyGrass = parameters.dailyGrass;
        this.startGrass = parameters.startGrass;
        this.behaviourRandomization = parameters.behaviourRandomization;

        this.map = new WorldMap(mapWidth, mapHeight, isGlobe);

        this.animalCopulation = new AnimalCopulation(this.map, this.mutationRandomization, this.energyLoss);

        int tenPercentOfHeight = (int) (this.mapHeight / 10);
//        equator will be +/-20% of whole area
        this.equatorUpperBorder = (int) (mapHeight / 2 + tenPercentOfHeight);
        this.equatorBottomBorder = (int) (mapHeight / 2 - tenPercentOfHeight);

        this.visualizer = new Visualizer(this, this.map, this.equatorBottomBorder, this.equatorUpperBorder, parameters);

        generateAnimals(parameters);
        generateGrass(this.startGrass);

    }

    void run(javafx.event.ActionEvent actionEvent) {

        if (!simulationPaused) {
            this.currentEra++;
            update();
            visualizer.display();
        }

    }

    private void update() {

        changeEnergyDueToMove();
        moveAnimals();
        eatGrass();
        copulate();
        generateGrass(this.dailyGrass);

    }

//    animals interactions

    private void generateAnimals(SimulationParameters parameters){

        for (int i = 0; i < parameters.startAnimals; i++){

            Genes genes = new Genes(parameters.genesLength);
            Vector2d position = this.map.randomUnoccupiedPosition(100, 0, this.mapHeight - 1);
            Animal animal = new Animal(map, position, startEnergy, genes);
            this.map.place(animal);
            this.animals.add(animal);
            animal.addObserver(this.map);

        }
    }

    private void moveAnimals() {
        for (Animal animal: this.animals) {
            animal.move(this.energyLoss);
            animal.nextGene(this.behaviourRandomization);
        }
    }

    private void changeEnergyDueToMove() {

        for (Animal animal: new ArrayList<>(animals)) {
            animal.makeOlder();

            animal.changeEnergy(-this.energyLoss);
            if (animal.getEnergy() <= 0) {
                this.animals.remove(animal);
                this.deadAnimalsCounter++;
            }

        }
    }

    private void eatGrass() {

        ArrayList<Animal> grassEaters = this.map.getGrassEaters();

        if (grassEaters.isEmpty()) {
            return;
        }

        for (Animal animal: grassEaters) {
            Grass eatenGrass = new Grass(animal.getPosition(), this.plantEnergy);
//            will have to check if the equals method works (does not seem to work)
            grasses.remove(eatenGrass);
            animal.changeEnergy(plantEnergy);
            this.map.positionChanged(eatenGrass, animal.getPosition(), null);
        }

    }

    private void copulate() {

        ArrayList<PriorityQueue<Animal>> potentialPartners = this.map.getPotentialPartners();

        for (PriorityQueue<Animal> candidates: potentialPartners) {
//            only two candidates copulate, even if there is more potential candidates

            Animal firstParent = candidates.poll();
            Animal secondParent = candidates.poll();

//            idk if this is necessary but let's assume that it is
            candidates.add(firstParent);
            candidates.add(secondParent);

//            we are safe about nullpointerexception because in worldmap class we check if
//            the priority queue has more than two animals
            if (secondParent.getEnergy() < this.copulationMinEnergy) {
//                if the second parent has energy to copulate then we can be
//                sure that the first one has enough energy as well
                continue;
            }

            Vector2d childPosition = firstParent.getPosition();

            Animal child = this.animalCopulation.reproduce(firstParent, secondParent);
            child.addObserver(this.map);
            animals.add(child);
            this.map.place(child);
        }
    }

//    grass interactions

    public static int getRandomValue(int min, int max) {
//        minimum is inclusive and maximum is exclusive
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    private void generateGrass(int amount) {

        int junglePlants, otherPlants;

        junglePlants = (int) (amount * 0.8);

        if (amount == 0) {
            otherPlants = 0;
        } else {
//            so that there is always one plant outside the equator
            otherPlants = Math.max((int) (amount * 0.2), 1);
        }

        for (int i = 0; i < junglePlants; i++) {

            Vector2d position = this.map.randomUnoccupiedPosition(100, equatorBottomBorder, equatorUpperBorder - 1);
            if (position != null) {
                Grass grass = new Grass(position, this.plantEnergy);
                map.place(grass);
                this.grasses.add(grass);
            }
        }

        for (int i = 0; i < otherPlants; i++) {
//            to place the grass outside the equator we can pick randomly whether the grass needs to be placed above
//            the equator or below it
            Vector2d position;
            if (getRandomValue(0, 2) == 0) {
                position = this.map.randomUnoccupiedPosition(100, 0, this.equatorBottomBorder - 1);

            } else {
                position = this.map.randomUnoccupiedPosition(100, this.equatorUpperBorder + 1, this.mapHeight - 1);
            }

            if (position != null) {
                Grass grass = new Grass(position, this.plantEnergy);
                map.place(grass);
                this.grasses.add(grass);
            }

        }
    }

    public boolean isPaused() {
        return this.simulationPaused;
    }

    public void pause(boolean change) {
        simulationPaused = change;
    }

//    public void run(javafx.event.ActionEvent actionEvent) {
//    }

    public ArrayList<Grass> getGrasses() {
        return this.grasses;
    }

    public ArrayList<Animal> getAnimals() {
        return this.animals;
    }

    public int getCurrentEra() {
        return this.currentEra;
    }

    public int getTotalEnergy() {
        int sum = 0;

        for (Animal animal: this.animals) {
            sum += animal.getEnergy();
        }

        return sum;
    }

    public Animal getSelectedAnimal(){
        return selectedAnimal;
    }

    public void setSelectedAnimal(Animal selectedAnimal){
        this.selectedAnimal = selectedAnimal;
    }
}
