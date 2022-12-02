package com.ca2.Controllers;

import com.ca2.ADT.BakedGood;
import com.ca2.ADT.Ingredient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class TileController {

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private ImageView thumb;

    private ShowGoodsController controller;
    private int tileType;

    private BakedGood bg;
    private Ingredient ing;


    public void setData(BakedGood _bg, ShowGoodsController controller) {
        this.tileType = 1;
        this.bg = _bg;

        thumb.setImage(bg.getImage());
        label1.setText(bg.getName());
        label2.setText(bg.getOrigin());
        label3.setText(bg.getDesc());

        this.controller = controller;
        this.tileType = 0;
    }

    public void setData(Ingredient _ing, ShowGoodsController controller) {
        this.tileType = 2;
        this.ing = _ing;

        thumb.setImage(ing.getImage());
        label1.setText(ing.getName());
        label2.setText(ing.getDesc());
        label3.setText(String.valueOf(ing.getCals()));
    }

    @FXML
    void tileClicked(MouseEvent event) {

        this.controller.showGood(this.bg);
    }
}
