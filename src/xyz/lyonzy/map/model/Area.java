package xyz.lyonzy.map.model;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by brend on 23/04/2016.
 */
public class Area extends FlowPane{
    private Pane area, numberCont;
    private int buildingNo;
    private Circle circle;
    private Text number;
    private int x, y, height, width;

    public Area() {
        setup();
        Consts.setNoOfBuildings(Consts.noOfBuildings+1);
        buildingNo = Consts.noOfBuildings;
    }

    public Area(int x, int y, int height, int width, int buildingNo) {
        setup();
        this.relocate(x, y);
        this.setPrefSize(width, height);
        this.buildingNo = buildingNo;
        enableMove();
        disableMove();
        generateLook();
        enableOpenBuilding();

    }

    private void setup() {
        area = this;
        numberCont = new StackPane();
        area.getChildren().add(numberCont);
        this.setAlignment(Pos.CENTER);
    }

    class MousePosition {
        double x, y;
    }

    public void enableMove(){

        final MousePosition dragMousePosition = new MousePosition();

        area.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                area.setCursor(Cursor.MOVE);
                dragMousePosition.x = area.getLayoutX() - mouseEvent.getSceneX();
                dragMousePosition.y = area.getLayoutY() - mouseEvent.getSceneY();
            }
        });

        area.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                area.setLayoutX(mouseEvent.getSceneX() + dragMousePosition.x);
                area.setLayoutY(mouseEvent.getSceneY() + dragMousePosition.y);
            }
        });

        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                area.setCursor(Cursor.HAND);
            }
        });

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                area.setCursor(Cursor.HAND);
            }
        });

        this.setOnMouseExited(e->{});

        this.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        generateCircle();
    }

    public void disableMove(){
        circle.setRadius(5);
        number.setStyle("-fx-font: 6 arial;");
        this.setOnMousePressed(e->{});
        this.setOnMouseDragged(e->{});
        this.setOnMouseReleased(e->{});
        this.setOnMouseEntered(e->{
            area.setStyle("-fx-background-color: rgba(100, 100, 100, 0.5);");
            circle.setRadius(10);
            number.setStyle("-fx-font: 10 arial;");
        });
        this.setOnMouseExited(e->{
            area.setStyle("-fx-background-color: rgba(100, 100, 100, 0.0);");
            circle.setRadius(5);
            number.setStyle("-fx-font: 6 arial;");
        });
    }



    private void createMover(){
    //todo create this method
    }

    public Pane setupArea(int x, int y){
        this.setPrefSize(x, y);
        this.setMinSize(20, 20);
        this.setMaxSize(600, 600);

   return area;
    }

    private void generateCircle(){

        circle = new Circle(10);
        circle.setFill(Color.SKYBLUE);
        circle.setStroke(Color.WHITESMOKE);
        circle.setStrokeWidth(2);


        number = new Text(Integer.toString(buildingNo));
        number.setStyle("-fx-font: 10 arial;");
        numberCont.relocate(2,2);
        numberCont.getChildren().addAll(circle,number);
    }

    public void generateLook(){
        this.getChildren().clear();
        area.getChildren().add(numberCont);
        area.setBackground(null);
    }

    public void enableOpenBuilding(){
        this.setOnMouseClicked(e->openBuilding());
    }
    public void disableOpenBuilding(){
        this.setOnMouseClicked(e->{});
    }


    private void openBuilding(){
        Stage buildingWindow = new Stage();
        Consts.currentBuilding = buildingNo;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/buildinginfo.fxml"));
            buildingWindow.setScene(new Scene(root, 600, 475));
            buildingWindow.setResizable(false);
            buildingWindow.setTitle("Building Information");
            buildingWindow.initModality(Modality.APPLICATION_MODAL);
            buildingWindow.showAndWait();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
