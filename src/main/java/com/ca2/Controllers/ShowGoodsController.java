package com.ca2.Controllers;


import com.ca2.ADT.BakedGood;
import com.ca2.ADT.Ingredient;
import com.ca2.ADT.RecipesManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ShowGoodsController {

    @FXML
    private GridPane goodsGrid;

    @FXML
    private ToggleButton calsSortingToggle;


    @FXML
    private ToggleButton nameSortingToggle;

    @FXML
    private HBox toggleBox;

    ToggleGroup group;

    private RecipesManager recipes;
    private Controller parentController;



    public void showGoods() {
        this.hideToggle(true);
        int indexToggle = this.group.getToggles().indexOf(this.group.getSelectedToggle());
        this.goodsGrid.getChildren().removeAll(this.goodsGrid.getChildren());

        recipes.addGoodsToGrid(this.goodsGrid, this, indexToggle);
    }


    public void showGood(BakedGood bg) {
        this.hideToggle(false);
        this.goodsGrid.getChildren().removeAll(this.goodsGrid.getChildren());
        this.parentController.showBakedGoodView(bg);
    }

    public void setParentController(Controller controller) {
        this.parentController = controller;
    }

    public void reloadRecipes(RecipesManager recipes) {
        this.recipes = recipes;
    }

    public void initToggleGroup() {
        this.group = new ToggleGroup();
        this.group.getToggles().addAll(this.nameSortingToggle, this.calsSortingToggle);
        this.group.selectToggle(this.nameSortingToggle);

        this.group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                showGoods();
            }
        });
    }


    private void hideToggle(boolean value) {
        this.toggleBox.setVisible(value);
    }


    public void showIngredients() {
        this.hideToggle(false);
        this.goodsGrid.getChildren().removeAll(this.goodsGrid.getChildren());
        recipes.addIngredientsToGrid(this.goodsGrid, this);
    }

    public void showIngredient(Ingredient ing) {
        this.hideToggle(false);
        this.goodsGrid.getChildren().removeAll(this.goodsGrid.getChildren());
        this.parentController.showIngredientView(ing);
    }

    public void showIngredients(String param2, String query) {
        this.hideToggle(false);
        this.goodsGrid.getChildren().removeAll(this.goodsGrid.getChildren());
        recipes.addIngredientsToGrid(this.goodsGrid, this, param2, query);
    }

    public void showGoods(String param2, String query) {
        this.hideToggle(true);
        this.goodsGrid.getChildren().removeAll(this.goodsGrid.getChildren());
        recipes.addGoodsToGrid(this.goodsGrid, this, param2, query);
    }
}