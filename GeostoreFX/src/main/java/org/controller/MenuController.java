package org.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.Utente;
import org.services.LoadPage;
import org.sqlite.util.StringUtils;
import org.utility.PartialSceneDTO;

public class MenuController {
    @FXML
    private BorderPane fxmlLoader; // Questo è il BorderPane di menu.fxml

    @FXML
    private Label name, surname;

    @FXML
    private Cliente user;

    @FXML
    private String isAdmin;

    //------------------INITIALIZE-----------------------

    //salvataggio utente per il menu
    public void saveUser(Cliente utente) {

        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) utente;
            user = admin;
            isAdmin = admin.getCodeAdmin();
        }
        else{
            user = utente;
            isAdmin = null;
        }

        setFields();
    }

    private void setFields() {
        name.setText(user.getNome().toUpperCase());
        surname.setText(user.getCognome().toUpperCase());
    }


    public void loadHomepage() {
        System.out.println("goes to homepage");
        LoadPage.getPartialScene(fxmlLoader, "homepage", user);
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void logout(ActionEvent event){
        LoadPage.saveStage(event);

        LoadPage.answerScene("positive", "LOGOUT EFFETTUATO CON SUCCESSO");

        //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(evt -> {
            // Dopo 2 secondi, carica la terza scena
            LoadPage.getFullScene("prepage");
        });
        delay.play();

    }

    @FXML
    private void user(){
        if(isAdmin != null && (isAdmin.contains("A") || isAdmin.contains("U") || isAdmin.contains("N"))){
            System.out.println("goes to user");
            LoadPage.getPartialScene(fxmlLoader, "chooseTUserAdmin", user);
        }
        else{
            System.out.println("goes to user");
            PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
            partialSceneDTO.setFxmlLoader(fxmlLoader);
            partialSceneDTO.setInnerScene("readProfileUser");
            partialSceneDTO.setUser(user);
            LoadPage.getPartialSceneCRU(partialSceneDTO, null);
        }
    }

    @FXML
    private void product(){
        if(isAdmin != null && (isAdmin.contains("A") || isAdmin.contains("P") || isAdmin.contains("Q"))){
            System.out.println("goes to product");
            LoadPage.getPartialScene(fxmlLoader, "chooseTProductAdmin", user);
        }
        else{
            System.out.println("goes to product");
            LoadPage.getPartialScene(fxmlLoader, "chooseTProductCliente", user);
        }
    }

    @FXML
    private void order(){
        if(isAdmin != null && (isAdmin.contains("A") || isAdmin.contains("Q") || isAdmin.contains("O"))){
            System.out.println("goes to order");
            LoadPage.getPartialScene(fxmlLoader, "chooseTOrderAdmin", user);
        }
        else{
            System.out.println("goes to order");
            LoadPage.getPartialScene(fxmlLoader, "chooseTOrderCliente", user);
        }
    }

    @FXML
    private void category(){
        if(isAdmin != null && (isAdmin.contains("A") || isAdmin.contains("P") || isAdmin.contains("Q"))){
            System.out.println("goes to category");
            LoadPage.getPartialScene(fxmlLoader, "chooseTCategoryAdmin", user);
        }
        else{
            System.out.println("goes to category");
            PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
            partialSceneDTO.setFxmlLoader(fxmlLoader);
            partialSceneDTO.setInnerScene("readProductType");
            partialSceneDTO.setItemScene("category");
            partialSceneDTO.setUser(user);
            LoadPage.getPartialSceneCRU(partialSceneDTO, null);
        }
    }

    @FXML
    private void material(){
        if(isAdmin != null && (isAdmin.contains("A") || isAdmin.contains("P") || isAdmin.contains("Q"))){
            System.out.println("goes to material");
            LoadPage.getPartialScene(fxmlLoader, "chooseTMaterialAdmin", user);
        }
        else{
            System.out.println("goes to material");
            PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
            partialSceneDTO.setFxmlLoader(fxmlLoader);
            partialSceneDTO.setInnerScene("readProductType");
            partialSceneDTO.setItemScene("material");
            partialSceneDTO.setUser(user);
            LoadPage.getPartialSceneCRU(partialSceneDTO, null);
        }
    }

    @FXML
    public void initialize() {
        System.out.println("Root inizializzato: " + fxmlLoader);
    }

}