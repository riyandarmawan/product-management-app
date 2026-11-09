/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package main;

import javafx.application.Application;
import javafx.stage.Stage;
import model.DaftarProduk;

/**
 *
 * @author Riyan
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception{
        new Navigator(stage, new DaftarProduk()).bukaTambahForm();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
