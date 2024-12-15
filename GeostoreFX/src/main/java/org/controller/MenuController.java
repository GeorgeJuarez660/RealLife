package org.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.Utente;
import org.services.LoadPage;
import org.sqlite.util.StringUtils;

public class MenuController {
    @FXML
    private BorderPane fxmlLoader; // Questo è il BorderPane di menu.fxml

    @FXML
    private Label name, surname;

    @FXML
    private Cliente user;

    @FXML
    private Boolean isAdmin;

    //------------------INIZIALIZE-----------------------

    //salvataggio utente per il menu
    public void saveUser(Cliente utente) {

        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) utente;
            user = admin;
            isAdmin = admin.getCodeAdmin() != null && !admin.getCodeAdmin().isEmpty() && !admin.getCodeAdmin().isBlank();
        }
        else{
            user = utente;
            isAdmin = false;
        }

        setFields();
    }

    private void setFields() {
        name.setText(user.getNome().toUpperCase());
        surname.setText(user.getCognome().toUpperCase());
    }


    public void loadHomepage() {
        System.out.println("goes to homepage");
        LoadPage.getPartialScene(fxmlLoader, "homepage", user);
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void logout(ActionEvent event){
        LoadPage.saveStage(event);

        LoadPage.answerScene("positive", "LOGOUT EFFETTUATO CON SUCCESSO");

        //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(evt -> {
            // Dopo 2 secondi, carica la terza scena
            LoadPage.getFullScene("prepage");
        });
        delay.play();

    }


    @FXML
    public void initialize() {
        System.out.println("Root inizializzato: " + fxmlLoader);
    }

    // Metodo per cambiare il testo di titleGets

    /*@FXML
    private void caricaScene2() {
        Pane scene2 = getPage("searchUsers.fxml"); // Usa il metodo generico per caricare la scena
        if (scene2 != null) {
            root.setCenter(scene2); // Imposta la scena nel centro del BorderPane
        }
    }*/
}