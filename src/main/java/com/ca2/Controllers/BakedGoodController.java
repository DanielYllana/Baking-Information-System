package com.ca2.Controllers;

import com.ca2.ADT.BakedGood;
import com.ca2.ADT.Ingredient;
import com.ca2.ADT.RecipesManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class BakedGoodController {
    @FXML
    private Label description;

    @FXML
    private ImageView image;


    @FXML
    private Label name;

    @FXML
    private Label origin;

    @FXML
    private ListView<String> ingredientList;

    private BakedGood good;
    private Controller parentController;


    public void setData(BakedGood _good, RecipesManager recipes) {
        this.good = _good;

        image.setImage(good.getImage());
        name.setText(good.getName());
        origin.setText(good.getOrigin());
        description.setText(good.getDesc());

        this.ingredientList.getItems().removeAll(this.ingredientList.getItems());
        this.ingredientList.setCellFactory(new IngredientListCellFactory());

        ObservableList<String> listIngredients = FXCollections.observableArrayList();
        recipes.extractRecipe(listIngredients, _good);
        this.ingredientList.getItems().addAll(listIngredients);

    }

    @FXML
    void drillDown(MouseEvent event) {
        String selection = this.ingredientList.getSelectionModel().getSelectedItem();

        String name = selection.split(":")[0];
        if (!name.isBlank() && !name.isEmpty()) {
            parentController.showIngredientView(name);
        }

    }


    public void setParentController(Controller controller) {
        this.parentController = controller;
    }


    @FXML
    void deleteBakedGood(MouseEvent event) {
        this.parentController.delete(this.good);
        this.parentController.homeView();
    }



    @FXML
    void editBakedGood(MouseEvent event) {
        this.parentController.ediBakedGood(good);
    }



    public static class IngredientListCellFactory implements Callback<ListView<String>, ListCell<String>> {
        @Override
        public ListCell<String> call(ListView<String> param) {
            return new ListCell<String>(){
                @Override
                public void updateItem(String str, boolean empty) {
                    super.updateItem(str, empty);
                    if (empty || str == null) {
                        setText(null);
                        setGraphic(null);
                        return;
                    }
                    setText(str);
                }
            };
        }
    }

}
