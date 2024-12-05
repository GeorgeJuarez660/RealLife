package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.services.LoadPage;

public class PrepageController {

    @FXML
    private Button admin, cliente;

    @FXML
    private void register(ActionEvent event) {
        System.out.println("signing up");
        LoadPage.access(event, "register");
    }

    @FXML
    private void loginAdmin(ActionEvent event) {
        System.out.println("signing in admin");
        LoadPage.access(event, "Admin");
    }

    @FXML
    private void loginCliente(ActionEvent event) {
        System.out.println("signing in cliente");
        LoadPage.access(event, "User");
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