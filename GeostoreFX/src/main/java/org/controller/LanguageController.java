package org.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.services.LoadPage;

public class LanguageController {

    //------------------BUTTONS-----------------------
    @FXML
    private void back(ActionEvent event) {
        System.out.println("Going back");

        LoadPage.getFullScene("welcome");
    }


    @FXML
    public void initialize() {
        System.out.println("Welcome");
    }
}