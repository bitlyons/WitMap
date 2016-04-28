package xyz.lyonzy.map.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import xyz.lyonzy.map.model.Area;
import xyz.lyonzy.map.model.Consts;
import xyz.lyonzy.map.model.Database;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * This is the controller for map.fxml
 **/
public class MapController implements Initializable {

    @FXML
    TextField newWidthText, newHeightText;
    @FXML
    Pane newAreaInsert, mapPane, saveLocationArea;
    @FXML
    RadioMenuItem editMapOff;
    Database database = new Database();
    Tooltip minMaxSize = new Tooltip("Min size 20x20, Max 600x600");
    Area x;

    @FXML
    void newArea() {
        newAreaInsert.setOpacity(1);
        newAreaInsert.toFront();
        newWidthText.setTooltip(minMaxSize);
        newHeightText.setTooltip(minMaxSize);
        disableMoveAll();
    }

    @FXML
    void newSubmit() {
        Area x = new Area();
        int width = 0, height = 0;
        try {
            width = Integer.parseInt(newWidthText.getText());
            height = Integer.parseInt(newHeightText.getText());
        } catch (Exception e) {
            //todo Alert needing to be numbers
            System.out.println("issue");
        }
        try {
            if (width >= 20 && width <= 600 && height >= 20 && height <= 600) {
                database.addArea(x);
                mapPane.getChildren().add(x.setupArea(width, height));
                saveLocationArea.setOpacity(1);
                saveLocationArea.toFront();
            }
        } catch (Exception e) {
            //alert error creating area
        }

        if (width < 20 || width > 600 && height < 20 || height > 600) ;//alert must be between 5x5 and 600x600;

        else{
            cancelNewArea();
            x.enableMove();
            x.disableOpenBuilding();
            x.editBuilding(false);

        }
    }


    @FXML
    public void cancelNewArea() {
        newAreaInsert.toBack();
        newAreaInsert.setOpacity(0);
        newWidthText.setText(null);
        newHeightText.setText(null);
        newWidthText.setTooltip(null);
        newHeightText.setTooltip(null);
    }


    @FXML
    void saveLocation() {
        disableMoveAll();
        for (int i = 0; i < mapPane.getChildren().size(); i++) {
            if (mapPane.getChildren().get(i) instanceof Area) {
                ((Area) mapPane.getChildren().get(i)).generateLook();
                ((Area) mapPane.getChildren().get(i)).enableOpenBuilding();
            }
        }
        editMapOff.setSelected(true);
    }

    @FXML
    void enableMoveAll() {
        cancelNewArea();
        for (int i = 0; i < mapPane.getChildren().size(); i++) {
            if (mapPane.getChildren().get(i) instanceof Area) {
                ((Area) mapPane.getChildren().get(i)).enableMove();
                ((Area) mapPane.getChildren().get(i)).disableOpenBuilding();
            }
        }
        saveLocationArea.setOpacity(1);
        saveLocationArea.toFront();
    }


    public void save() {
        saveLocationArea.setOpacity(1);
        saveLocationArea.toFront();
    }


    @FXML
    void disableMoveAll() {
        saveLocationArea.setOpacity(0);

        for (int i = 0; i < mapPane.getChildren().size(); i++) {
            if (mapPane.getChildren().get(i) instanceof Area) {
                ((Area) mapPane.getChildren().get(i)).disableMove();
                ((Area) mapPane.getChildren().get(i)).setReferenceParent(this);
                ((Area) mapPane.getChildren().get(i)).enableOpenBuilding();
                try {
                    database.updateArea(((Area) mapPane.getChildren().get(i)).getBuildingNo(),
                            ((Area) mapPane.getChildren().get(i)).getX(), ((Area) mapPane.getChildren().get(i)).getY(),
                            ((Area) mapPane.getChildren().get(i)).getPrefHeight(), ((Area) mapPane.getChildren().get(i)).getPrefWidth());
                } catch (Exception e) {
                    System.out.println("Error disable move all");
                    System.out.println(e.getMessage());
                }//todo alert error//
            }

        }
    }

    public void removeArea(Area area) {
        mapPane.getChildren().remove(area);
    }

    public void slideShow() {
        try{
        Stage slideshow = new Stage();
        Parent slide = FXMLLoader.load(getClass().getResource("../view/slideshow.fxml"));
        slideshow.setTitle("Waterford Institute of Technology Slideshow");
        slideshow.setScene(new Scene(slide, 700, 510));
        slideshow.setResizable(false);
        slideshow.showAndWait();}
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Consts.setNoOfBuildings(database.numberOfAreas());
        if (Consts.getNoOfBuildings() > 0) {
            ResultSet areas = database.getAllArea();
            try {
                while (areas.next()) {
                    Area tempArea = new Area(areas.getDouble("x"), areas.getDouble("y"), areas.getDouble("height"),
                            areas.getDouble("width"), areas.getInt("aId"));
                    mapPane.getChildren().add(tempArea);
                    tempArea.setReferenceParent(this);
                }
            } catch (Exception e) {
                System.out.println("error");
            }

        }

    }
}
