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
    /*public void getValues(String IDkey){

        service = new Service();
        Prodotto prodotto;
        prodotto = service.ottieniProdotto(Integer.parseInt(IDkey));

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
    public Codice setValues() throws ParseException { //recuperato da mask
        Codice codice = new Codice();
        service = new Service();

        codice.setCodice(code.getText());
        codice.setDescrizione(description.getText());

        return codice;
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