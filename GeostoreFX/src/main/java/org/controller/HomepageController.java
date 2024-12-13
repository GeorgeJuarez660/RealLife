package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.models.*;
import org.services.LoadPage;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomepageController {

    @FXML
    private Label title;

    @FXML
    private Button createNews;

    private Cliente user;
    private Boolean isAdmin;
    private BorderPane fxmlLoader;
    private NewsRepository newsRep;

    @FXML
    private VBox newsList;


    public void save(BorderPane fxmlLoader, Cliente utente){
        user = new Cliente();
        user.setNome(utente.getNome());
        user.setCognome(utente.getCognome());
        user.setSesso(utente.getSesso());
        user.setDataNascita(utente.getDataNascita());
        user.setEmail(utente.getEmail());
        user.setPassword(utente.getPassword());

        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) user;
            isAdmin = admin.getCodeAdmin() != null && !admin.getCodeAdmin().isEmpty() && !admin.getCodeAdmin().isBlank();
        }
        else{
            isAdmin = false;
        }

        this.fxmlLoader = fxmlLoader;
    }

    @FXML
    public void title() {
        System.out.println("homepage");

        String startMorning = "06:00";
        String endMorning = "12:00";
        String startAfternoon = "12:00";
        String endAfternoon = "17:00";
        String startEvening = "17:00";
        String endEvening = "23:00";
        String startNight = "23:00";
        String endNight = "06:00";

        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formatTime = currentTime.format(formatter);

        String greetings = "";

        if(formatTime.compareTo(startMorning) >= 0 && formatTime.compareTo(endMorning) < 0){
            greetings = "Buongiorno ";
        }
        else if(formatTime.compareTo(startAfternoon) >= 0 && formatTime.compareTo(endAfternoon) < 0){
            greetings = "Buon pomeriggio ";
        }
        else if(formatTime.compareTo(startEvening) >= 0 && formatTime.compareTo(endEvening) < 0){
            greetings = "Buonasera ";
        }
        else if(formatTime.compareTo(startNight) >= 0 || formatTime.compareTo(endNight) < 0){
            greetings = "Buona permanenza notturna ";
        }
        greetings += user.getNome();
        title.setText(greetings);

        //LoadPage.access("register");
    }

    public void showButtonCreateNews(){
        createNews.setVisible(isAdmin); //rende invisibile
        createNews.setManaged(isAdmin); //ignora la presenza
    }

    public void loadNews(){
        newsRep = new NewsRepository();
        Map<Integer, News> notizie = new HashMap();

        notizie = newsRep.getNotizieWithDB();

        for (News value : notizie.values()) {
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/newsInfoGets.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl);
                HBox productItem = loader.load();
                NewsInfoController newsInfoController = loader.getController();
                newsInfoController.setValues(value);
                newsInfoController.enableButtons(isAdmin);
                newsList.getChildren().add(productItem);

                // Carica il file FXML
                // Imposta la scena caricata come contenuto centrale del BorderPane

            } catch (Exception e) {
                System.out.println("No page found. Please check FXMLLoader.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadInfo(){
        System.out.println("goes to info");
        LoadPage.getPartialScene(fxmlLoader, "info", user, isAdmin);
    }




    /*@FXML
    private void caricaScene2() {
        Pane scene2 = getPage("searchUsers.fxml"); // Usa il metodo generico per caricare la scena
        if (scene2 != null) {
            root.setCenter(scene2); // Imposta la scena nel centro del BorderPane
        }
    }*/
}