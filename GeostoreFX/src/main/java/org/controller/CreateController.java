package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controller.masks.*;
import org.models.*;
import org.services.LoadPage;
import org.services.Service;
import org.utility.Utility;

import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;

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
        if(itemScene != null && itemScene.equals("code")){
            title.setText("Creazione codice");
        }
        else if(itemScene != null && itemScene.equals("product")){
            title.setText("Creazione prodotto");
        }
        else if(itemScene != null && itemScene.equals("order")){
            title.setText("Ordinazione prodotto");
        }
        else if(itemScene != null && itemScene.equals("category")){
            title.setText("Creazione categoria");
        }
        else if(itemScene != null && itemScene.equals("material")){
            title.setText("Creazione materia");
        }
        else{
            title.setText("Creazione notizia");
        }
    }

    public void loadMask(String itemScene, String IDOrderKey){ //idOrderKey usato per l'ordinazione prodotto
        this.itemScene = itemScene;

        if(this.itemScene != null && this.itemScene.equals("user")){
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/masks/userMask.fxml");
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
        else if(this.itemScene != null && this.itemScene.equals("code")){
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/masks/codeMask.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl);
                VBox mask = loader.load();
                CodeMaskController codeMaskController = loader.getController();// Ottieni il controller della scena caricata
                maskController = codeMaskController;
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
                URL fileUrl = getClass().getResource("/org/scenes/masks/productMask.fxml");
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
        else if(this.itemScene != null && this.itemScene.equals("order")){
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/masks/orderMask.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl);
                VBox mask = loader.load();
                OrderMaskController orderMaskController = loader.getController();// Ottieni il controller della scena caricata
                orderMaskController.getValuesForOrder(IDOrderKey, user);
                maskController = orderMaskController;
                createMask.getChildren().add(mask);

                // Carica il file FXML
                // Imposta la scena caricata come contenuto centrale del BorderPane

            } catch (Exception e) {
                System.out.println("No page found. Please check FXMLLoader.");
                e.printStackTrace();
            }
        }
        else if(this.itemScene != null && this.itemScene.equals("category")){
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/masks/categoryMask.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl);
                VBox mask = loader.load();
                CategoryMaskController categoryMaskController = loader.getController();// Ottieni il controller della scena caricata
                maskController = categoryMaskController;
                createMask.getChildren().add(mask);

                // Carica il file FXML
                // Imposta la scena caricata come contenuto centrale del BorderPane

            } catch (Exception e) {
                System.out.println("No page found. Please check FXMLLoader.");
                e.printStackTrace();
            }
        }
        else if(this.itemScene != null && this.itemScene.equals("material")){
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/masks/materialMask.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl);
                VBox mask = loader.load();
                MaterialMaskController materialMaskController = loader.getController();// Ottieni il controller della scena caricata
                maskController = materialMaskController;
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
                URL fileUrl = getClass().getResource("/org/scenes/masks/newsMask.fxml");
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
        else if(this.itemScene != null && this.itemScene.equals("code")){
            LoadPage.getPartialScene(fxmlLoader, "chooseTCodeAdmin", user);
        }
        else if(this.itemScene != null && this.itemScene.equals("product")){
            LoadPage.getPartialScene(fxmlLoader, "chooseTProductAdmin", user);
        }
        else if(this.itemScene != null && this.itemScene.equals("order")){
            if(isAdmin){
                LoadPage.getPartialScene(fxmlLoader, "chooseTOrderAdmin", user);
            }
            else{
                LoadPage.getPartialScene(fxmlLoader, "chooseTOrderCliente", user);
            }
        }
        else if(this.itemScene != null && this.itemScene.equals("category")){
            LoadPage.getPartialScene(fxmlLoader, "chooseTCategoryAdmin", user);
        }
        else if(this.itemScene != null && this.itemScene.equals("material")){
            LoadPage.getPartialScene(fxmlLoader, "chooseTMaterialAdmin", user);
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

            //crea prodotto
            service.creazioneProdotto(p, user);
        }
        else if(maskController instanceof OrderMaskController){
            OrderMaskController orderMaskController = (OrderMaskController) maskController;
            Ordine o = orderMaskController.setValues();

            //ordina prodotto
            service.ordinazioneProdotto(o, user);
        }
        else if(maskController instanceof CategoryMaskController){
            CategoryMaskController categoryMaskController = (CategoryMaskController) maskController;
            Categoria c = categoryMaskController.setValues();

            service.creazioneCategoria(c, user);
        }
        else if(maskController instanceof MaterialMaskController){
            MaterialMaskController materialMaskController = (MaterialMaskController) maskController;
            Materia m = materialMaskController.setValues();

            service.creazioneMateria(m, user);
        }
        else{
            NewsMaskController newsMaskController = (NewsMaskController) maskController;
            News n = newsMaskController.setValues();

            service.creazioneNotizia(n, user);
        }

    }
}