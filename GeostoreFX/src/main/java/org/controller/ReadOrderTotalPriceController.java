package org.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controller.item.OrderTotalPriceItemController;
import org.controller.item.UserItemController;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.Ordine;
import org.models.Utente;
import org.services.LoadPage;
import org.services.Service;

import java.net.URL;
import java.sql.Date;
import java.util.Calendar;

public class ReadOrderTotalPriceController {// Questo è il BorderPane di menu.fxml

    @FXML
    private Label title;

    @FXML
    private Button re_search;


    private Cliente user;
    private Boolean isAdmin;
    private BorderPane fxmlLoader;
    private Service service;

    @FXML
    private HBox item;

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

    public void setTitle(String value, String choosedDate) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(Date.valueOf(choosedDate));
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        value = value + " " + giorno + "/" + mese + "/" + anno;

        title.setText(value);
    }

    public void loadItem(String itemScene, String chooseDate){
        service = new Service();
        Ordine ordine;

        ordine = service.ordiniTotaliGiornalieri(user, chooseDate);

        if(ordine.getUtente() != null){
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/item/" + itemScene + ".fxml"); //trova la scena news
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl);
                VBox userProfileItem = loader.load();
                OrderTotalPriceItemController orderTotalPriceItemController = loader.getController();
                orderTotalPriceItemController.setValues(ordine);
                item.getChildren().add(userProfileItem);

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
    private void research() { //button per scegliere il giorno
        System.out.println("Going back");

        LoadPage.getPartialScene(fxmlLoader, "orderTotalPriceChooseDate", user);
    }
}