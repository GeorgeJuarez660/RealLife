package org.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.models.Amministratore;
import org.models.Cliente;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;

public class ChooseTCodeController {
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
    private void back() { //button per tornare indietro
        System.out.println("goes to user");
        LoadPage.getPartialScene(fxmlLoader, "chooseTUserAdmin", user);
    }

    @FXML
    private void lookAssociatedCodes() {
        System.out.println("goes to look associated codes");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("read");
        partialSceneDTO.setItemScene("code-A");
        partialSceneDTO.setUser(user);
        LoadPage.getPartialSceneCRU(partialSceneDTO, null);
    }

    @FXML
    private void lookCodes() {
        System.out.println("goes to look codes");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("read");
        partialSceneDTO.setItemScene("code");
        partialSceneDTO.setUser(user);
        LoadPage.getPartialSceneCRU(partialSceneDTO, null);
    }

    @FXML
    private void createCode() {
        System.out.println("goes to create code");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("create");
        partialSceneDTO.setItemScene("code");
        partialSceneDTO.setUser(user);
        LoadPage.getPartialSceneCRU(partialSceneDTO, null);
    }

}