package org.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.services.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductInfoController implements Initializable {

    @FXML
    private Label name, available;

    public void setValues(Product product){
        name.setText(product.getProductName());
        available.setText(product.getDisponibilità());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}