package xyz.lyonzy.map.controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
    RadioMenuItem editMapOff;

    Tooltip minMaxSize = new Tooltip("Min size 5x5, Max 600x600");

    @FXML
    void newArea() {
        newAreaInsert.setOpacity(1);
        newAreaInsert.toFront();
        newWidthText.setTooltip(minMaxSize);
        newHeightText.setTooltip(minMaxSize);
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
        if (width >= 20 && width <= 600 && height >= 20 && height <= 600) {
            mapPane.getChildren().add(x.setupArea(width, height));
            saveLocationArea.setOpacity(1);
            saveLocationArea.toFront();
        }

        if (width >= 20 || width > 600 || height >= 20 || height > 600) ;//alert must be between 5x5 and 600x600;

        cancelNewArea();
        x.enableMove();
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
            if (mapPane.getChildren().get(i) instanceof Area)
                ((Area) mapPane.getChildren().get(i)).generateLook();
        }
        editMapOff.setSelected(true);
    }

    @FXML
    void enableMoveAll() {
        for (int i = 0; i < mapPane.getChildren().size(); i++) {
            if (mapPane.getChildren().get(i) instanceof Area)
                ((Area) mapPane.getChildren().get(i)).enableMove();
        }
        saveLocationArea.setOpacity(1);
        saveLocationArea.toFront();
    }



    @FXML
    void disableMoveAll() {
        saveLocationArea.setOpacity(0);
        for (int i = 0; i < mapPane.getChildren().size(); i++) {
            if (mapPane.getChildren().get(i) instanceof Area)
                ((Area) mapPane.getChildren().get(i)).disableMove();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
