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

    //per la creazione/modifica prodotto
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
    //per la modifica prodotto
    /*public void getValues(String IDkey){

        service = new Service();
        CodiceAssociateDTO codiceAssociate;
        codiceAssociate = service.ottieniCodiceAssociato(IDkey);

        name.setText(prodotto.getNome());
        price.setText(Utility.formatValueBigDecimal(prodotto.getPrezzo()));
        available.setValue(prodotto.getDisponibilita().getId() + " - " + prodotto.getDisponibilita().getCode());
        category.setValue(prodotto.getCategoria().getId() + " - " + prodotto.getCategoria().getNome());
        material.setValue(prodotto.getMateria().getId() + " - " + prodotto.getMateria().getNome());
        quantity.setText(prodotto.getQuantita_disp().toString());

        this.IDkey = IDkey;
    }*/

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per la creazione codice
    public CodiceAssociateDTO setValues() throws ParseException { //recuperato da mask
        CodiceAssociateDTO associateDTO = new CodiceAssociateDTO();
        service = new Service();

        Codice codice = new Codice();
        codice.setId(Integer.parseInt(adminCode.getValue().substring(0, adminCode.getValue().indexOf(" - "))));
        codice.setCodice(adminCode.getValue().substring(adminCode.getValue().indexOf(" - ") + 3));
        associateDTO.setCodiceAdmin(codice);

        associateDTO.setEmailUtente(userEmail.getValue().substring(userEmail.getValue().indexOf(" - ") + 3));

        return associateDTO;
    }

    //per la modifica codice
    /*public Prodotto setValuesWithID() throws ParseException { //recuperato da mask
        Prodotto prodotto = new Prodotto();
        service = new Service();

        prodotto.setId(Integer.parseInt(IDkey));
        prodotto.setNome(name.getText());

        if(price != null && price.getText() != null && !price.getText().isEmpty() && !price.getText().isBlank()) {
            prodotto.setPrezzo(Utility.formatValueString(price.getText()));
        }
        else{
            prodotto.setPrezzo(new BigDecimal(0));
        }

        Disponibilita disponibilita = new Disponibilita();
        disponibilita.setId(Integer.parseInt(available.getValue().replaceAll("[^0-9]", "")));
        prodotto.setDisponibilita(disponibilita);

        Categoria categoria = new Categoria();
        categoria.setId(Integer.parseInt(category.getValue().replaceAll("[^0-9]", "")));
        prodotto.setCategoria(categoria);

        Materia materia = new Materia();
        materia.setId(Integer.parseInt(material.getValue().replaceAll("[^0-9]", "")));
        prodotto.setMateria(materia);

        if(quantity != null && quantity.getText() != null && !quantity.getText().isEmpty() && !quantity.getText().isBlank()){
            prodotto.setQuantita_disp(Integer.parseInt(quantity.getText()));
        }
        else{
            prodotto.setQuantita_disp(0);
        }

        return prodotto;
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}