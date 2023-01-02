package Project;

//import ErrorLogger;
import javafx.application.Application;
import javafx.stage.Stage;

public class SimulationLauncher extends Application {

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage stage) throws Exception {

        try{
//            JSONParametersReader jsonReader = new JSONParametersReader();
//            SimulationParameters simulationParameters = jsonReader.getParameters();

            SimulationParameters simulationParameters = new SimulationParameters(20, 20, 100,
                    10, 10, 5, 400, 60, 50, 100,
                    3, 3, true, true, 10, true, 100);

//            for (int i = 0; i < simulationParameters.numberOfWindows; i++){
//                SimulationEngine simulationEngine = new SimulationEngine();
//                simulationEngine.start(simulationParameters);
//            }

            SimulationEngine simulationEngine = new SimulationEngine();
            simulationEngine.start(simulationParameters);
        }
        catch(Exception e){
//            Logging.ErrorLogger errorLogger = new ErrorLogger();
//            errorLogger.log(e.getMessage());
        }

    }



}
