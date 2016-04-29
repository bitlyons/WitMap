package xyz.lyonzy.map.model;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import xyz.lyonzy.map.controller.MapController;
import xyz.lyonzy.map.misc.Alerts;

/**
 * Created by brenden on 23/04/2016.
 * This file deals with creating/updating and deleting areas on the map.
 */
public class Area extends FlowPane {
    Alerts alert = new Alerts();
    private Pane area, numberCont;
    private int buildingNo;
    private Circle circle;
    private Text number;
    private double x, y, height, width;
    private MapController referenceParent;

    public Area() {
        setup();
        Consts.setNoOfBuildings(Consts.noOfBuildings + 1);
        buildingNo = Consts.noOfBuildings;
    }

    public Area(double x, double y, double height, double width, int buildingNo) {
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

    public void enableMove() {

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
                Consts.currentBuilding = buildingNo;
            }
        });

        this.setOnMouseExited(e -> {
        });

        this.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        generateCircle();
    }

    public void disableMove() {
        circle.setRadius(5);
        number.setStyle("-fx-font: 6 arial;");
        this.setOnMousePressed(e -> {
        });
        this.setOnMouseDragged(e -> {
        });
        this.setOnMouseReleased(e -> {
        });
        this.setOnMouseEntered(e -> {
            area.setCursor(Cursor.HAND);
            area.setStyle("-fx-background-color: rgba(100, 100, 100, 0.5);");
            circle.setRadius(10);
            number.setStyle("-fx-font: 10 arial;");
            Consts.currentBuilding = buildingNo;
        });
        this.setOnMouseExited(e -> {
            area.setCursor(Cursor.DEFAULT);
            area.setStyle("-fx-background-color: rgba(100, 100, 100, 0.0);");
            circle.setRadius(5);
            number.setStyle("-fx-font: 6 arial;");
        });
        x = this.getLayoutX();
        y = this.getLayoutY();
        width = this.getPrefWidth();
        height = this.getPrefHeight();

    }

    private void createMover() {
        //todo create this method
    }

    public Pane setupArea(int x, int y) {
        this.setPrefSize(x, y);
        this.setMinSize(20, 20);
        this.setMaxSize(600, 600);

        return area;
    }

    private void generateCircle() {

        circle = new Circle(10);
        circle.setFill(Color.SKYBLUE);
        circle.setStroke(Color.WHITESMOKE);
        circle.setStrokeWidth(2);


        number = new Text(Integer.toString(buildingNo));
        number.setStyle("-fx-font: 10 arial;");
        numberCont.relocate(2, 2);
        numberCont.getChildren().addAll(circle, number);
    }

    public void generateLook() {
        this.getChildren().clear();
        area.getChildren().add(numberCont);
        area.setBackground(null);
    }

    public void enableOpenBuilding() {

        this.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) openBuilding();
            if (e.getButton() == MouseButton.SECONDARY) {
                ContextMenu rightClick = new ContextMenu();
                MenuItem open = new MenuItem("Open");
                MenuItem editSize = new MenuItem("Edit Area Size");
                MenuItem moveArea = new MenuItem("Move Area");
                MenuItem edit = new MenuItem("Edit Building Info");
                MenuItem delete = new MenuItem("Delete Building");
                MenuItem cancel = new MenuItem("Cancel");
                rightClick.getItems().addAll(open, editSize, moveArea, edit, delete, cancel);
                rightClick.show(area, e.getScreenX(), e.getScreenY());

                open.setOnAction(f -> openBuilding());

                editSize.setOnAction(f -> {
                    referenceParent.cancelNewArea();
                    Stage areaStage = new Stage();
                    GridPane grid = new GridPane();

                    Label heightLab = new Label("Height");
                    Label widthLab = new Label("Width");

                    TextField height = new TextField();
                    TextField width = new TextField();

                    Button submit = new Button("submit");
                    submit.setOnAction(ee -> {
                        try {
                            this.setPrefSize(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));
                            this.enableMove();
                            this.disableOpenBuilding();
                            referenceParent.save();
                        } catch (Exception g) {//todo alert error
                        }
                        areaStage.close();
                    });

                    grid.add(heightLab, 1, 1);
                    grid.add(height, 1, 2);
                    grid.add(widthLab, 2, 1);
                    grid.add(width, 2, 2);
                    grid.add(submit, 1, 3);
                    areaStage.setScene(new Scene(grid, 300, 80));
                    areaStage.showAndWait();

                });
                edit.setOnAction(f -> {
                    editBuilding(true);
                    referenceParent.cancelNewArea();
                });
                moveArea.setOnAction(f -> {
                    referenceParent.cancelNewArea();
                    enableMove();
                    disableOpenBuilding();
                    referenceParent.save();
                });
                delete.setOnAction(f -> {
                    referenceParent.cancelNewArea();
                    if (alert.deleteBuilding()) {
                        Database database = new Database();
                        database.deleteBuilding(this.buildingNo);
                        database.deleteArea(this.buildingNo);
                        referenceParent.removeArea(this);
                    }
                });
            }
        });
    }

    public void disableOpenBuilding() {
        this.setOnMouseClicked(e -> {
        });
    }

    public void openBuilding() {
        Stage buildingWindow = new Stage();
        Consts.currentArea = this;
        Consts.currentBuilding = buildingNo;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/buildinginfo.fxml"));
            buildingWindow.setScene(new Scene(root, 600, 475));
            buildingWindow.setResizable(false);
            buildingWindow.setTitle("Building Information");
            buildingWindow.initModality(Modality.APPLICATION_MODAL);
            buildingWindow.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void editBuilding(boolean edit) {
        Consts.currentArea = this;
        Consts.currentBuilding = buildingNo;
        Consts.edit = edit;
        Stage buildingWindow = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/editbuilding.fxml"));
            buildingWindow.setScene(new Scene(root, 600, 480));
            buildingWindow.setResizable(false);
            buildingWindow.setTitle("Building Information");
            buildingWindow.initModality(Modality.APPLICATION_MODAL);
            buildingWindow.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(int buildingNo) {
        this.buildingNo = buildingNo;
    }

    public void setReferenceParent(MapController referenceParent) {
        this.referenceParent = referenceParent;
    }

    class MousePosition {
        double x, y;
    }
}
