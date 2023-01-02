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
//            JSONParametersReader jsonReader = new JSONParametersReader();
//            SimulationParameters simulationParameters = jsonReader.getParameters();

            SimulationParameters simulationParameters = new SimulationParameters(20, 20, 100,
                    10, 25, 5, 400, 60, 50, 100,
                    3, 3, true, true, 10, true, 1000);

            SimulationEngine simulationEngine = new SimulationEngine();
            simulationEngine.start(simulationParameters);
        }
        catch(Exception e) {
            System.out.println(e);
        }

    }



}
