package com.ca2.Controllers;

import com.ca2.ADT.BakedGood;
import com.ca2.ADT.Ingredient;
import com.ca2.ADT.RecipesManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class IngredientController {

    @FXML
    private Label calories;

    @FXML
    private Label desc;

    @FXML
    private ImageView image;

    @FXML
    private Label name;

    private Ingredient ing;
    private Controller parentController;


    public void setData(Ingredient _ing, RecipesManager recipes) {
        this.ing = _ing;

        image.setImage(ing.getImage());
        name.setText(ing.getName());
        desc.setText(ing.getDesc());
        calories.setText(String.valueOf(ing.getCals()));
    }


    public void setParentController(Controller controller) {
        this.parentController = controller;
    }


    @FXML
    void deleteIngredient(MouseEvent event) {
        this.parentController.delete(this.ing);
        this.parentController.homeView();
    }



    @FXML
    void editIngredient(MouseEvent event) {
        this.parentController.editIngredient(this.ing);
    }

}
