package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.services.LoadPage;

public class WelcomeController {

    //------------------BUTTONS-----------------------
    @FXML
    private void goesToPrepage(ActionEvent event) {
        System.out.println("goes to prepage");
        LoadPage.saveStage(event);

        LoadPage.getFullScene("prepage");
    }


    @FXML
    public void initialize() {
        System.out.println("Welcome");
    }
}