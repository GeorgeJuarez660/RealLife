package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.models.Amministratore;
import org.models.Cliente;
import org.services.LoadPage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class InfoController {

    @FXML
    private BorderPane fxmlLoader;

    @FXML
    private Label version;

    @FXML
    private Cliente user;

    @FXML
    private Boolean isAdmin;

    public void save(BorderPane fxmlLoader, Cliente utente){
        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) utente;
            user = admin;
            isAdmin = admin.getCodeAdmin() != null && !admin.getCodeAdmin().isEmpty() && !admin.getCodeAdmin().isBlank();
        }
        else{
            user = utente;
            isAdmin = false;
        }

        this.fxmlLoader = fxmlLoader;
    }

    @FXML
    public void setVersion(String value) {
        System.out.println("info");

        version.setText(value);
    }

    @FXML
    private void back() {
        System.out.println("Going back");

        LoadPage.getPartialScene(fxmlLoader, "homepage", user, isAdmin);
    }
}