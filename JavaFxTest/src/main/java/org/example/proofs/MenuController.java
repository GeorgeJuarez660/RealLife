package org.example.proofs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController {
    @FXML
    private BorderPane fxmlLoader; // Questo è il BorderPane di menu.fxml

    @FXML
    private Label name, surname;

    @FXML
    private void clicking() {
        System.out.println("Clicked");
        MenuLoadPage.getScene(fxmlLoader, "gets");
    }

    @FXML
    public void setFields() {
        name.setText("GIORGIO");
        surname.setText("NOCERINO");
    }


    @FXML
    private void user_clicking() {
        System.out.println("Clicked");
        MenuLoadPage.getScene(fxmlLoader, "chooseTUserAdmin");
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