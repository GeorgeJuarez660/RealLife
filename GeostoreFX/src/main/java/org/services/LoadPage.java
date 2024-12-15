package org.services;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controller.*;
import org.models.Cliente;
import org.models.Utente;
import org.utility.PartialSceneDTO;


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
    public static void goesToMenu(Cliente user) {
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
            menuController.loadHomepage(); //carica l'homepage

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
    public static void getPartialScene(BorderPane fxmlLoader, String innerScene, Cliente user) {
        Pane view = null;
        try {
            // Costruisce il percorso completo del file FXML
            URL fileUrl = Main.class.getResource("/org/scenes/" + innerScene + ".fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl);
            Pane newScene = loader.load();
            Object controller = loader.getController(); // Ottieni il controller della scena caricata

            // setta i il controller a seconda della scena caricata
            /*if (controller instanceof GetsController) {
                GetsController getsController = (GetsController) controller;
                getsController.setTitle("Test Title");
            }*/
            if(controller instanceof HomepageController){
                HomepageController homepageController = (HomepageController) controller;
                homepageController.save(fxmlLoader, user);
                homepageController.title(); //andare a homepage
                homepageController.showButtonCreateNews();
                homepageController.loadNews();
            }
            else if(controller instanceof InfoController){
                InfoController infoController = (InfoController) controller;
                infoController.save(fxmlLoader, user);
                infoController.setVersion("1.6.0");
            }
            // Carica il file FXML
            // Imposta la scena caricata come contenuto centrale del BorderPane
            fxmlLoader.setCenter(newScene);

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }
    }

    @FXML
    public static void getPartialSceneCRU(PartialSceneDTO partialSceneDTO, String IDkey) {
        Pane view = null;
        try {
            // Costruisce il percorso completo del file FXML
            URL fileUrl = Main.class.getResource("/org/scenes/" + partialSceneDTO.getInnerScene() + ".fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl);
            Pane newScene = loader.load();
            Object controller = loader.getController(); // Ottieni il controller della scena caricata

            // setta i il controller a seconda della scena caricata
            if(controller instanceof CreateController){
                CreateController createController = (CreateController) controller;
                createController.save(partialSceneDTO.getFxmlLoader(), partialSceneDTO.getUser());
                createController.setTitle("Creazione notizia");
                createController.loadMask("newsMask");
            }
            else if(controller instanceof ReadController){
                ReadController readController = (ReadController) controller;
                readController.save(partialSceneDTO.getFxmlLoader(), partialSceneDTO.getUser());
                readController.setTitle("Elenco notizie");
                readController.loadItems("newsItem", IDkey);
            }
            else if(controller instanceof UpdateController){
                UpdateController updateController = (UpdateController) controller;
                updateController.save(partialSceneDTO.getFxmlLoader(), partialSceneDTO.getUser());
                updateController.setTitle("Modifica notizia");
                updateController.loadMask("newsMask", IDkey);
            }

            // Carica il file FXML
            // Imposta la scena caricata come contenuto centrale del BorderPane
            partialSceneDTO.getFxmlLoader().setCenter(newScene);

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }
    }
}

