package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.models.Amministratore;
import org.models.Cliente;
import org.services.LoadPage;

import java.sql.Date;

public class LoadingController {
    @FXML
    private Label response;


    @FXML
    public void response(String answer) {
        System.out.println(answer);

        response.setText(answer);

    }


}