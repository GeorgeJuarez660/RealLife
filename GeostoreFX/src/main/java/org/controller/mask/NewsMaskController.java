package org.controller.mask;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.models.News;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.sql.Date;
import java.util.ResourceBundle;

public class NewsMaskController implements Initializable {

    @FXML
    private DatePicker date;
    @FXML
    private TextArea text;

    public void setDate(){
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(Date.valueOf(LocalDate.now()));
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        date.setPromptText(giorno+"/"+mese+"/"+anno);
    }


    public News setValues() throws ParseException {
        News news = new News();
        news.setTesto(text.getText());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date.getPromptText(), formatter);

        news.setDataMod(Date.valueOf(localDate));
        news.setDataPub(Date.valueOf(localDate));
        return news;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}