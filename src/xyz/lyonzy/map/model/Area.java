package xyz.lyonzy.map.model;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * Created by brend on 23/04/2016.
 */
public class Area extends FlowPane{
    private Pane area, numberCont;
    private int buildingNo;


    public Area() {
        area=this;
        numberCont = new StackPane();
        area.getChildren().add(numberCont);
        Consts.setNoOfBuildings(Consts.noOfBuildings+1);
        buildingNo = Consts.noOfBuildings;
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

        area.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                area.setCursor(Cursor.HAND);
            }
        });

        area.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                area.setCursor(Cursor.HAND);
            }
        });

        area.setOnMouseExited(e->{});

        area.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        generateCircle();
    }

    public void disableMove(){

        area.setOnMousePressed(e->{});
        area.setOnMouseDragged(e->{});
        area.setOnMouseReleased(e->{});
        area.setOnMouseEntered(e->area.setStyle("-fx-background-color: rgba(100, 100, 100, 0.5);"));
        area.setOnMouseExited(e->area.setStyle("-fx-background-color: rgba(100, 100, 100, 0.0);"));
    }



    private void createMover(){
    //todo create this method
    }

    public Pane setupArea(int x, int y){
        area.setPrefSize(x, y);
        area.setMinSize(20, 20);
        area.setMaxSize(600, 600);

   return area;
    }

    private void generateCircle(){

        Circle circle = new Circle(10);
        circle.setFill(Color.SKYBLUE);
        circle.setStroke(Color.WHITESMOKE);
        circle.setStrokeWidth(2);


        Text number = new Text(Integer.toString(buildingNo));
        numberCont.relocate(2,2);
        numberCont.getChildren().addAll(circle,number);
    }

    public void generateLook(){
        this.getChildren().clear();
        area.getChildren().add(numberCont);
        area.setBackground(null);
    }
}
