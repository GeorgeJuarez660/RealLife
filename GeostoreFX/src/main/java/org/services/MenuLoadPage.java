package org.services;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controller.GetsController;
import org.controller.Main;
import org.controller.MenuController;


import java.net.URL;

public class MenuLoadPage {
    @FXML
    private static Stage stage; // Questo è il BorderPane di menu.fxml

    @FXML
    public static void getFullScene(ActionEvent event, String clicked) {
        Pane view = null;
        try {
            // Costruisce il percorso completo del file FXML
            URL fileUrl = Main.class.getResource("/org/scenes/" + clicked + ".fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl);
            Pane newScene = loader.load();
            MenuController controller = loader.getController(); // Ottieni il controller della scena caricata


            controller.setFields();

            //carica la scena
            Scene scene = new Scene(newScene, 800, 600);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }
    }

    @FXML
    public static void getScene(BorderPane fxmlLoader, String clicked) {
        Pane view = null;
        try {
            // Costruisce il percorso completo del file FXML
            URL fileUrl = Main.class.getResource(clicked + ".fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl);
            Pane newScene = loader.load();
            Object controller = loader.getController(); // Ottieni il controller della scena caricata

            // Se il controller è quello della scena 2, possiamo modificare titleGets
            if (controller instanceof GetsController) {
                GetsController getsController = (GetsController) controller;
                getsController.setTitle("Test Title");
            }
            // Carica il file FXML
            // Imposta la scena caricata come contenuto centrale del BorderPane
            fxmlLoader.setCenter(newScene);

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }
    }
}