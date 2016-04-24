package xyz.lyonzy.map.controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import xyz.lyonzy.map.model.Area;

import java.net.URL;
import java.util.ResourceBundle;

public class MapController implements Initializable {

    @FXML
    TextField newWidthText, newHeightText;
    @FXML
    Pane newAreaInsert, mapPane, saveLocationArea;


    @FXML
    void newArea() {
        newAreaInsert.setOpacity(1);
        newAreaInsert.toFront();
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
        if (width >= 20 && width <= 1000 && height >= 20 && height <= 1000){
            mapPane.getChildren().add(x.setupArea(width, height));
            saveLocationArea.setOpacity(1);
            saveLocationArea.toFront();
        }

        if (width >= 20 || width > 1000 || height >= 20 || height > 1000) ;//alert must be between 5x5 and 1000x1000;

        cancelNewArea();
        x.enableMove();
    }


    @FXML
    void cancelNewArea() {
        newAreaInsert.setOpacity(0);
        newWidthText.setText(null);
        newHeightText.setText(null);
    }


    @FXML
    void saveLocation(){
        saveLocationArea.setOpacity(0);
        disableMoveAll();
        for (int i = 0; i < mapPane.getChildren().size(); i++) {
            if (mapPane.getChildren().get(i) instanceof Area)
                ((Area) mapPane.getChildren().get(i)).generateLook();
        }
    }

    @FXML
    void enableMoveAll() {
        for (int i = 0; i < mapPane.getChildren().size(); i++) {
            if (mapPane.getChildren().get(i) instanceof Area)
                ((Area) mapPane.getChildren().get(i)).enableMove();
        }
    }



    @FXML
    void disableMoveAll() {
        for (int i = 0; i < mapPane.getChildren().size(); i++) {
            if (mapPane.getChildren().get(i) instanceof Area)
                ((Area) mapPane.getChildren().get(i)).disableMove();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
