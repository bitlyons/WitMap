package xyz.lyonzy.map.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import xyz.lyonzy.map.model.Building;
import xyz.lyonzy.map.model.Consts;
import xyz.lyonzy.map.model.Database;
import xyz.lyonzy.map.model.Room;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by brendan Lyons on 26/04/2016.
 * This class is the controller for editbuilding.fxml
 */
public class EditBuildingController implements Initializable {
    @FXML
    TextField bId, bName, biURL;
    @FXML
    TextArea bInfo, bOpeningHours;
    @FXML
    Button submit, cancel;
    @FXML
    TableColumn colRooms;
    @FXML
    TableView tableView;

    int roomIndex;
    Room oldRoom;
    Room newRoom;

    Database database = new Database();
    Building currentBuilding;
    int imageRef;
    ObservableList<Room> rooms;

    @FXML
    void submit() {
        try {
            int bId = Integer.parseInt(this.bId.getText());
            String bName = this.bName.getText();
            String biURL = this.biURL.getText();
            String bInfo = this.bInfo.getText();
            String bOpeningHours = this.bOpeningHours.getText();

            try {
                database.updateBuilding(bId, bName, bInfo, bOpeningHours, biURL);
            } catch (Exception e) {
                System.out.println("Failed to update building");
            }

        } catch (NullPointerException e) {
            //todo alert missing field
            System.out.println("something is null");
        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
        }
        close(); //todo only close if no error was found
    }


    @FXML
    void close() {
        Consts.setEdit(false);
        Stage stage = (Stage) submit.getScene().getWindow();
        stage.close();
    }

    @FXML
    void getRoom(){
        Stage editRoom = new Stage();
        Label roomLab = new Label("Room Name :");
        TextField roomName = new TextField();
        GridPane pane = new GridPane();
        pane.add(roomLab, 1,1);
        pane.add(roomName,2,1);

        Button save = new Button("Save");
        save.setOnAction(e->{
            database.updateRoom(roomName.getText(), newRoom.getName());
            //re-get the rooms from the database and set it to the table view
            currentBuilding.getRooms();
            rooms = FXCollections.observableArrayList(Consts.rooms);
            tableView.setItems(rooms);
            editRoom.close();
        });

        pane.add(save,2,2);
        editRoom.setScene(new Scene(pane, 270,70));
        editRoom.showAndWait();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Consts.isEdit()) {
            try {
                this.currentBuilding = database.getBuilding(Consts.getCurrentBuilding());
                bId.setText(Integer.toString(currentBuilding.getBuildingNo()));
                bName.setText(currentBuilding.getBuildingName());
                biURL.setText(currentBuilding.getImage());
                bInfo.setText(currentBuilding.getBuildingInfo());
                bOpeningHours.setText(currentBuilding.getOpeningHours());

                currentBuilding.getRooms();
                rooms = FXCollections.observableArrayList(Consts.rooms);
                tableView.setItems(rooms);
                colRooms.setCellValueFactory(new PropertyValueFactory<Room, String>("name"));

                tableView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
                    if(oldValue instanceof Room) oldRoom = (Room) oldValue;
                    if(newValue instanceof Room) newRoom = (Room) newValue;
                            roomIndex = tableView.getSelectionModel().getSelectedIndex();
                });


            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            }
        } else{
            try {
                System.out.println(Consts.getCurrentBuilding());
                this.currentBuilding = new Building(Consts.getCurrentBuilding());
                database.addBuilding(currentBuilding);
                bId.setText(Integer.toString(currentBuilding.getBuildingNo()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            }
    }
}
