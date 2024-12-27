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
import org.controller.mask.ProductMaskController;
import org.controller.mask.UserMaskController;
import org.models.*;
import org.services.LoadPage;
import org.services.Service;
import org.utility.Utility;

import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreateController {// Questo è il BorderPane di menu.fxml

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

    public void setTitle(String itemScene) {
        if(itemScene != null && itemScene.equals("user")){
            title.setText("Creazione utente");
        }
        else if(itemScene != null && itemScene.equals("product")){
            title.setText("Creazione prodotto");
        }
        else{
            title.setText("Creazione notizia");
        }
    }

    public void loadMask(String itemScene){
        this.itemScene = itemScene;

        if(this.itemScene != null && this.itemScene.equals("user")){
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/mask/userMask.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl);
                VBox mask = loader.load();
                UserMaskController userMaskController = loader.getController();// Ottieni il controller della scena caricata
                maskController = userMaskController;
                createMask.getChildren().add(mask);

                // Carica il file FXML
                // Imposta la scena caricata come contenuto centrale del BorderPane

            } catch (Exception e) {
                System.out.println("No page found. Please check FXMLLoader.");
                e.printStackTrace();
            }
        }
        else if(this.itemScene != null && this.itemScene.equals("product")){
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/mask/productMask.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl);
                VBox mask = loader.load();
                ProductMaskController productMaskController = loader.getController();// Ottieni il controller della scena caricata
                productMaskController.setAvailable();
                productMaskController.setCategory();
                productMaskController.setMaterial();
                maskController = productMaskController;
                createMask.getChildren().add(mask);

                // Carica il file FXML
                // Imposta la scena caricata come contenuto centrale del BorderPane

            } catch (Exception e) {
                System.out.println("No page found. Please check FXMLLoader.");
                e.printStackTrace();
            }
        }
        else{
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/mask/newsMask.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl);
                VBox mask = loader.load();
                NewsMaskController newsMaskController = loader.getController();// Ottieni il controller della scena caricata
                newsMaskController.setDate();
                maskController = newsMaskController;
                createMask.getChildren().add(mask);

                // Carica il file FXML
                // Imposta la scena caricata come contenuto centrale del BorderPane

            } catch (Exception e) {
                System.out.println("No page found. Please check FXMLLoader.");
                e.printStackTrace();
            }
        }

    }

    //------------------BUTTONS-----------------------

    @FXML
    private void back() { //button per tornare indietro
        System.out.println("Going back");
        if(this.itemScene != null && this.itemScene.equals("user")){
            LoadPage.getPartialScene(fxmlLoader, "chooseTUserAdmin", user);
        }
        else if(this.itemScene != null && this.itemScene.equals("product")){
            LoadPage.getPartialScene(fxmlLoader, "chooseTProductAdmin", user);
        }
        else{
            LoadPage.getPartialScene(fxmlLoader, "homepage", user);
        }
    }

    @FXML
    private void create(ActionEvent event) throws ParseException { //button per creare
        System.out.println("Start creating");
        LoadPage.saveStage(event);
        LoadPage.loadingScene("CREAZIONE IN CORSO...");

        service = new Service();

        if(maskController instanceof UserMaskController){
            UserMaskController userMaskController = (UserMaskController) maskController;
            Cliente u = userMaskController.setValues();
            service.creazioneUtente(u, user);
        }
        else if(maskController instanceof ProductMaskController){
            ProductMaskController productMaskController = (ProductMaskController) maskController;
            Prodotto p = productMaskController.setValues();

            //notizia per la creazione prodotto
            News notiziaCreazione = new News();
            notiziaCreazione.setUtente(user);
            notiziaCreazione.setDataPub(Date.valueOf(LocalDate.now()));
            notiziaCreazione.setDataMod(Date.valueOf(LocalDate.now()));
            notiziaCreazione.setTesto("È stato pubblicato un nuovo prodotto: " + p.getNome() + " a soli " + Utility.formatValueBigDecimal(p.getPrezzo()) + " C. " + p.getDisponibilita().getCode() + " su GeoStore");
            service.creazioneNotiziaSenzaRisposta(notiziaCreazione);

            //crea prodotto
            service.creazioneProdotto(p, user);
        }
        else{
            NewsMaskController newsMaskController = (NewsMaskController) maskController;
            News n = newsMaskController.setValues();
            service.creazioneNotizia(n, user);
        }

    }
}