package Project;

public class SimulationParameters {

    public final int width;
    public final int height;
    public final int energyLoss;
    public final int startAnimals;
    public final int startGrass;
    public final int dailyGrass;
    public final int startEnergy;
    public final int plantEnergy;
    public final int copulationMinEnergy;
    public final int copulationEnergyLoss;
    public final int minMutation;
    public final int maxMutation;
    public final boolean mutationRandomization;
    public final boolean behaviourRandomization;
    public final int genesLength;
    public final boolean isGlobe;



    public SimulationParameters(int width, int height, int energyLoss, int startAnimals, int startGrass, int dailyGrass,
                                int startEnergy, int plantEnergy, int copulationEnergy, int copulationEnergyLoss,
                                int minMutation, int maxMutation, boolean mutationRandomization,
                                boolean behaviourRandomization, int genesLength, boolean isGlobe) {

        this.width = width;
        this.height = height;
        this.energyLoss = energyLoss;
        this.startAnimals = startAnimals;
        this.startGrass = startGrass;
        this.dailyGrass = dailyGrass;
        this.startEnergy = startEnergy;
        this.plantEnergy = plantEnergy;
        this.copulationMinEnergy = copulationEnergy;
        this.copulationEnergyLoss = copulationEnergyLoss;
        this.minMutation = minMutation;
        this.maxMutation = maxMutation;
        this.mutationRandomization = mutationRandomization;
        this.behaviourRandomization = behaviourRandomization;
        this.genesLength = genesLength;
        this.isGlobe = isGlobe;
    }
}
