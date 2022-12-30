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

//    private final int squareSize;
    private final int mapWidth;
    private final int mapHeight;
    private final Simulation simulation;
    private final WorldMap map;
    private final SimulationParameters simulationParameters;
    private final Vector2d equatorTopRight;
    private final Vector2d equatorBottomleft;

//    private final int equatorBottomBorder;
//    private final int equatorUpperBorder;


    public Visualizer(Simulation simulation, WorldMap map, int equatorBottomBorder, int equatorUpperBorder, SimulationParameters simulationParameters) {
        this.simulation = simulation;
        this.map = map;
        this.simulationParameters = simulationParameters;
        this.mapWidth = simulationParameters.width;
        this.mapHeight = simulationParameters.height;
        this.equatorBottomleft = new Vector2d(0,equatorBottomBorder);
        this.equatorTopRight = new Vector2d(mapWidth - 1, equatorUpperBorder);
//        this.squareSize = (int) (simulationParameters.windowHeight / mapHeight);

//        initialize();

    }

//    private void initialize(){
//        Stage stage = new Stage();
//        stage.setTitle("Simulation");
//
//        Canvas canvas = new Canvas();
//        canvas.setWidth(mapWidth * squareSize);
//        canvas.setHeight(mapHeight * squareSize);
//        map2D = canvas.getGraphicsContext2D();
//
//        Button pauseButton = createPauseButton();
//
//        TextField statisticsTextField = new TextField();
//        Button statisticsButton = createStatisticsButton(statisticsTextField);
//
//        TextField eraTextField = new TextField();
//        Button eraJumpButton = createEraJumpButton(eraTextField);
//
//        Button displayDominatingButton = createDisplayDominatingButton();
//
//        EventHandler<MouseEvent> animalSelectHandler = e -> {
//            Vector2d location = new Vector2d((int)(e.getSceneX() / squareSize), (int)(e.getSceneY() / squareSize));
//            selectAnimalAt(location);
//    };


}
