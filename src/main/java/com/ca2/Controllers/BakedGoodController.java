package com.ca2.Controllers;

import com.ca2.ADT.BakedGood;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class BakedGoodController {
    @FXML
    private Label description;

    @FXML
    private ImageView image;


    @FXML
    private Label name;

    @FXML
    private Label origin;

    private BakedGood good;
    private Controller parentController;


    public void setData(BakedGood _good) {
        this.good = _good;

        image.setImage(good.getImage());
        name.setText(good.getName());
        origin.setText(good.getOrigin());
        description.setText(good.getDesc());
    }


    public void setParentController(Controller controller) {
        this.parentController = controller;
    }


    @FXML
    void deleteBakedGood(MouseEvent event) {
        this.parentController.delete(this.good);
        this.parentController.homeView();
    }
}
