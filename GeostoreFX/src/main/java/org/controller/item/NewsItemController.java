package org.controller.item;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.models.News;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class NewsItemController implements Initializable {

    @FXML
    private Label date, text;
    @FXML
    private Button update, delete;

    public void setValues(News notizia){
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(notizia.getDataPub());
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        date.setText(giorno+"/"+mese+"/"+anno+":");
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