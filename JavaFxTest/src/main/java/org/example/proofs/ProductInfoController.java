package org.example.proofs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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