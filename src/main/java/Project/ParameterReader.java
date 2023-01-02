package Project;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ParameterReader {

    public SimulationParameters getParameters() throws IOException, IllegalArgumentException, org.json.simple.parser.ParseException {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("parameters.json"))
        {
            JSONObject json = (JSONObject) jsonParser.parse(reader);
            int width = ((Long)json.get("width")).intValue();
            int height = ((Long)json.get("height")).intValue();
            int energyLoss = ((Long)json.get("energyLoss")).intValue();
            int startAnimals = ((Long)json.get("startAnimals")).intValue();
            int startGrass = ((Long)json.get("startGrass")).intValue();
            int dailyGrass = ((Long)json.get("dailyGrass")).intValue();
            int startEnergy = ((Long)json.get("startEnergy")).intValue();
            int plantEnergy = ((Long)json.get("plantEnergy")).intValue();
            int copulationEnergy = ((Long)json.get("copulationEnergy")).intValue();
            int copulationEnergyLoss = ((Long)json.get("copulationEnergyLoss")).intValue();
            int minMutation = ((Long)json.get("minMutation")).intValue();
            int maxMutation = ((Long)json.get("maxMutation")).intValue();
            boolean mutationRandomization = ((Boolean)json.get("mutationRandomization"));
            boolean behaviourRandomization = ((Boolean)json.get("behaviourRandomization"));
            int genesLength = ((Long)json.get("genesLength")).intValue();
            boolean isGlobe = ((Boolean)json.get("isGlobe"));
            int millisecondsPerEra = ((Long)json.get("millisecondsPerEra")).intValue();

//            if(millisecondsPerEra <= 0 || width <= 0 || height <= 0 || startAnimals <= 0 || startEnergy <= 0 ||
//                    moveEnergy <= 0 || plantEnergy <= 0 || jungleRatio <= 0 || numberOfWindows <= 0)
//                throw new IllegalArgumentException("All arguments should be positive.");

            return new SimulationParameters(width, height, energyLoss, startAnimals, startGrass, dailyGrass, startEnergy,
                    plantEnergy, copulationEnergy, copulationEnergyLoss, minMutation, maxMutation,
                    mutationRandomization, behaviourRandomization, genesLength, isGlobe, millisecondsPerEra);

        }
    }
}
