package org.services;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controller.*;
import org.models.Cliente;
import org.models.Utente;


import java.net.URL;

public class LoadPage {
    @FXML
    private static Stage savedStage; // Questo è il BorderPane di menu.fxml

    public static void saveStage(Event event) {
        //carica lo stage per cambiare scene
        savedStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    @FXML
    public static void getFullScene(String choosedScene) {
        Pane view = null;
        try {
            // Costruisce il percorso completo del file FXML
            URL fileUrl = Main.class.getResource("/org/scenes/" + choosedScene + ".fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("Nessun file FXML trovato");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl);
            Pane newScene = loader.load();

            //carica la scena
            double prefWidth = savedStage.getWidth(); //dimensione rimane invariata o mantenuta dall'utente
            double prefHeight = savedStage.getHeight();
            Scene scene = new Scene(newScene);
            savedStage.setWidth(prefWidth);
            savedStage.setHeight(prefHeight); //inserendo dimensioni fisse
            savedStage.setScene(scene);
            savedStage.show();

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }
    }

    @FXML
    public static void loginToMenu(Cliente user) {
        Pane view = null;
        try {
            // Costruisce il percorso completo del file FXML
            URL fileUrl = Main.class.getResource("/org/scenes/menu.fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("Nessun file FXML trovato");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl);
            Pane newScene = loader.load();
            MenuController menuController = loader.getController(); //Ottieni il controller della scena caricata
            menuController.saveUser(user);

            //carica la scena
            double prefWidth = savedStage.getWidth(); //dimensione rimane invariata o mantenuta dall'utente
            double prefHeight = savedStage.getHeight();
            Scene scene = new Scene(newScene);
            savedStage.setWidth(prefWidth);
            savedStage.setHeight(prefHeight); //inserendo dimensioni fisse
            savedStage.setScene(scene);
            savedStage.show();

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }
    }

    @FXML
    public static void access(String choose) {
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
            double prefWidth = savedStage.getWidth();
            double prefHeight = savedStage.getHeight();
            Scene scene = new Scene(newScene);
            savedStage.setWidth(prefWidth);
            savedStage.setHeight(prefHeight);
            savedStage.setScene(scene);
            savedStage.show();

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }
    }

    @FXML
    public static void answerScene(String choose, String response) {
        try {
            URL fileUrl = null;

            // Costruisce il percorso completo del file FXML
            if(choose.equals("positive")){
                fileUrl = Main.class.getResource("/org/scenes/positiveAnswer.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("Nessun file FXML trovato");
                }
            }
            else if(choose.equals("negative")){
                fileUrl = Main.class.getResource("/org/scenes/negativeAnswer.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("Nessun file FXML trovato");
                }
            }

            FXMLLoader loader = new FXMLLoader(fileUrl);
            Pane newScene = loader.load();

            AnswerController answerController = (AnswerController) loader.getController(); //Ottieni il controller della scena caricata
            answerController.response(response);

            //carica la scena
            double prefWidth = savedStage.getWidth();
            double prefHeight = savedStage.getHeight();
            Scene scene = new Scene(newScene);
            savedStage.setWidth(prefWidth);
            savedStage.setHeight(prefHeight);
            savedStage.setScene(scene);
            savedStage.show();

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }
    }

    @FXML
    public static void loadingScene(String response) {
        try {
            URL fileUrl = null;

            // Costruisce il percorso completo del file FXML
            fileUrl = Main.class.getResource("/org/scenes/loading.fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("Nessun file FXML trovato");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl);
            Pane newScene = loader.load();
            Object controller = loader.getController(); //Ottieni il controller della scena caricata

            LoadingController loadingController = (LoadingController) loader.getController();
            loadingController.response(response);

            //carica la scena
            double prefWidth = savedStage.getWidth();
            double prefHeight = savedStage.getHeight();
            Scene scene = new Scene(newScene);
            savedStage.setWidth(prefWidth);
            savedStage.setHeight(prefHeight);
            savedStage.setScene(scene);
            savedStage.show();

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