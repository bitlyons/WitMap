package xyz.lyonzy.map.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import xyz.lyonzy.map.model.Building;
import xyz.lyonzy.map.model.Consts;
import xyz.lyonzy.map.model.Database;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Brendan Lyons on 25/04/16.
 * this is the controller for buildinginfo.fxml
 */
public class BuildingController implements Initializable{
    @FXML
    public ImageView buildingImageViw;
    @FXML
    Text buildingText, buildingName, openingHours;
    @FXML
    TableView roomTable;
    @FXML
    TableColumn roomsColums;
    @FXML
    Button close;

    Database database = new Database();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
       try {
           Building currentBuilding = database.getBuilding(Consts.getCurrentBuilding());
           this.buildingName.setText(currentBuilding.getBuildingName());
           this.buildingImageViw.setImage(new Image(currentBuilding.getImage()));
           this.buildingText.setText(currentBuilding.getBuildingInfo());
           this.openingHours.setText(currentBuilding.getOpeningHours());
       }catch (Exception e){
           System.out.println("Error");
       }
    }
}
