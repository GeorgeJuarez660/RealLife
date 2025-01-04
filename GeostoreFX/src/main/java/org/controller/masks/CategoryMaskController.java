package org.controller.masks;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.models.Categoria;
import org.models.Disponibilita;
import org.models.Materia;
import org.models.Prodotto;
import org.services.Service;
import org.utility.Utility;

import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CategoryMaskController implements Initializable {

    @FXML
    private TextField name;

    private Service service;
    private String IDkey; //usato per la ricerca/modifica/rimozione

    //------------------INITIALIZE-----------------------

    //per la modifica categoria
    public void getValues(String IDkey){

        service = new Service();
        Categoria categoria;
        categoria = service.ottieniCategoria(Integer.parseInt(IDkey));

        name.setText(categoria.getNome());

        this.IDkey = IDkey;
    }

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per la creazione categoria
    public Categoria setValues() throws ParseException { //recuperato da mask
        Categoria categoria = new Categoria();

        categoria.setNome(name.getText());

        return categoria;
    }

    //per la modifica categoria
    public Categoria setValuesWithID() throws ParseException { //recuperato da mask
        Categoria categoria = new Categoria();

        categoria.setId(Integer.parseInt(IDkey));
        categoria.setNome(name.getText());

        return categoria;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}