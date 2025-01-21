package org.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import org.models.Amministratore;
import org.models.Cliente;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderTotalPriceController {

    @FXML
    private DatePicker date;

    private Cliente user;
    private Boolean isAdmin;
    private BorderPane fxmlLoader;
    private Service service;

    //------------------INITIALIZE-----------------------

    public void save(BorderPane fxmlLoader, Cliente utente){

        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) utente;
            user = admin;
            isAdmin = admin.getCodeAdmin() != null && !admin.getCodeAdmin().isEmpty() && !admin.getCodeAdmin().isBlank() &&
                    (admin.getCodeAdmin().contains("A")
                    || admin.getCodeAdmin().contains("Q")
                    || admin.getCodeAdmin().contains("O"));
        }
        else{
            user = utente;
            isAdmin = false;
        }

        this.fxmlLoader = fxmlLoader;
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void back() { //button per tornare indietro
        System.out.println("Going back");
        if(isAdmin){
            LoadPage.getPartialScene(fxmlLoader, "chooseTOrderAdmin", user);
        }
        else{
            LoadPage.getPartialScene(fxmlLoader, "chooseTOrderCliente", user);
        }
    }


    @FXML
    private void calculate() {
        System.out.println("goes to look order total price");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("readOrderTotalPrice");
        partialSceneDTO.setUser(user);

        String choosedDate = String.valueOf(date.getValue());

        if(choosedDate != null && !choosedDate.isEmpty() && !choosedDate.isBlank() && !choosedDate.equals("null")){
            LoadPage.getPartialSceneCRU(partialSceneDTO, choosedDate);
        }
    }
}