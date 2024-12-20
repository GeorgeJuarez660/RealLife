package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.models.Amministratore;
import org.models.Cliente;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;

public class ChooseTUserAdminController {
    private Cliente user;
    private Boolean isAdmin;
    private BorderPane fxmlLoader;
    private Service service;

    //------------------INITIALIZE-----------------------

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

    //------------------BUTTONS-----------------------

    @FXML
    private void lookUserProfile() {
        System.out.println("goes to look user profile");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("readSolo");
        partialSceneDTO.setUser(user);
        LoadPage.getPartialSceneCRU(partialSceneDTO, null);
    }

    @FXML
    private void lookUsers() {
        System.out.println("goes to look user profile");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("read");
        partialSceneDTO.setItemScene("user");
        partialSceneDTO.setUser(user);
        LoadPage.getPartialSceneCRU(partialSceneDTO, null);
    }

}