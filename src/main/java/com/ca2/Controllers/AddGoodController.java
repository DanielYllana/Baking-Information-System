package com.ca2.Controllers;

import com.ca2.ADT.BakedGood;
import com.ca2.ADT.RecipesManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddGoodController {

    @FXML
    private TextField desc;

    @FXML
    private TextField name;

    @FXML
    private TextField origin;

    private Controller parentController;

    private String url;

    private RecipesManager recipes;

    private boolean edit = false;
    private BakedGood bg;

    @FXML
    void saveStock(MouseEvent event) {
        if (this.edit) {
            this.parentController.delete(bg);
        }

        String name = this.name.getText();
        String origin = this.origin.getText();
        String desc = this.desc.getText();

        BakedGood bg = new BakedGood(name, origin, desc, this.url);

        this.recipes.addBakedGood(bg);

        if (this.edit) {
            this.parentController.showBakedGoodView(bg);
        } else {
            this.parentController.homeView();
        }

        this.edit = false;
    }

    @FXML
    void selectImage(MouseEvent event) {
        this.url = this.parentController.selectImageFile();
    }


    public void updateRecipesManager(RecipesManager _recipes) {
        this.recipes = _recipes;
    }


    public void setParentController(Controller controller) {
        this.parentController = controller;
    }

    public void editGood(BakedGood _bg) {
        this.bg = _bg;
        this.name.setText(bg.getName());
        this.desc.setText(bg.getDesc());
        this.origin.setText(bg.getOrigin());
        this.url = bg.getImageURL();

        this.edit = true;
    }


    public void reset() {
        this.name.setText(null);
        this.desc.setText(null);
        this.origin.setText(null);
    }
}
