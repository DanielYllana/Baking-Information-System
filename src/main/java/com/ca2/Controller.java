package com.ca2;

import com.ca2.ADT.RecipesManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private HBox drillDown;

    @FXML
    private VBox mainPane;

    @FXML
    private TextField searchText;

    @FXML
    private HBox showGoods;


    RecipesManager recipes;

    private Stage stage;


    public Controller() {
        this.recipes = new RecipesManager();
        this.homeView();
        // Here go the other controllers

    }


    public void homeView() {
        this.showGoods(null);
    }






    @FXML
    void addBakedGood(MouseEvent event) {
        this.selectedMenu(addBakedGood);
    }

    @FXML
    void addIngredient(MouseEvent event) {
        this.selectedMenu(addIngredient);
    }

    @FXML
    void addRecipe(MouseEvent event) {
        this.selectedMenu(addRecipe);
    }

    @FXML
    void drillDown(MouseEvent event) {
        this.selectedMenu(drillDown);
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

    }

    @FXML
    void showGoods(MouseEvent event) {
        this.selectedMenu(showGoods);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.homeView();
    }


    public String selectImageFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image");

        // Extension filter
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png", "JPG files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extensionFilter);

        String userDirectoryString = System.getProperty("user.dir") + "/src/main/resources/com/example/assignment1/images";
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
        this.drillDown.getStyleClass().remove("selected");
        this.showGoods.getStyleClass().remove("selected");
        this.addBakedGood.getStyleClass().remove("selected");
        this.addRecipe.getStyleClass().remove("selected");
        this.addIngredient.getStyleClass().remove("selected");


        if (box != null) {
            box.getStyleClass().add("selected");
        }
    }
}
