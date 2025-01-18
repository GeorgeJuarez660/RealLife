package org.controller.masks;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.models.*;
import org.services.Service;
import org.utility.Utility;

import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

public class CodeMaskController implements Initializable {

    @FXML
    private TextField code;
    @FXML
    private TextArea description;

    private Service service;
    private String IDkey; //usato per la ricerca/modifica/rimozione

    //------------------INITIALIZE-----------------------

    //per la modifica prodotto
    public void getValues(String IDkey){

        service = new Service();
        Codice codice;
        codice = service.ottieniCodice(Integer.parseInt(IDkey));

        code.setText(codice.getCodice());
        description.setText(codice.getDescrizione());

        this.IDkey = IDkey;
    }

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per la creazione codice
    public Codice setValues() throws ParseException { //recuperato da mask
        Codice codice = new Codice();
        service = new Service();

        //funzione random che va da 100 a 999
        Random random = new Random();
        int num = random.nextInt(900) + 100;

        codice.setCodice(code.getText().toUpperCase() + num); //inserisce i primi tre caratteri e il numero random
        codice.setDescrizione(description.getText());

        return codice;
    }

    //per la modifica codice
    public Codice setValuesWithID() throws ParseException { //recuperato da mask
        Codice codice = new Codice();
        service = new Service();

        codice.setId(Integer.parseInt(IDkey));
        codice.setCodice(code.getText());
        codice.setDescrizione(description.getText());

        return codice;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}