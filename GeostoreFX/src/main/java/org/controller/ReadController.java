package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controller.item.NewsItemController;
import org.controller.item.ProductItemController;
import org.controller.item.UserItemController;
import org.models.*;
import org.services.LoadPage;
import org.services.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadController {// Questo è il BorderPane di menu.fxml

    @FXML
    private Label title;

    @FXML
    private TextField search;

    @FXML
    private Button backBtn;

    private Cliente user;
    private Boolean isAdmin;
    private BorderPane fxmlLoader;
    private Service service;
    private String itemScene;
    private Boolean showSearch;

    @FXML
    private VBox itemList;

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
            title.setText("Elenco profili utente");
        }
        else if(itemScene != null && itemScene.contains("product")){
            String lastChar = itemScene.substring(itemScene.length() - 1);
            if(lastChar.equals("1")){
                title.setText("Elenco prodotti in generale");
            }
            else{
                title.setText("Elenco prodotti disponibili");
            }
        }
        else{
            title.setText("Elenco notizie");
        }
    }

    public void showSearchBar(Boolean show){
        search.setVisible(show);
        search.setManaged(show);
        showSearch = show;
    }

    public void loadItems(String itemScene, String IDkey){
        service = new Service();
        this.itemScene = itemScene;
        List<News> notizie = new ArrayList<>();
        Map<Integer, Utente> utenti = new HashMap<>();
        Map<Integer, Prodotto> prodotti = new HashMap<>();

        if (IDkey != null && !IDkey.isEmpty() && !IDkey.isBlank()){
            if(this.itemScene != null && this.itemScene.equals("user")){ //vede prima quale item riferisce
                utenti = service.ottieniUtente(Integer.parseInt(IDkey));
            }
            else if(this.itemScene != null && this.itemScene.contains("product")){
                String lastChar = this.itemScene.substring(this.itemScene.length() - 1);
                if(lastChar.equals("1")){
                    prodotti = service.ottieniProdotto(Integer.parseInt(IDkey));
                }
                else{
                    prodotti = service.ottieniProdottoDisponibile(Integer.parseInt(IDkey));
                }
            }
            else{
                notizie = service.ottieniNotizieByKeyword(IDkey);
            }
            search.setText(IDkey);
        }
        else{
            if(this.itemScene != null && this.itemScene.equals("user")) { //vede prima quale item riferisce
                utenti = service.elencoUtenti();
            }
            else if(this.itemScene != null && this.itemScene.contains("product")){
                String lastChar = this.itemScene.substring(this.itemScene.length() - 1);
                if(lastChar.equals("1")){
                    prodotti = service.elencoProdotti();
                }
                else{
                    prodotti = service.elencoProdottiDisponibili();
                }
            }
            else{
                notizie = service.elencoNotizie();
            }
        }

        itemList.getChildren().clear(); //pulisce prima di aggiungere

        if(this.itemScene != null && this.itemScene.equals("user")){
            for (Utente utente : utenti.values()) {
                if(utente.getId() != null && utente.getId() != 0){
                    try {
                        // Costruisce il percorso completo del file FXML
                        URL fileUrl = getClass().getResource("/org/scenes/item/userItem.fxml"); //trova la scena news
                        if (fileUrl == null) {
                            throw new java.io.FileNotFoundException("FXML file can't be found");
                        }

                        FXMLLoader loader = new FXMLLoader(fileUrl);
                        HBox userItem = loader.load();
                        UserItemController userItemController = loader.getController();
                        userItemController.save(fxmlLoader, user);
                        userItemController.setValues(utente);
                        userItemController.enableButtons(isAdmin);
                        itemList.getChildren().add(userItem);

                        // Carica il file FXML
                        // Imposta la scena caricata come contenuto centrale del BorderPane

                    } catch (Exception e) {
                        System.out.println("No page found. Please check FXMLLoader.");
                        e.printStackTrace();
                    }
                }
            }
        }
        else if(this.itemScene != null && this.itemScene.contains("product")){
            for (Prodotto prodotto : prodotti.values()) {
                if(prodotto.getId() != null && prodotto.getId() != 0){
                    try {
                        // Costruisce il percorso completo del file FXML
                        URL fileUrl = getClass().getResource("/org/scenes/item/productItem.fxml"); //trova la scena news
                        if (fileUrl == null) {
                            throw new java.io.FileNotFoundException("FXML file can't be found");
                        }

                        FXMLLoader loader = new FXMLLoader(fileUrl);
                        HBox productItem = loader.load();
                        ProductItemController productItemController = loader.getController();
                        productItemController.save(fxmlLoader, user);
                        productItemController.setValues(prodotto);
                        productItemController.enableButtons(isAdmin);
                        itemList.getChildren().add(productItem);

                        // Carica il file FXML
                        // Imposta la scena caricata come contenuto centrale del BorderPane

                    } catch (Exception e) {
                        System.out.println("No page found. Please check FXMLLoader.");
                        e.printStackTrace();
                    }
                }
            }
        }
        else{
            for (News notizia : notizie) {
                if(notizia.getId() != null && notizia.getId() != 0){
                    try {
                        // Costruisce il percorso completo del file FXML
                        URL fileUrl = getClass().getResource("/org/scenes/item/newsItem.fxml"); //trova la scena news
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
        }

    }

    //------------------BUTTONS-----------------------

    @FXML
    private void back() { //button per tornare indietro
        System.out.println("Going back");
        if(this.itemScene != null && this.itemScene.equals("user")){
            LoadPage.getPartialScene(fxmlLoader, "chooseTUserAdmin", user);
        }
        else if(this.itemScene != null && this.itemScene.contains("product")){
            if(isAdmin){
                LoadPage.getPartialScene(fxmlLoader, "chooseTProductAdmin", user);
            }
            else{
                LoadPage.getPartialScene(fxmlLoader, "chooseTProductCliente", user);
            }
        }
        else{
            LoadPage.getPartialScene(fxmlLoader, "homepage", user);
        }
    }

    @FXML
    private void searching(){ //button per cercare
        System.out.println("Start searching");

        if(showSearch){
            if(this.itemScene != null && this.itemScene.equals("user")){
                String keyword = search.getText();
                loadItems("user", keyword);
            }
            else if(this.itemScene != null && this.itemScene.equals("product-1")){
                String keyword = search.getText();
                loadItems("product-1", keyword);
            }
            else if(this.itemScene != null && this.itemScene.equals("product-2")){
                String keyword = search.getText();
                loadItems("product-2", keyword);
            }
            else{
                String keyword = search.getText();
                loadItems("news", keyword);
            }
        }
        else{
            showSearchBar(true);
        }
    }
}