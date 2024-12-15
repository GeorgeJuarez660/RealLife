package org.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controller.mask.NewsMaskController;
import org.models.*;
import org.services.LoadPage;
import org.services.Service;
import org.utility.Utility;

import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CreateController {// Questo è il BorderPane di menu.fxml

    @FXML
    private Label title;

    @FXML
    private HBox createMask;

    private Cliente user;
    private Boolean isAdmin;
    private BorderPane fxmlLoader;
    private Service service;

    private Object maskController;

    //------------------INIZIALIZE-----------------------

    public void save(BorderPane fxmlLoader, Cliente utente){

        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) utente;
            user = admin;
            isAdmin = admin.getCodeAdmin() != null && !admin.getCodeAdmin().isEmpty() && !admin.getCodeAdmin().isBlank();
        }
        else{
            user = utente;
            isAdmin = false;
        }

        this.fxmlLoader = fxmlLoader;
    }

    public void setTitle(String value) {
        title.setText(value);
    }

    public void loadMask(String maskScene){
        try {
            // Costruisce il percorso completo del file FXML
            URL fileUrl = getClass().getResource("/org/scenes/mask/" + maskScene + ".fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl);
            VBox mask = loader.load();
            Object controller = loader.getController(); // Ottieni il controller della scena caricata
            if (controller instanceof NewsMaskController) {
                NewsMaskController newsMaskController = (NewsMaskController) controller;
                newsMaskController.setDate();
                maskController = newsMaskController;
            }
            createMask.getChildren().add(mask);

            // Carica il file FXML
            // Imposta la scena caricata come contenuto centrale del BorderPane

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void back() { //button per tornare indietro
        System.out.println("Going back");
        LoadPage.getPartialScene(fxmlLoader, "homepage", user);
    }

    @FXML
    private void create(ActionEvent event) throws ParseException { //button per creare
        System.out.println("Start creating");
        LoadPage.saveStage(event);
        LoadPage.loadingScene("CREAZIONE IN CORSO...");

        service = new Service();

        NewsMaskController newsMaskController = (NewsMaskController) maskController;
        News n = newsMaskController.setValues();
        service.creazioneNotizia(n, user);
    }
}