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

    private Ingredient ing;

    private boolean edit = false;

    @FXML
    void saveStock(MouseEvent event) {
        if (this.edit) {
            this.parentController.delete(this.ing);
        }
        String name = this.name.getText();
        String desc = this.desc.getText();
        Double cals = Double.parseDouble(this.cals.getText());

        Ingredient ing = new Ingredient(name, desc, cals, this.url);
        this.recipes.addIngredient(ing);

        if (this.edit) {
            this.parentController.showIngredientView(ing);
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

    public void editIngredient(Ingredient _ing) {
        this.ing = _ing;
        this.name.setText(ing.getName());
        this.desc.setText(ing.getDesc());
        this.cals.setText(String.valueOf(ing.getCals()));
        this.url = _ing.getImageURL();

        this.edit = true;
    }


    public void reset() {
        this.name.setText(null);
        this.desc.setText(null);
        this.cals.setText(null);

        this.edit = false;
    }
}
