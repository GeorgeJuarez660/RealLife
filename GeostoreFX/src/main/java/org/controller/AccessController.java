package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.services.LoadPage;
import org.services.User;

public class AccessController {
    @FXML
    private TextField name, surname, sex, bornDate, phoneNumber, address, email, codeAdmin;

    @FXML
    private PasswordField password, confirmPassword;


    @FXML
    private void signup(ActionEvent event) {
        System.out.println("Signing up");

        User user = new User();

        user.setName(name.getText());
        user.setSurname(surname.getText());
        user.setSex(sex.getText());
        user.setBornDate(bornDate.getText());
        user.setPhoneNumber(phoneNumber.getText());
        user.setAddress(address.getText());
        user.setEmail(email.getText());
        user.setCodiceAdmin(codeAdmin.getText());



        LoadPage.getFullScene(event, "prepage");
    }

    @FXML
    private void back(ActionEvent event) {
        System.out.println("Going back");

        LoadPage.getFullScene(event, "prepage");
    }


}