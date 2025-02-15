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
import java.util.ResourceBundle;

public class LoadingController {
    @FXML
    private Label response;

    //------------------INITIALIZE-----------------------

    public void response(String answer) {
        System.out.println(answer);

        response.setText(answer);

    }

    public void responseWithLang(String answer, ResourceBundle resLang) {
        System.out.println(answer);

        switch (answer) {
            case "LOAD-REG":
                response.setText(resLang.getString("loading.register"));
                break;
            case "LOAD-LOG":
                response.setText(resLang.getString("loading.login"));
                break;
            case "LOAD-CHL":
                response.setText(resLang.getString("loading.language"));
                break;
            default:
                break;
        }
    }


}