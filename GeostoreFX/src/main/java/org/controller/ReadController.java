package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controller.item.NewsItemController;
import org.controller.mask.NewsMaskController;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.News;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;

import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class ReadController {// Questo è il BorderPane di menu.fxml

    @FXML
    private Label title;

    @FXML
    private TextField search;

    private Cliente user;
    private Boolean isAdmin;
    private BorderPane fxmlLoader;
    private Service service;

    @FXML
    private VBox itemList;

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

    public void loadItems(String itemScene, String IDkey){
        service = new Service();
        Map<Integer, News> notizie = new HashMap();

        if (IDkey != null && !IDkey.isEmpty() && !IDkey.isBlank()){
            notizie = service.ottieniNotizieByKeyword(IDkey);
            search.setText(IDkey);
        }
        else{
            notizie = service.elencoNotizie();
        }

        itemList.getChildren().clear(); //pulisce prima di aggiungere

        for (News notizia : notizie.values()) {
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/item/" + itemScene + ".fxml"); //trova la scena news
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl);
                HBox newsItem = loader.load();
                NewsItemController newsItemController = loader.getController();
                newsItemController.save(fxmlLoader, user);
                newsItemController.setValues(notizia);
                newsItemController.enableButtons(isAdmin);
                itemList.getChildren().add(newsItem);

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

        LoadPage.getPartialScene(fxmlLoader, "homepage", user);
    }

    @FXML
    private void searching(){ //button per cercare
        System.out.println("Start searching");

        String keyword = search.getText();
        loadItems("newsItem", keyword);

    }
}