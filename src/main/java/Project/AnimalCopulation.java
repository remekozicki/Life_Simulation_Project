package Project;

public class AnimalCopulation {

    private final WorldMap map;
    private final boolean isRandom;
    private final float energyLoss;

    public AnimalCopulation(WorldMap map, boolean isRandom, float energyLoss) {
        this.map = map;
        //        simulation variants
        this.isRandom = isRandom;
        this.energyLoss = energyLoss;
    }

    public Animal reproduce(Animal a1, Animal a2) {

        Vector2d childPosition = a1.getPosition();
        int genesSize = a1.getGenes().getSize();

        Animal stronger, weaker;

        if (a1.getEnergy() > a2.getEnergy()) {
            stronger = a1;
            weaker = a2;

        } else {
            stronger = a2;
            weaker = a1;
        }

        int placeToDiv = (int) ((stronger.getEnergy() / (stronger.getEnergy() + weaker.getEnergy())) * genesSize);

        Genes childGenes;

//        selecting which side of stronger parent's genes the child will inherit
        if ((int) (Math.random() * 2) == 0) {
            childGenes = new Genes(stronger.getGenes(), weaker.getGenes(), placeToDiv, isRandom);

        } else {
            placeToDiv = genesSize - placeToDiv;
            childGenes = new Genes(weaker.getGenes(), stronger.getGenes(), placeToDiv, isRandom);
        }


//        reducing parents' energy
        a1.changeEnergy(-energyLoss);
        a2.changeEnergy(-energyLoss);

        Animal child = new Animal(this.map, childPosition, 2 * energyLoss, childGenes);
        a1.addChild(child);
        a2.addChild(child);

        return child;
    }

}
