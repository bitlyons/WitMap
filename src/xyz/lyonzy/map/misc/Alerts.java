package xyz.lyonzy.map.misc;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Created by Brendan Lyons on 26/04/2016.
 * This file deals with any alerts the map may need, from deleting an area+ building, to errors.
 */
public class Alerts {

    public boolean deleteBuilding() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Building");
        alert.setHeaderText("It looks like you wish to delete this building");
        alert.setContentText("Are you sure you wish to do that? this action \nis non-reversible!!!");

        Optional<ButtonType> result = alert.showAndWait();

        return result.get() == ButtonType.OK;
    }
}
