package Project;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Visualizer {

    private final int mapWidth;
    private final int mapHeight;
    private final Simulation simulation;
    private final WorldMap map;
    private final SimulationParameters simulationParameters;
    private final int equatorBottomBorder;
    private final int equatorUpperBorder;
    private final int squareSize;
    private GraphicsContext map2D;

//    private final int equatorBottomBorder;
//    private final int equatorUpperBorder;


    public Visualizer(Simulation simulation, WorldMap map, int equatorBottomBorder, int equatorUpperBorder, SimulationParameters simulationParameters) {
        this.simulation = simulation;
        this.map = map;
        this.simulationParameters = simulationParameters;
        this.mapWidth = simulationParameters.width;
        this.mapHeight = simulationParameters.height;
        this.equatorBottomBorder = equatorBottomBorder;
        this.equatorUpperBorder = equatorUpperBorder;
        this.squareSize = 20;

        initialize();

    }

    private void initialize() {
        Stage stage = new Stage();
        stage.setTitle("Simulation");

        Canvas canvas = new Canvas();
        canvas.setWidth(mapWidth * squareSize);
        canvas.setHeight(mapHeight * squareSize);
        map2D = canvas.getGraphicsContext2D();

        HBox container = new HBox(canvas);
        Scene scene = new Scene(container);
        stage.setScene(scene);
        stage.show();

//        Button pauseButton = createPauseButton();
//
//        TextField statisticsTextField = new TextField();
//        Button statisticsButton = createStatisticsButton(statisticsTextField);

//        TextField eraTextField = new TextField();
//        Button eraJumpButton = createEraJumpButton(eraTextField);

//        Button displayDominatingButton = createDisplayDominatingButton();

//        EventHandler<MouseEvent> animalSelectHandler = e -> {
//            Vector2d location = new Vector2d((int) (e.getSceneX() / squareSize), (int) (e.getSceneY() / squareSize));
//            selectAnimalAt(location);
//        };
    }

    private Button createPauseButton(){

        Button pauseButton = new Button("Pause");

        pauseButton.setOnAction(actionEvent -> {
            if (simulation.isPaused()) {
                simulation.pause(false);
                pauseButton.setText("Pause");
            } else {
                simulation.pause(true);
                pauseButton.setText("Resume");
            }
        });

        return pauseButton;
    }

    public void display() {
        drawMap();
    }

    private void drawMap() {
//        background

        map2D.setFill(Color.valueOf("#00aa00"));
        map2D.fillRect(0, 0, mapWidth * squareSize, mapHeight * squareSize);

        map2D.setFill(Color.valueOf("#004400"));
        map2D.fillRect(0, equatorBottomBorder * squareSize, mapWidth * squareSize, (equatorUpperBorder - equatorBottomBorder) * squareSize);

        map2D.setFill(Color.valueOf("#00ff00"));
        for (Grass grass: simulation.getGrasses()) {
            Vector2d position = grass.getPosition();
            System.out.println(position);
            map2D.fillRoundRect(position.x * squareSize, position.y * squareSize, squareSize, squareSize, squareSize / 2f, squareSize / 2f);
        }

        for (Animal animal: simulation.getAnimals()) {
            map2D.setFill(energyToColor(animal.getEnergy()));
            Vector2d position = animal.getPosition();
            System.out.println(position);
            map2D.fillOval(position.x * squareSize, position.y * squareSize, squareSize, squareSize);
        }
    }

    private Color energyToColor(float energy) {

        float energyRatio = energy / (simulationParameters.startEnergy);
        if (energyRatio > 1)
            return Color.color(1, 1, 1);
        if (energyRatio >= 0.5)
            return Color.color(0, 183 / 255f * energyRatio, energyRatio);

        return Color.color(energyRatio, 0, energyRatio);
    }


}
