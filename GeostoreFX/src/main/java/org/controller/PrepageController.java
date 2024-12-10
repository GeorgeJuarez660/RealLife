package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.services.LoadPage;

public class PrepageController {

    @FXML
    private void register(ActionEvent event) {
        System.out.println("signing up");
        LoadPage.saveStage(event);

        LoadPage.access("register");
    }

    @FXML
    private void loginAdmin(ActionEvent event) {
        System.out.println("signing in admin");
        LoadPage.saveStage(event);

        LoadPage.access("Admin");
    }

    @FXML
    private void loginCliente(ActionEvent event) {
        System.out.println("signing in cliente");
        LoadPage.saveStage(event);

        LoadPage.access("User");
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