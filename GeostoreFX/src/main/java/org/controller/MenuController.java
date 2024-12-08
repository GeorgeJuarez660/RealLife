package org.controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import org.models.Cliente;
import org.models.Utente;
import org.services.LoadPage;

public class MenuController {
    @FXML
    private BorderPane fxmlLoader; // Questo è il BorderPane di menu.fxml

    @FXML
    private Label name, surname;

    @FXML
    private Cliente user;

    @FXML
    private void clicking() {
        System.out.println("Clicked");
        LoadPage.getPartialScene(fxmlLoader, "gets");
    }

    @FXML //salvataggio utente per il menu
    public void saveUser(Cliente utente) {
        user = new Cliente();
        user.setNome(utente.getNome());
        user.setCognome(utente.getCognome());
        user.setSesso(utente.getSesso());
        user.setDataNascita(utente.getDataNascita());
        user.setEmail(utente.getEmail());
        user.setPassword(utente.getPassword());

        setFields();
    }

    @FXML
    private void setFields() {
        name.setText(user.getNome());
        surname.setText(user.getCognome());
    }


    @FXML
    private void user_clicking() {
        System.out.println("Clicked");
        LoadPage.getPartialScene(fxmlLoader, "chooseTUserAdmin");
    }

    @FXML
    private void logout(){

        LoadPage.answerScene("positive", "LOGOUT EFFETTUATO CON SUCCESSO");

        //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> {
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