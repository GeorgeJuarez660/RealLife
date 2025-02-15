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

public class AnswerController {
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
            case "REG-Y":
                response.setText(resLang.getString("answer.register.positive"));
                break;
            case "LOG-Y":
                response.setText(resLang.getString("answer.login.positive"));
                break;
            case "LOG-N":
                response.setText(resLang.getString("answer.login.negative"));
                break;
            case "REG-N":
                response.setText(resLang.getString("answer.register.negative"));
                break;
            case "BD-ERR":
                response.setText(resLang.getString("answer.register.errorBornDate"));
                break;
            case "PWD-NOMATCH":
                response.setText(resLang.getString("answer.register.differentPasswords"));
                break;
            case "AGE-ERR":
                response.setText(resLang.getString("answer.register.wrongAge"));
                break;
            default:
                break;
        }
    }

}