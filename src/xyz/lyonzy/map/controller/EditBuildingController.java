package xyz.lyonzy.map.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import xyz.lyonzy.map.misc.Alerts;
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
    TableColumn<Room, String> colRooms;
    @FXML
    TableColumn<String, String > imageCol;
    @FXML
    TableView<String> imageTab;
    @FXML
    TableView<Room> tableView;
    @FXML
    ImageView imageViewOther;

    int imageRef;
    private int roomIndex;
    private Room oldRoom;
    private Room newRoom;
    private Database database = new Database();
    private Building currentBuilding;
    private ObservableList<Room> rooms;
    private ObservableList<String> images;
    private String oldImage, newImage;

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

    private void createEditRoom(boolean edit) {
        Stage stage = new Stage();
        stage.setTitle(edit ? "Edit Room" : "Create New Room");
        GridPane pane = new GridPane();

        Label roomNameLab = new Label("RoomName");
        TextField roomNameFi = new TextField();
        if (edit) roomNameFi.setText(newRoom.getName());

        pane.add(roomNameLab, 1, 1);
        pane.add(roomNameFi, 2, 1);
        pane.setHgap(5);
        pane.setVgap(5);

        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            if (roomNameFi.getText() != null) {
                if (!edit) database.addRoom(currentBuilding.getBuildingNo(), roomNameFi.getText());
                else {
                    database.updateRoom(newRoom.getName(), roomNameFi.getText());
                }
                stage.close();
            }
        });
        pane.add(submit, 2, 2);

        stage.setScene(new Scene(pane, 270, 70));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        tableViewUpdate();
    }


    @FXML
    void addRoom() {
        createEditRoom(false);
    }

    @FXML
    void editRoom() {
        try {
            createEditRoom(true);
        } catch (NullPointerException e) {
            Alerts.alert("Select a Room", "Please select a room below before pressing\nthis button");
        }
    }


    @FXML
    void deleteRoom() {
        try {
            database.deleteRoom(newRoom.getName());
            tableViewUpdate();
        } catch (NullPointerException e) {
            Alerts.alert("Select a Room", "Please select a room below before pressing\nthis button");
        }
    }

    @FXML
    private void close() {
        Consts.setEdit(false);
        Stage stage = (Stage) submit.getScene().getWindow();
        stage.close();
    }


    private void image(boolean edit){
        Stage istage = new Stage();
        istage.setTitle(edit ? "Edit Image Url" : "Create New Image");
        GridPane pane = new GridPane();

        Label imageLab = new Label("Image URL");
        TextField imageurl = new TextField();
        if (edit) imageurl.setText(newImage);

        pane.add(imageLab, 1, 1);
        pane.add(imageurl, 2, 1);
        pane.setHgap(5);
        pane.setVgap(5);

        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            if (imageurl.getText() != null) {
                if (!edit) database.addImage(currentBuilding.getBuildingNo(),imageurl.getText());
                else database.updateImage(newImage, imageurl.getText());
                istage.close();
            }
        });
        pane.add(submit, 2, 2);

        istage.setScene(new Scene(pane, 270, 70));
        istage.initModality(Modality.APPLICATION_MODAL);
        istage.showAndWait();
        tableViewUpdate();
    }


    @FXML private void addImage(){
        image(false);
    }
    @FXML private void editImage(){
        image(true);
    }
    @FXML private void deleteImage(){
        database.deleteImage(newImage);
        tableViewUpdate();
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

            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            }
        } else {
            try {

                this.currentBuilding = new Building(Consts.getCurrentBuilding());
                database.addBuilding(currentBuilding);
                bId.setText(Integer.toString(currentBuilding.getBuildingNo()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        tableViewUpdate();
        colRooms.setCellValueFactory(new PropertyValueFactory<>("name"));
        imageCol.setCellValueFactory(e -> new SimpleStringProperty(e.getValue()));

        tableView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            oldRoom = oldValue;
            newRoom = newValue;
            roomIndex = tableView.getSelectionModel().getSelectedIndex();
        });

        imageTab.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            oldImage = oldValue;
            newImage = newValue;
            try{
                imageViewOther.setImage(new Image(newImage.contains("http") || currentBuilding.getImage().contains("www") ?
                        newImage : "file:" + currentBuilding.getImage()));
            } catch (Exception e){  }
        });



    }

    private void tableViewUpdate() {
        currentBuilding.getRooms();
        rooms = FXCollections.observableArrayList(Consts.rooms);
        tableView.setItems(rooms);

        images = FXCollections.observableArrayList(database.getImages(currentBuilding.getBuildingNo()));
        imageTab.setItems(images);
    }
}
