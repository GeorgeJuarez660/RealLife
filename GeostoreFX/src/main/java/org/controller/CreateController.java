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
    private NewsRepository nRe;

    private Object maskController;


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

    @FXML
    private void back() {
        System.out.println("Going back");

        LoadPage.getPartialScene(fxmlLoader, "homepage", user, isAdmin);
    }

    @FXML
    private void create(ActionEvent event) throws ParseException {
        System.out.println("Start creating");
        LoadPage.saveStage(event);

        NewsMaskController newsMaskController = (NewsMaskController) maskController;
        News n = newsMaskController.setValues();
        insertNews(event, n);
        //LoadPage.getPartialScene(fxmlLoader, "homepage", user, isAdmin);
    }

    private void insertNews(ActionEvent event, News news) {
        System.out.println("Creating news");
        LoadPage.saveStage(event);
        LoadPage.loadingScene("CREAZIONE IN CORSO...");

        news.setUtente(user);

        nRe = new NewsRepository();
        int num = 0;

        num = nRe.insertNotizieWithDB(news.getDataPub(), news.getDataMod(), news.getTesto(), news.getUtente().getId());

        if(num > 1){
            Utility.sendResponse(num, "NEWS CREATO", user);
        }
        else{
            Utility.sendResponse(num, "CREAZIONE NEWS", user);

        }

        /*{
            LoadPage.answerScene("negative", "LE PASSWORD NON COINCIDONO");
            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(e -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.getFullScene("register");
            });
            delay.play();
        }*/
    }
}