package org.controller.mask;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.models.Cliente;
import org.models.News;
import org.services.Service;

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

    private Service service;
    private String IDkey; //usato per la ricerca/modifica/rimozione
    private Cliente user; //usato per la ricerca/modifica/rimozione

    //------------------INIZIALIZE-----------------------

    //per la creazione notizia
    public void setDate(){
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(Date.valueOf(LocalDate.now()));
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        date.setPromptText(giorno+"/"+mese+"/"+anno);
    }

    //per la modifica notizia
    public void getValues(String IDkey){

        service = new Service();
        News news = new News();
        news = service.ottieniNotiziaByID(IDkey);

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(news.getDataMod());
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        date.setPromptText(giorno+"/"+mese+"/"+anno);
        text.setText(news.getTesto());
        this.IDkey = IDkey;
    }

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per la creazione notizia
    public News setValues() throws ParseException { //recuperato da mask
        News news = new News();
        news.setTesto(text.getText());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date.getPromptText(), formatter);

        news.setDataMod(Date.valueOf(localDate));
        news.setDataPub(Date.valueOf(localDate));
        return news;
    }

    //per la modifica notizia
    public News setValuesWithID() throws ParseException { //recuperato da mask
        News news = new News();
        news.setTesto(text.getText());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date.getPromptText(), formatter);

        news.setDataMod(Date.valueOf(localDate));
        news.setDataPub(Date.valueOf(localDate));
        news.setId(Integer.parseInt(IDkey));
        return news;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}