package Project;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.util.Duration;

public class SimulationEngine {

    private Timeline loop(Simulation simulation, int milliseconds) {
        final Duration duration = Duration.millis(milliseconds);

        final KeyFrame frame = new KeyFrame(duration, simulation::run);
        Timeline timeline = new Timeline(frame);
        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;
    }

    public void start(SimulationParameters simulationParameters) throws Exception {

        Simulation simulation = new Simulation(simulationParameters);
        Timeline gameLoop = loop(simulation, simulationParameters.millisecondsPerEra);
        gameLoop.playFromStart();
    }

}
