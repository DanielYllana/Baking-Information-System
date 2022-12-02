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

    @FXML
    void saveStock(MouseEvent event) {
        String name = this.name.getText();
        String origin = this.origin.getText();
        String desc = this.desc.getText();

        BakedGood bg = new BakedGood(name, origin, desc, this.url);

        this.recipes.addBakedGood(bg);
        this.parentController.homeView();
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
}
