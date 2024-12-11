package org.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.models.News;
import org.models.Prodotto;

import java.net.URL;
import java.util.ResourceBundle;

public class NewsInfoController implements Initializable {

    @FXML
    private Label dateNtext;

    public void setValues(News notizia){
        dateNtext.setText(notizia.getTesto());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}