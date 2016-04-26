package xyz.lyonzy.map.controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import xyz.lyonzy.map.model.Area;
import xyz.lyonzy.map.model.Consts;
import xyz.lyonzy.map.model.Database;

import java.net.URL;
import java.util.ResourceBundle;

public class MapController implements Initializable {

    @FXML
    TextField newWidthText, newHeightText;
    @FXML
    Pane newAreaInsert, mapPane, saveLocationArea;
    @FXML
    RadioMenuItem editMapOff;
    Database database = new Database();
    Tooltip minMaxSize = new Tooltip("Min size 20x20, Max 600x600");

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

        if (width >= 20 || width > 600 || height >= 20 || height > 600) ;//alert must be between 5x5 and 600x600;

        cancelNewArea();
        x.enableMove();
        x.disableOpenBuilding();
    }


    @FXML
    void cancelNewArea() {
        newAreaInsert.toBack();
        newAreaInsert.setOpacity(0);
        newWidthText.setText(null);
        newHeightText.setText(null);
        newWidthText.setTooltip(null);
        newHeightText.setTooltip(null);
    }


    @FXML
    void saveLocation(){
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
            if (mapPane.getChildren().get(i) instanceof Area){
                ((Area) mapPane.getChildren().get(i)).enableMove();
                ((Area) mapPane.getChildren().get(i)).disableOpenBuilding();
            }
        }
        saveLocationArea.setOpacity(1);
        saveLocationArea.toFront();
    }



    @FXML
    void disableMoveAll() {
        saveLocationArea.setOpacity(0);

        for (int i = 0; i < mapPane.getChildren().size(); i++) {
            if (mapPane.getChildren().get(i) instanceof Area){
                ((Area) mapPane.getChildren().get(i)).disableMove();
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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Consts.setNoOfBuildings(database.numberOfAreas());
        if (Consts.getNoOfBuildings() > 0) {
            for (int i = 0; i < Consts.getNoOfBuildings(); i++) {
                try {
                    mapPane.getChildren().add(database.getArea(i + 1));

                } catch (Exception e) {
                    System.out.println("Error with area");
                    System.out.print(e.getMessage());

                }
            }
        }

    }
}
