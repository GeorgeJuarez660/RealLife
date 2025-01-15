package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controller.masks.CodeAssociateMaskController;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.CodiceAssociateDTO;
import org.services.LoadPage;
import org.services.Service;

import java.net.URL;
import java.text.ParseException;

public class UpdateAssociateUserController {// Questo è il BorderPane di menu.fxml

    @FXML
    private Label title;

    @FXML
    private HBox createMask;

    private Cliente user;
    private Boolean isAdmin;
    private BorderPane fxmlLoader;
    private Service service;
    private String itemScene;

    private Object maskController;

    //------------------INITIALIZE-----------------------

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

    public void loadMask(String itemScene, String emailKey){
        service = new Service();

        try {
            // Costruisce il percorso completo del file FXML
            URL fileUrl = getClass().getResource("/org/scenes/masks/" + itemScene + ".fxml"); //trova la scena associazione utente
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl);
            VBox mask = loader.load();
            CodeAssociateMaskController codeAssociateMaskController = loader.getController();
            codeAssociateMaskController.setAdminCode();
            codeAssociateMaskController.setUserEmail();
            codeAssociateMaskController.getValues(emailKey);
            maskController = codeAssociateMaskController;
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

        LoadPage.getPartialScene(fxmlLoader, "chooseTCodeAdmin", user);
    }

    @FXML
    private void update(ActionEvent event) throws ParseException { //button per modificare
        System.out.println("Start updating");
        LoadPage.saveStage(event);
        LoadPage.loadingScene("MODIFICA ASSOCIAZIONE IN CORSO...");

        service = new Service();

        if(maskController instanceof CodeAssociateMaskController){
            CodeAssociateMaskController codeAssociateMaskController = (CodeAssociateMaskController) maskController;
            CodiceAssociateDTO ca = codeAssociateMaskController.setValuesWithID();

            service.modificaAssociazioneCodice(ca, user);
        }

    }
}