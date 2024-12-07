package org.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.models.Prodotto;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductInfoController implements Initializable {

    @FXML
    private Label name, available;

    public void setValues(Prodotto product){
        name.setText(product.getNome());
        available.setText(product.getDisponibilita().getCode());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}