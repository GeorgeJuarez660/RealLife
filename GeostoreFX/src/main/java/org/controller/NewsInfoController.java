package org.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import org.models.News;
import org.models.Prodotto;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

public class NewsInfoController implements Initializable {

    @FXML
    private Label date, text;
    @FXML
    private Button update, delete;

    public void setValues(News notizia){
        date.setText(LocalDate.now().getDayOfMonth()+"/"+LocalDate.now().getMonthValue()+"/"+ LocalDate.now().getYear()+":");
        text.setText(notizia.getTesto());
    }

    public void enableButtons(Boolean isAdmin){
        update.setVisible(isAdmin);
        update.setManaged(isAdmin);
        delete.setVisible(isAdmin);
        delete.setManaged(isAdmin);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}