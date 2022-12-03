package com.ca2.Controllers;

import com.ca2.ADT.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class AddRecipeController {

    @FXML
    private ComboBox<BakedGood> comboBoxGood;

    @FXML
    private Button ingredientButton;

    @FXML
    private VBox leftVBox;

    @FXML
    private VBox rightVBox;

    private Controller parentController;

    private RecipesManager recipes;

    private ObservableList<Ingredient> listIngredients;


    public void reset() {
        Node tempLeft = this.leftVBox.getChildren().get(0);
        this.leftVBox.getChildren().removeAll(this.leftVBox.getChildren());
        this.leftVBox.getChildren().add(tempLeft);

        Node tempRight = this.rightVBox.getChildren().get(0);
        this.rightVBox.getChildren().removeAll(this.rightVBox.getChildren());
        this.rightVBox.getChildren().add(tempRight);
        this.rightVBox.getChildren().add(this.ingredientButton);

        this.comboBoxGood.getItems().removeAll(this.comboBoxGood.getItems());
    }


    @FXML
    void addIngredient(MouseEvent event) {
        ComboBox<Ingredient> temp = new ComboBox<>();
        temp.setCellFactory(new IngredientCellFactory());
        temp.getItems().addAll(this.listIngredients);

        leftVBox.getChildren().add(temp);

        rightVBox.getChildren().set(rightVBox.getChildren().size() - 1, new TextField());
        rightVBox.getChildren().add(this.ingredientButton);
    }


    @FXML
    void saveStock(MouseEvent event) {
        if (this.leftVBox.getChildren().size() <= 1) {
            this.parentController.homeView();
            return;
        }

        boolean valid = true;

        HashTable<String, Double> ingredientTable = new HashTable<>();

        for (int i = 0; i < this.leftVBox.getChildren().size() - 1; i++) {
            ComboBox<Ingredient> ing = (ComboBox<Ingredient>) this.leftVBox.getChildren().get(i + 1);
            TextField amount = (TextField) this.rightVBox.getChildren().get(i + 1);
            if (ing == null || amount == null) {
                valid = false;
                continue;
            }

            ingredientTable.insert(ing.getValue().getName(), Double.parseDouble(amount.getText()));
        }

        BakedGood bg = this.comboBoxGood.getValue();

        if (bg == null) {
            valid = false;
        }

        if (valid) {
            this.recipes.addRecipe(bg, ingredientTable);
        }

        this.parentController.homeView();
    }


    public void initController() {
        ObservableList<BakedGood> listBakedGood = FXCollections.observableArrayList();
        this.recipes.addGoodsToList(listBakedGood);
        this.comboBoxGood.getItems().addAll(listBakedGood);

        this.listIngredients = FXCollections.observableArrayList();
        this.recipes.addIngredientsToList(listIngredients);
    }


    public void updateRecipesManager(RecipesManager _recipes) {
        this.recipes = _recipes;
    }


    public void setParentController(Controller controller) {
        this.parentController = controller;
    }


    public void setCellFactories() {
        this.comboBoxGood.setCellFactory(new BakedGoodCellFactory());
    }



    public static class IngredientCellFactory implements Callback<ListView<Ingredient>, ListCell<Ingredient>> {
        @Override
        public ListCell<Ingredient> call(ListView<Ingredient> param) {
            return new ListCell<Ingredient>() {
                @Override
                public void updateItem(Ingredient ing, boolean empty) {
                    super.updateItem(ing, empty);
                    if (empty || ing == null) {
                        setText(null);
                        setGraphic(null);
                        return;
                    }
                    setText(ing.getName());
                }
            };
        }
    }

    public static class BakedGoodCellFactory implements Callback<ListView<BakedGood>, ListCell<BakedGood>> {
        @Override
        public ListCell<BakedGood> call(ListView<BakedGood> param) {
            return new ListCell<BakedGood>(){
                @Override
                public void updateItem(BakedGood bg, boolean empty) {
                    super.updateItem(bg, empty);
                    if (empty || bg == null) {
                        setText(null);
                        setGraphic(null);
                        return;
                    }
                    setText(bg.getName());
                }
            };
        }
    }


}
