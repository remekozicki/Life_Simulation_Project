package Project;

//import ErrorLogger;
import javafx.application.Application;
import javafx.stage.Stage;

public class SimulationLauncher extends Application {

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage stage) throws Exception {

        try {
            ParameterReader jsonReader = new ParameterReader();
            SimulationParameters simulationParameters = jsonReader.getParameters();

            SimulationParameters simulationParameters2 = new SimulationParameters(50, 30, 10,
                    30, 60, 5, 400, 60, 50, 100,
                    3, 3, true, true, 10, true, 100);

            SimulationEngine simulationEngine = new SimulationEngine();
            simulationEngine.start(simulationParameters);
        }
        catch(Exception e) {
            System.out.println(e);
        }

    }



}
