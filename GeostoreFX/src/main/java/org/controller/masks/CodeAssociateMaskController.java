package org.controller.masks;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.models.*;
import org.services.Service;

import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CodeAssociateMaskController implements Initializable {

    @FXML
    private ChoiceBox<String> adminCode, userEmail;

    private Service service;
    private String IDkey; //usato per la ricerca/modifica/rimozione

    //------------------INITIALIZE-----------------------

    //per l'associazione /modifica prodotto
    public void setUserEmail(){
        service = new Service();
        Map<Integer, Utente> users = new HashMap<>();
        users = service.elencoUtenti();

        for(Utente utente : users.values()){
            Cliente cliente = (Cliente) utente;
            userEmail.getItems().add(cliente.getId() + " - " + cliente.getEmail());
        }
    }

    public void setAdminCode(){
        service = new Service();
        Map<Integer, Codice> codes = new HashMap<>();
        codes = service.elencoCodici();

        for(Codice codice : codes.values()){
            adminCode.getItems().add(codice.getId() + " - " + codice.getCodice());
        }
    }
    //per la modifica codice associato
    public void getValues(String IDkey){

        service = new Service();
        CodiceAssociateDTO codiceAssociate;
        codiceAssociate = service.ottieniCodiceAssociato(IDkey);

        adminCode.setValue(codiceAssociate.getIdCodice() + " - " + codiceAssociate.getCodiceAdmin());
        userEmail.setValue(codiceAssociate.getIdUtente() + " - " + codiceAssociate.getEmailUtente());

        this.IDkey = IDkey;
    }

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per l'associazione codice
    public CodiceAssociateDTO setValues() throws ParseException { //recuperato da mask
        CodiceAssociateDTO associateDTO = new CodiceAssociateDTO();
        service = new Service();

        associateDTO.setIdCodice(Integer.parseInt(adminCode.getValue().substring(0, adminCode.getValue().indexOf(" - "))));
        associateDTO.setCodiceAdmin(adminCode.getValue().substring(adminCode.getValue().indexOf(" - ") + 3));

        associateDTO.setIdUtente(Integer.parseInt(userEmail.getValue().substring(0, userEmail.getValue().indexOf(" - "))));
        associateDTO.setEmailUtente(userEmail.getValue().substring(userEmail.getValue().indexOf(" - ") + 3));

        return associateDTO;
    }

    //per la modifica codice
    public CodiceAssociateDTO setValuesWithID() throws ParseException { //recuperato da mask
        CodiceAssociateDTO associateDTO = new CodiceAssociateDTO();
        service = new Service();

        associateDTO.setKey(IDkey);
        associateDTO.setIdCodice(Integer.parseInt(adminCode.getValue().substring(0, adminCode.getValue().indexOf(" - "))));
        associateDTO.setCodiceAdmin(adminCode.getValue().substring(adminCode.getValue().indexOf(" - ") + 3));

        associateDTO.setIdUtente(Integer.parseInt(userEmail.getValue().substring(0, userEmail.getValue().indexOf(" - "))));
        associateDTO.setEmailUtente(userEmail.getValue().substring(userEmail.getValue().indexOf(" - ") + 3));

        return associateDTO;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}