package xyz.lyonzy.map.misc;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.StageStyle;
import xyz.lyonzy.map.Main;

import java.util.Optional;

/**
 * Created by Brendan Lyons on 26/04/2016.
 * This file deals with any alerts the map may need, from deleting an area+ building, to errors.
 */
public class Alerts {
    static Main parent;

    public static void setParent(Main parentIn) {
        parent = parentIn;
    }

    public static void alert(String title, String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(info);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("About");
        alert.setHeaderText("About this Program");
        Hyperlink link = new Hyperlink("My GitHub");
        link.setOnAction(e -> parent.getHostServices().showDocument("https://github.com/bitlyons"));

        String info = "This Program was created by Brendan Lyons, as part of a student project.\n" +
                "It's goal is to show an ability to work with user interactivity in JavaFX,\n" +
                " ability to use various JavaFX Components, and connectivity to a database with the JDBC.\n";

        TextFlow textArea = new TextFlow();
        textArea.setPrefSize(500, 70);
        textArea.getChildren().add(new Text(info));
        VBox dialog = new VBox(5);

        dialog.setPrefSize(520, 70);
        dialog.getChildren().addAll(textArea, link);

        alert.getDialogPane().contentProperty().set(dialog);
        alert.showAndWait();
    }

    public static void databaseFail() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("A Connection to the Database was not able to be established");
        alert.setContentText("Please make sure you have an active connection, or that the database is running");

        alert.showAndWait();
    }

    public boolean deleteBuilding() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Building");
        alert.setHeaderText("It looks like you wish to delete this building");
        alert.setContentText("Are you sure you wish to do that? this action \nis non-reversible!!!");

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;

    }

}
