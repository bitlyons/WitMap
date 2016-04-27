package xyz.lyonzy.map.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import xyz.lyonzy.map.model.Building;
import xyz.lyonzy.map.model.Consts;
import xyz.lyonzy.map.model.Database;

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

    Database database = new Database();
    Building currentBuilding;
    int imageRef;


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
        } else
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
