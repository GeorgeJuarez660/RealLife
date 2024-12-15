package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controller.mask.NewsMaskController;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.News;
import org.services.LoadPage;
import org.services.Service;

import java.net.URL;
import java.text.ParseException;

public class UpdateController {// Questo è il BorderPane di menu.fxml

    @FXML
    private Label title;

    @FXML
    private HBox updateMask;

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

    public void loadMask(String maskScene, String IDkey){
        try {
            // Costruisce il percorso completo del file FXML della maschera
            URL fileUrl = getClass().getResource("/org/scenes/mask/" + maskScene + ".fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl);
            VBox mask = loader.load();
            Object controller = loader.getController(); // Ottieni il controller della scena caricata
            if (controller instanceof NewsMaskController) {
                NewsMaskController newsMaskController = (NewsMaskController) controller;
                newsMaskController.getValues(IDkey);
                maskController = newsMaskController;
            }
            updateMask.getChildren().add(mask);

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
    private void update(ActionEvent event) throws ParseException { //button per modificare
        System.out.println("Start creating");
        LoadPage.saveStage(event);
        LoadPage.loadingScene("MODIFICA IN CORSO...");

        service = new Service();

        NewsMaskController newsMaskController = (NewsMaskController) maskController;
        News n = newsMaskController.setValuesWithID();
        service.modificaNotizia(n, user);
    }
}