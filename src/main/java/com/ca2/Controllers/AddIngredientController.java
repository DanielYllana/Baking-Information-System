package com.ca2.Controllers;

import com.ca2.ADT.BakedGood;
import com.ca2.ADT.Ingredient;
import com.ca2.ADT.RecipesManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddIngredientController {


    @FXML
    private TextField cals;

    @FXML
    private TextField desc;

    @FXML
    private Button imageButton;

    @FXML
    private TextField name;

    private Controller parentController;

    private String url;

    private RecipesManager recipes;

    @FXML
    void saveStock(MouseEvent event) {
        String name = this.name.getText();
        String desc = this.desc.getText();
        Double cals = Double.parseDouble(this.cals.getText());

        Ingredient ing = new Ingredient(name, desc, cals, this.url);

        this.recipes.addIngredient(ing);
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
