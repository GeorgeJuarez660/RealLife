package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.services.LoadPage;

public class WelcomeController {
    @FXML
    private BorderPane welcomeLoader;

    @FXML
    private static Stage stage;// Questo è il BorderPane di menu.fxml

    //------------------BUTTONS-----------------------
    @FXML
    private void goesToPrepage(ActionEvent event) {
        System.out.println("Clicked");
        LoadPage.saveStage(event);

        LoadPage.getFullScene("prepage");
    }


    @FXML
    public void initialize() {
        System.out.println("Root inizializzato: " + welcomeLoader);
    }
}