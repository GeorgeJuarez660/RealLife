package org.controller.masks;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.models.Categoria;
import org.models.Materia;
import org.services.Service;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class MaterialMaskController implements Initializable {

    @FXML
    private TextField name;

    private Service service;
    private String IDkey; //usato per la ricerca/modifica/rimozione

    //------------------INITIALIZE-----------------------

    //per la modifica materia
    public void getValues(String IDkey){

        service = new Service();
        Materia materia;
        materia = service.ottieniMateria(Integer.parseInt(IDkey));

        name.setText(materia.getNome());

        this.IDkey = IDkey;
    }

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per la creazione materia
    public Materia setValues() throws ParseException { //recuperato da mask
        Materia materia = new Materia();

        materia.setNome(name.getText().toUpperCase());

        return materia;
    }

    //per la modifica materia
    public Materia setValuesWithID() throws ParseException { //recuperato da mask
        Materia materia = new Materia();

        materia.setId(Integer.parseInt(IDkey));
        materia.setNome(name.getText().toUpperCase());

        return materia;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}