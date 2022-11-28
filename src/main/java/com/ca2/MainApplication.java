package com.ca2;


import com.ca2.ADT.HashTable;
import com.ca2.ADT.RecipesManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //launch();

        HashTable<Integer, String> table = new HashTable<Integer, String>();
        table.insert(100, "one hundred");
        table.insert(200, "two hundred");
        table.insert(300, "three hundred");
        table.insert(400, "four hundred");
        table.insert(500, "five hundred");
        table.insert(3, "thrree");
        table.insert(600, "six hundred");

        // Test 1 - HashTable
        //System.out.println(table.get(300));
        //table.remove(300);
        //System.out.println(table.get(300));

        // Test 2 - HashTable
        //System.out.println(table.get(3));
        //table.remove(3);
        //System.out.println(table.get(3));

        URL path = MainApplication.class.getResource("Data.xml");

        RecipesManager recipes = new RecipesManager();
        assert path != null;
        DataManager.load(path.getPath(), recipes);
        System.out.println("Hello");
    }
}