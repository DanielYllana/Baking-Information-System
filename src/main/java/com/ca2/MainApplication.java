package com.ca2;


import com.ca2.Controllers.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {


    Controller controller;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        this.controller = fxmlLoader.getController();

        this.controller.setStage(stage);


        stage.setTitle("Bakery Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //launch();

        /*
        HashTable<Integer, String> table = new HashTable<Integer, String>();
        table.insert(100, "one hundred");
        table.insert(200, "two hundred");
        table.insert(300, "three hundred");
        table.insert(400, "four hundred");
        table.insert(500, "five hundred");
        table.insert(3, "thrree");
        table.insert(600, "six hundred");
        */
        // Test 1 - HashTable
        //System.out.println(table.get(300));
        //table.remove(300);
        //System.out.println(table.get(300));

        // Test 2 - HashTable
        //System.out.println(table.get(3));
        //table.remove(3);
        //System.out.println(table.get(3));

        launch();
    }
}