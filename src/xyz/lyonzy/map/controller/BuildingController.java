package xyz.lyonzy.map.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import xyz.lyonzy.map.model.Building;
import xyz.lyonzy.map.model.Consts;
import xyz.lyonzy.map.model.Database;
import xyz.lyonzy.map.model.Room;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Brendan Lyons on 25/04/16.
 * this is the controller for buildinginfo.fxml
 */
public class BuildingController implements Initializable {
    @FXML
    public ImageView buildingImageViw;
    @FXML
    Text buildingText, buildingName, openingHours;
    @FXML
    TableView<Room> roomTable;
    @FXML
    TableColumn<Room, String> roomsColums;
    @FXML
    Button close;
    @FXML
    MenuItem edit;

    Database database = new Database();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Building currentBuilding = database.getBuilding(Consts.getCurrentBuilding());
            this.buildingName.setText(currentBuilding.getBuildingName());
            this.buildingText.setText(currentBuilding.getBuildingInfo());
            this.openingHours.setText(currentBuilding.getOpeningHours());

            currentBuilding.getRooms();
            ObservableList<Room> rooms = FXCollections.observableArrayList(Consts.rooms);
            roomTable.setItems(rooms);
            roomsColums.setCellValueFactory(new PropertyValueFactory<Room, String>("name"));


            edit.setOnAction(e -> {
                Consts.getCurrentArea().editBuilding(true);
                close();
            });

            try {
                if (!currentBuilding.getImage().equals("def" + currentBuilding.getBuildingNo())) {
                    this.buildingImageViw.setImage(new Image(currentBuilding.getImage().contains("http") || currentBuilding.getImage().contains("www") ?
                            currentBuilding.getImage() : "file:" + currentBuilding.getImage()));
                }
            } catch (Exception e) {
                System.out.println("no image");
            }
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    @FXML
    void close() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
}
