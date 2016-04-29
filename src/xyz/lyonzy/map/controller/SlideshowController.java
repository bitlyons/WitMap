package xyz.lyonzy.map.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import xyz.lyonzy.map.model.Database;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Brendan Lyons on 28/04/16.
 * This class controls the slide show
 */
public class SlideshowController implements Initializable {
    @FXML
    Pagination slidepage;
    @FXML
    CheckBox at;
    ArrayList<String> images = new ArrayList<>();
    Timeline wait3Secs = new Timeline();

    private void getImages() {
        Database database = new Database();
        images = database.getAllImages();
    }

    @FXML
    void slideshow() {
        slidepage.setPageCount(images.size());
        slidepage.setPageFactory((Integer pageIndex) -> {
            ImageView image = new ImageView();
            image.setImage(new Image(images.get(pageIndex).contains("http") || images.get(pageIndex).contains("www") ?
                    images.get(pageIndex) : "file:" + images.get(pageIndex), 500, 400, true, false));
            return image;
        });
    }

    @FXML
    void startAnimating() {
        wait3Secs = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            int pos = (slidepage.getCurrentPageIndex() + 1) % slidepage.getPageCount();
            slidepage.setCurrentPageIndex(pos);
        }));

        wait3Secs.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    void animate() {
        if (at.isSelected()) {
            startAnimating();
            wait3Secs.play();
        } else {
            wait3Secs.stop();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getImages();
        slideshow();
        animate();
    }
}
