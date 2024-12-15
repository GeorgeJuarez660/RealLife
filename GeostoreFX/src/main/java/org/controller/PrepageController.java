package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.services.LoadPage;

public class PrepageController {

    //------------------BUTTONS-----------------------

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

}