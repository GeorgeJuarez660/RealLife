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
import org.controller.item.UserItemController;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.News;
import org.models.Utente;
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

        if (IDkey != null && !IDkey.isEmpty() && !IDkey.isBlank()){
            if(this.itemScene != null && this.itemScene.equals("user")){ //vede prima quale item riferisce
                utenti = service.ottieniUtente(Integer.parseInt(IDkey));
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
            else{
                notizie = service.elencoNotizie();
            }
        }

        itemList.getChildren().clear(); //pulisce prima di aggiungere

        if(this.itemScene != null && this.itemScene.equals("user")){
            for (Utente utente : utenti.values()) {
                try {
                    // Costruisce il percorso completo del file FXML
                    URL fileUrl = getClass().getResource("/org/scenes/item/userItem.fxml"); //trova la scena news
                    if (fileUrl == null) {
                        throw new java.io.FileNotFoundException("FXML file can't be found");
                    }

                    FXMLLoader loader = new FXMLLoader(fileUrl);
                    HBox newsItem = loader.load();
                    UserItemController userItemController = loader.getController();
                    userItemController.save(fxmlLoader, user);
                    userItemController.setValues(utente);
                    userItemController.enableButtons(isAdmin);
                    itemList.getChildren().add(newsItem);

                    // Carica il file FXML
                    // Imposta la scena caricata come contenuto centrale del BorderPane

                } catch (Exception e) {
                    System.out.println("No page found. Please check FXMLLoader.");
                    e.printStackTrace();
                }
            }
        }
        else{
            for (News notizia : notizie) {
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

    //------------------BUTTONS-----------------------

    @FXML
    private void back() { //button per tornare indietro
        System.out.println("Going back");
        if(this.itemScene != null && this.itemScene.equals("user")){
            LoadPage.getPartialScene(fxmlLoader, "chooseTUserAdmin", user);
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