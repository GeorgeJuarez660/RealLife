package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.services.MenuLoadPage;

public class WelcomeController {
    @FXML
    private BorderPane welcomeLoader;

    @FXML
    private static Stage stage;// Questo è il BorderPane di menu.fxml

    @FXML
    private void goes(ActionEvent event) {
        System.out.println("Clicked");
        MenuLoadPage.getFullScene(event, "menu");
    }


    @FXML
    public void initialize() {
        System.out.println("Root inizializzato: " + welcomeLoader);
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