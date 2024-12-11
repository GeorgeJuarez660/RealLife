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
        user = new Cliente();
        user.setNome(utente.getNome());
        user.setCognome(utente.getCognome());
        user.setSesso(utente.getSesso());
        user.setDataNascita(utente.getDataNascita());
        user.setEmail(utente.getEmail());
        user.setPassword(utente.getPassword());

        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) user;
            isAdmin = admin.getCodeAdmin() != null && !admin.getCodeAdmin().isEmpty() && !admin.getCodeAdmin().isBlank();
        }
        else{
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