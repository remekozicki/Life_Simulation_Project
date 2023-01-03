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

//    text variables

    private final Text eraValueText = new Text("-");
    private final Text animalsValueText = new Text("-");
    private final Text grassesValueText = new Text("-");
    private final Text averageEnergyValueText = new Text("-");
    private final Text selectedGenomeValueText = new Text("-");
    private final Text selectedPartOfGenotypeValueText = new Text("-");
    private final Text selectedEnergyValueText = new Text("-");
    private final Text selectedChildrenValueText = new Text("-");
    private final Text selectedAgeValueText = new Text("-");


    public Visualizer(Simulation simulation, WorldMap map, int equatorBottomBorder, int equatorUpperBorder, SimulationParameters simulationParameters) {
        this.simulation = simulation;
        this.map = map;
        this.simulationParameters = simulationParameters;
        this.mapWidth = simulationParameters.width;
        this.mapHeight = simulationParameters.height;
        this.equatorBottomBorder = equatorBottomBorder;
        this.equatorUpperBorder = equatorUpperBorder;
        this.squareSize = 600 / simulationParameters.height;

        initialize();

    }

    private void initialize() {
        Stage stage = new Stage();
        stage.setTitle("Simulation");

//        canvas
//        ------------------------------------------

        Canvas canvas = new Canvas();
        canvas.setWidth(mapWidth * squareSize);
        canvas.setHeight(mapHeight * squareSize);
        map2D = canvas.getGraphicsContext2D();

//        ------------------------------------------
//        pause button
//        ------------------------------------------

        Button pauseButton = createPauseButton();

        HBox pauseBtn = new HBox(pauseButton);
        pauseButton.setPrefWidth(180);
        pauseBtn.setMinHeight(40);
        pauseBtn.setAlignment(Pos.CENTER);
        pauseBtn.setSpacing(25);
        pauseBtn.setPadding(new Insets(5, 25, 5, 25));

//        ------------------------------------------
//        statistics
//        ------------------------------------------

        VBox generalInfoBox = new VBox();

        Text eraTitleText = new Text("Current Era:");
        Text animalsTitleText = new Text("Animals:");
        Text grassesTitleText = new Text("Grasses:");
        Text averageEnergyTitleText = new Text("Average energy:");

        Text generalInfoText = new Text(10, 20, "Statistics:\n");
        generalInfoText.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 16));

        generalInfoBox.getChildren().addAll(generalInfoText, eraTitleText, eraValueText, animalsTitleText,
                animalsValueText, grassesTitleText, grassesValueText, averageEnergyTitleText, averageEnergyValueText);
        generalInfoBox.setPadding(new Insets(5, 10, 10, 5));

//        ------------------------------------------
//        selected animal statistics
//        ------------------------------------------

        VBox selectedInfoBox = new VBox();

        Text selectedGenomeTitleText = new Text("Genotype:");
        Text selectedPartOfGenotypeTitleText = new Text("Part:");
        Text selectedEnergyTitleText = new Text("Energy:");
        Text selectedChildrenTitleText = new Text("Children:");
        Text selectedAgeTitleText = new Text("Age:");

        Text selectedInfoText = new Text(10, 20, "Selected animal information:\n");
        selectedInfoText.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 16));

        selectedInfoBox.getChildren().addAll(selectedInfoText, selectedGenomeTitleText, selectedGenomeValueText,
                selectedPartOfGenotypeTitleText, selectedPartOfGenotypeValueText, selectedEnergyTitleText,
                selectedEnergyValueText, selectedChildrenTitleText, selectedChildrenValueText, selectedAgeTitleText,
                selectedAgeValueText);

        selectedInfoBox.setPadding(new Insets(5, 10, 10, 5));

//        ------------------------------------------

        VBox statsSide = new VBox(pauseBtn, generalInfoBox, selectedInfoBox);

        EventHandler<MouseEvent> animalSelectHandler = e -> {
            Vector2d location = new Vector2d((int) (e.getSceneX() / squareSize), (int) (e.getSceneY() / squareSize));
            selectAnimalAt(location);
        };

        canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, animalSelectHandler);

        HBox container = new HBox(canvas, statsSide);
        Scene scene = new Scene(container);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
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
        getStatistics();
    }

    private void drawMap() {
//        background

        map2D.setFill(Color.valueOf("#00aa00"));
        map2D.fillRect(0, 0, mapWidth * squareSize, mapHeight * squareSize);

        map2D.setFill(Color.valueOf("#004400"));
        map2D.fillRect(0, (equatorBottomBorder) * squareSize, mapWidth * squareSize, (equatorUpperBorder - equatorBottomBorder) * squareSize);

        map2D.setFill(Color.valueOf("#00ff00"));
        for (Grass grass: simulation.getGrasses()) {
            Vector2d position = grass.getPosition();
            map2D.fillRoundRect(position.x * squareSize, position.y * squareSize, squareSize, squareSize, squareSize / 2f, squareSize / 2f);
        }

        for (Animal animal: simulation.getAnimals()) {
            map2D.setFill(energyToColor(animal.getEnergy()));
            Vector2d position = animal.getPosition();
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

    private void getStatistics() {

        eraValueText.setText(String.valueOf(simulation.getCurrentEra()));
        animalsValueText.setText(String.valueOf(simulation.getAnimals().size()));
        grassesValueText.setText(String.valueOf(simulation.getGrasses().size()));

        if (simulation.getAnimals().size() > 0) {
            averageEnergyValueText.setText(String.valueOf((int)(simulation.getTotalEnergy() / simulation.getAnimals().size())));
        }

    }

    private void selectAnimalAt(Vector2d position){
        simulation.setSelectedAnimal(map.animalAt(position));
        drawMap();
        displaySelectedAnimal();
    }

    public void displaySelectedAnimal(){
        if (simulation.getSelectedAnimal() != null) {
            if (simulation.getAnimals().contains(simulation.getSelectedAnimal())) {
                map2D.setStroke(Color.valueOf("#ff0000"));
                map2D.setLineWidth(squareSize / 3f);
                Vector2d position = simulation.getSelectedAnimal().getPosition();
                map2D.strokeOval(position.x * squareSize, position.y * squareSize, squareSize, squareSize);
            }

            String genomeText = simulation.getSelectedAnimal().getGenes().toString();

            selectedGenomeValueText.setText(genomeText);
            selectedPartOfGenotypeValueText.setText(String.valueOf(simulation.getSelectedAnimal().getCurrentGene()));
            selectedEnergyValueText.setText(String.valueOf(simulation.getSelectedAnimal().getEnergy()));
            selectedChildrenValueText.setText(String.valueOf(simulation.getSelectedAnimal().getChildren().size()));
            selectedAgeValueText.setText(String.valueOf(simulation.getSelectedAnimal().getAge()));
        }
    }


}
