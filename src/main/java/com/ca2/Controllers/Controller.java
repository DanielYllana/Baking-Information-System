package com.ca2.Controllers;

import com.ca2.ADT.BakedGood;
import com.ca2.ADT.Ingredient;
import com.ca2.ADT.RecipesManager;
import com.ca2.DataManager;
import com.ca2.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller  implements Initializable {

    @FXML
    private HBox addBakedGood;

    @FXML
    private HBox addIngredient;

    @FXML
    private HBox addRecipe;

    @FXML
    private VBox mainPane;

    @FXML
    private TextField searchText;

    @FXML
    private HBox showGoods;

    @FXML
    private HBox showIngredients;

    @FXML
    private ComboBox<String> comboBoxParam1;

    @FXML
    private ComboBox<String> comboBoxParam2;



    RecipesManager recipes;

    private Stage stage;


    private ScrollPane goodsViewer;
    private ShowGoodsController showGoodsController;


    private VBox bakedGoodViewer;
    private BakedGoodController bakedGoodViewerController;

    private VBox addGoodViewer;
    private AddGoodController addGoodViewerController;

    private VBox addIngredientViewer;
    private AddIngredientController addIngredientViewerController;

    private VBox addRecipeViewer;
    private AddRecipeController addRecipeViewerController;

    private VBox ingredientViewer;
    private IngredientController ingredientViewerController;

    public Controller() {
        URL path = MainApplication.class.getResource("Data.xml");

        this.recipes = new RecipesManager();
        assert path != null;
        DataManager.load(path.getPath(), recipes);


        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApplication.class.getResource("goods-viewer.fxml"));
            this.goodsViewer = fxmlLoader.load();
            this.showGoodsController = fxmlLoader.getController();
            this.showGoodsController.setParentController(this);
            this.showGoodsController.initToggleGroup();


            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApplication.class.getResource("good-viewer.fxml"));
            this.bakedGoodViewer = fxmlLoader.load();
            this.bakedGoodViewerController = fxmlLoader.getController();
            this.bakedGoodViewerController.setParentController(this);


            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApplication.class.getResource("add-good.fxml"));
            this.addGoodViewer = fxmlLoader.load();
            this.addGoodViewerController = fxmlLoader.getController();
            this.addGoodViewerController.setParentController(this);


            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApplication.class.getResource("add-ingredient.fxml"));
            this.addIngredientViewer = fxmlLoader.load();
            this.addIngredientViewerController = fxmlLoader.getController();
            this.addIngredientViewerController.setParentController(this);

            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApplication.class.getResource("add-recipes.fxml"));
            this.addRecipeViewer = fxmlLoader.load();
            this.addRecipeViewerController = fxmlLoader.getController();
            this.addRecipeViewerController.setParentController(this);
            this.addRecipeViewerController.setCellFactories();


            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApplication.class.getResource("ingredient-viewer.fxml"));
            this.ingredientViewer = fxmlLoader.load();
            this.ingredientViewerController = fxmlLoader.getController();
            this.ingredientViewerController.setParentController(this);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    @FXML
    void changeParam1(ActionEvent event) {
        this.comboBoxParam2.getItems().removeAll(this.comboBoxParam2.getItems());

        if (this.comboBoxParam1.getValue().equals("Baked Good")) {
            this.comboBoxParam2.getItems().addAll("Name", "Origin");
        } else if (this.comboBoxParam1.getValue().equals("Ingredient")) {
            this.comboBoxParam2.getItems().addAll("Name", "Description");
        }
        this.comboBoxParam2.getSelectionModel().selectFirst();
    }


    public void homeView() {
        this.showGoods(null);
    }


    public void ediBakedGood(BakedGood bg) {
        this.addGoodViewerController.updateRecipesManager(this.recipes);
        this.addGoodViewerController.editGood(bg);

        this.mainPane.getChildren().removeAll(this.mainPane.getChildren());
        this.mainPane.getChildren().add(this.addGoodViewer);
    }


    public void editIngredient(Ingredient ing) {
        this.addIngredientViewerController.updateRecipesManager(this.recipes);
        this.addIngredientViewerController.editIngredient(ing);

        this.mainPane.getChildren().removeAll(this.mainPane.getChildren());
        this.mainPane.getChildren().add(this.addIngredientViewer);
    }

    @FXML
    void addBakedGood(MouseEvent event) {
        this.selectedMenu(addBakedGood);
        this.addGoodViewerController.reset();
        this.addGoodViewerController.updateRecipesManager(this.recipes);

        this.mainPane.getChildren().removeAll(this.mainPane.getChildren());
        this.mainPane.getChildren().add(this.addGoodViewer);
    }

    @FXML
    void addIngredient(MouseEvent event) {
        this.selectedMenu(addIngredient);
        this.addIngredientViewerController.reset();
        this.addIngredientViewerController.updateRecipesManager(this.recipes);

        this.mainPane.getChildren().removeAll(this.mainPane.getChildren());
        this.mainPane.getChildren().add(this.addIngredientViewer);
    }

    @FXML
    void addRecipe(MouseEvent event) {
        this.selectedMenu(addRecipe);
        this.addRecipeViewerController.reset();
        this.addRecipeViewerController.updateRecipesManager(this.recipes);
        this.addRecipeViewerController.initController();

        this.mainPane.getChildren().removeAll(this.mainPane.getChildren());
        this.mainPane.getChildren().add(this.addRecipeViewer);
    }

    @FXML
    void showIngredients(MouseEvent event) {
        this.selectedMenu(showIngredients);

        this.mainPane.getChildren().removeAll(this.mainPane.getChildren());
        this.mainPane.getChildren().add(this.goodsViewer);

        this.showGoodsController.reloadRecipes(this.recipes);
        this.showGoodsController.showIngredients();
    }

    @FXML
    void loadData(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Stock File");


        //Extention filter
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        String userDirectoryString = System.getProperty("user.dir");
        File userDirectory = new File(userDirectoryString);

        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }

        fileChooser.setInitialDirectory(userDirectory);

        File chosenFile = fileChooser.showOpenDialog(this.stage);

        if(chosenFile != null) {
            DataManager.load(chosenFile.getPath(), this.recipes);
        } else {
            System.out.println("Unable to open file");
        }

        this.homeView();
    }

    @FXML
    void reloadSearch(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            this.search(null);
        }
    }

    @FXML
    void resetData(MouseEvent event) {
        this.recipes.reset();
        this.homeView();
    }



    @FXML
    void saveData(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        String userDirectoryString = System.getProperty("user.dir");
        File userDirectory = new File(userDirectoryString);

        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }

        fileChooser.setInitialDirectory(userDirectory);

        File file = fileChooser.showSaveDialog(this.stage);

        if (file != null) {
            System.out.println("Saving xml to file: " + file.getPath());
            DataManager.save(file, this.recipes);
        }
    }



    @FXML
    void search(MouseEvent event) {
        String param1 = this.comboBoxParam1.getValue();
        String param2 = this.comboBoxParam2.getValue();
        String query = this.searchText.getText();

        if (query.isBlank()) {
            return ;
        }

        this.selectedMenu(null);
        this.mainPane.getChildren().removeAll(this.mainPane.getChildren());
        this.mainPane.getChildren().add(this.goodsViewer);

        if (param1.equals("Ingredient")) {
            this.showGoodsController.reloadRecipes(this.recipes);
            this.showGoodsController.showIngredients(param2, query);
        } else if (param1.equals("Baked Good")) {
            this.showGoodsController.reloadRecipes(this.recipes);
            this.showGoodsController.showGoods(param2, query);
        }

    }

    @FXML
    void showGoods(MouseEvent event) {
        this.selectedMenu(showGoods);

        this.mainPane.getChildren().removeAll(this.mainPane.getChildren());
        this.mainPane.getChildren().add(this.goodsViewer);
        this.showGoodsController.reloadRecipes(this.recipes);
        this.showGoodsController.showGoods();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("init");
        this.showGoods(null);
        this.homeView();

        this.comboBoxParam1.getItems().addAll("Baked Good", "Ingredient");
        this.comboBoxParam1.getSelectionModel().selectFirst();
        this.changeParam1(null);
    }


    public String selectImageFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image");

        // Extension filter
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png", "JPG files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extensionFilter);

        String userDirectoryString = System.getProperty("user.dir") + "/src/main/resources/com/ca2/images";
        File userDirectory = new File(userDirectoryString);

        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }

        fileChooser.setInitialDirectory(userDirectory);

        File chosenFile = fileChooser.showOpenDialog(this.stage);

        if(chosenFile != null) {
            return chosenFile.getPath();
        } else {
            System.out.println("Unable to open file");
            return "";
        }
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }


    private void selectedMenu(HBox box) {
        this.showIngredients.getStyleClass().remove("selected");
        this.showGoods.getStyleClass().remove("selected");
        this.addBakedGood.getStyleClass().remove("selected");
        this.addRecipe.getStyleClass().remove("selected");
        this.addIngredient.getStyleClass().remove("selected");


        if (box != null) {
            box.getStyleClass().add("selected");
        }
    }

    public void showBakedGoodView(BakedGood bg) {
        this.mainPane.getChildren().removeAll(this.mainPane.getChildren());
        this.mainPane.getChildren().add(this.bakedGoodViewer);
        this.bakedGoodViewerController.setData(bg, recipes);
    }

    public void setRecipes(RecipesManager recipes) {
        this.recipes = recipes;
    }

    public void delete(BakedGood good) {
        this.recipes.delete(good);
    }

    public void delete(Ingredient ing) { this.recipes.delete(ing); }

    public void showIngredientView(Ingredient ing) {
        this.mainPane.getChildren().removeAll(this.mainPane.getChildren());
        this.mainPane.getChildren().add(this.ingredientViewer);
        this.ingredientViewerController.setData(ing, recipes);
    }


    public void showIngredientView(String t1) {
        this.showIngredientView(this.recipes.getIngredient(t1));
    }
}
