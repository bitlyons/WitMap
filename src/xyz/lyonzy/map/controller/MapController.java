package xyz.lyonzy.map.controller;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
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
    private Database database = new Database();
    private Tooltip minMaxSize = new Tooltip("Min size 20x20, Max 600x600");
    private Area newArea;
    private int tWidth = 0, tHeight = 0;
    private boolean reset = false;

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
        newArea = new Area();

        try {
            tWidth = Integer.parseInt(newWidthText.getText());
            tHeight = Integer.parseInt(newHeightText.getText());
        } catch (Exception e) {
            //todo Alert needing to be numbers
            System.out.println("issue");
        }
        try {
            if (tWidth >= 20 && tWidth <= 600 && tHeight >= 20 && tHeight <= 600) {
                attemptAddingArea();
            }
        } catch (Exception e) {
            //alert error creating area
        }

        if (tWidth >= 20 || tWidth > 600 || tHeight >= 20 || tHeight > 600) ;//alert must be between 5x5 and 600x600;
        else {
            reset = false;
            cancelNewArea();
            newArea.enableMove();
            newArea.disableOpenBuilding();

            newArea.editBuilding(false);
        }
    }

    private void attemptAddingArea() {
        try {
            database.addArea(newArea);
            mapPane.getChildren().add(newArea.setupArea(tWidth, tHeight));
            saveLocationArea.setOpacity(1);
            saveLocationArea.toFront();
        } catch (MySQLIntegrityConstraintViolationException e) {
            newArea.setBuildingNo(newArea.getBuildingNo() + 1);
            attemptAddingArea(); // FIXME: 28/04/16 (temp solution)
            reset = false;
            System.out.println("test");
        } catch (Exception e) {

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
