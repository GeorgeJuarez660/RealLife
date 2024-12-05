package org.services;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controller.AccessController;
import org.controller.GetsController;
import org.controller.Main;


import java.net.URL;

public class LoadPage {
    @FXML
    private static Stage stage; // Questo è il BorderPane di menu.fxml

    @FXML
    public static void getFullScene(ActionEvent event, String clicked) {
        Pane view = null;
        try {
            // Costruisce il percorso completo del file FXML
            URL fileUrl = Main.class.getResource("/org/scenes/" + clicked + ".fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("Nessun file FXML trovato");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl);
            Pane newScene = loader.load();
            /*Object controller = loader.getController(); //Ottieni il controller della scena caricata

            if(controller instanceof PrepageController){
                PrepageController prepageController = (PrepageController) controller;
            }*/


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
    public static void access(ActionEvent event, String choose) {
        try {
            String chooseType =  "login" + choose;

            URL fileUrl = null;

            // Costruisce il percorso completo del file FXML
            if(chooseType.equals("loginAdmin")){
                fileUrl = Main.class.getResource("/org/scenes/loginAdmin.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("Nessun file FXML trovato");
                }
            }
            else if(chooseType.equals("loginUser")){
                fileUrl = Main.class.getResource("/org/scenes/loginCliente.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("Nessun file FXML trovato");
                }
            }
            else{
                fileUrl = Main.class.getResource("/org/scenes/register.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("Nessun file FXML trovato");
                }
            }

            FXMLLoader loader = new FXMLLoader(fileUrl);
            Pane newScene = loader.load();
            /*Object controller = loader.getController(); //Ottieni il controller della scena caricata

            if(controller instanceof AccessController){
                AccessController accessController = (AccessController) controller;
            }*/


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
    public static void getPartialScene(BorderPane fxmlLoader, String clicked) {
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