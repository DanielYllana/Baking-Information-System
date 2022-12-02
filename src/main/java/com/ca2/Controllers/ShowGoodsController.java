package com.ca2.Controllers;


import com.ca2.ADT.BakedGood;
import com.ca2.ADT.RecipesManager;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class ShowGoodsController {

    @FXML
    private GridPane goodsGrid;

    private RecipesManager recipes;
    private Controller parentController;

    public void showGoods() {
        this.goodsGrid.getChildren().removeAll(this.goodsGrid.getChildren());
        recipes.addGoodsToGrid(this.goodsGrid, this);
    }

    /*
    public void drillDown(DisplayCase dc, DisplayTray dt) {
        this.jewelGrid.getChildren().removeAll(this.jewelGrid.getChildren());
        store.addToGrid(this.jewelGrid, dc, dt, this);
    }*/

    public void showGood(BakedGood bg) {
        this.goodsGrid.getChildren().removeAll(this.goodsGrid.getChildren());
        this.parentController.showBakedGoodView(bg);
    }

    public void setParentController(Controller controller) {
        this.parentController = controller;
    }

    public void reloadRecipes(RecipesManager recipes) {
        this.recipes = recipes;
    }


    public void showIngredients() {
        this.goodsGrid.getChildren().removeAll(this.goodsGrid.getChildren());
        recipes.addIngredientsToGrid(this.goodsGrid, this);
    }
}