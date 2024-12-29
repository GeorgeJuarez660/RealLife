package org.controller.mask;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.models.*;
import org.services.Service;
import org.utility.Utility;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderMaskController implements Initializable {

    @FXML
    private TextField productName, userEmail, quantity, productPrice;
    @FXML
    private DatePicker orderDate;

    private Service service;
    private String IDkey; //usato per la ricerca/modifica/rimozione

    private Cliente userOrder;
    private Prodotto productOrder;

    //------------------INITIALIZE-----------------------

    //per l'ordinazione prodotto
    public void getValuesForOrder(String IDOrderkey, Cliente user){

        service = new Service();
        Prodotto prodotto;
        prodotto = service.ottieniProdotto(Integer.parseInt(IDOrderkey));

        productName.setText(prodotto.getNome());
        userEmail.setText(user.getEmail());

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(Date.valueOf(LocalDate.now()));
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        orderDate.setPromptText(giorno+"/"+mese+"/"+anno);
        productPrice.setText(Utility.formatValueBigDecimal(prodotto.getPrezzo()));

        userOrder = user;
        productOrder = prodotto;
    }

    //per la modifica ordine
    /*public void getValues(String IDkey){

        service = new Service();
        Prodotto prodotto;
        prodotto = service.ottieniProdotto(Integer.parseInt(IDkey));

        name.setText(prodotto.getNome());
        price.setText(Utility.formatValueBigDecimal(prodotto.getPrezzo()));
        available.setValue(prodotto.getDisponibilita().getId() + " - " + prodotto.getDisponibilita().getCode());
        category.setValue(prodotto.getCategoria().getId() + " - " + prodotto.getCategoria().getNome());
        material.setValue(prodotto.getMateria().getId() + " - " + prodotto.getMateria().getNome());
        quantity.setText(prodotto.getQuantita_disp().toString());

        this.IDkey = IDkey;
    }*/

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per l'ordinazione prodotto
    public Ordine setValues() throws ParseException { //recuperato da mask
        Ordine ordine = new Ordine();

        ordine.setUtente(userOrder);
        ordine.setProdotto(productOrder);

        if(productPrice != null && productPrice.getText() != null && !productPrice.getText().isEmpty() && !productPrice.getText().isBlank()) {
            ordine.setPrezzo_unitario(Utility.formatValueString(productPrice.getText()));
        }
        else{
            ordine.setPrezzo_unitario(new BigDecimal(0));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(orderDate.getPromptText(), formatter);

        ordine.setData_ordine(Timestamp.valueOf(localDate.atStartOfDay()));

        if(quantity != null && quantity.getText() != null && !quantity.getText().isEmpty() && !quantity.getText().isBlank()){
            ordine.setQuantita(Integer.parseInt(quantity.getText()));
        }
        else{
            ordine.setQuantita(0);
        }

        Stato stato = new Stato();
        stato.setId(1);

        ordine.setStato(stato);

        return ordine;
    }

    //per la modifica prodotto
    /*public Prodotto setValuesWithID() throws ParseException { //recuperato da mask
        Prodotto prodotto = new Prodotto();
        service = new Service();

        prodotto.setId(Integer.parseInt(IDkey));
        prodotto.setNome(name.getText());

        if(price != null && price.getText() != null && !price.getText().isEmpty() && !price.getText().isBlank()) {
            prodotto.setPrezzo(Utility.formatValueString(price.getText()));
        }
        else{
            prodotto.setPrezzo(new BigDecimal(0));
        }

        Disponibilita disponibilita = new Disponibilita();
        disponibilita.setId(Integer.parseInt(available.getValue().substring(0, 1)));
        prodotto.setDisponibilita(disponibilita);

        Categoria categoria = new Categoria();
        categoria.setId(Integer.parseInt(category.getValue().substring(0, 1)));
        prodotto.setCategoria(categoria);

        Materia materia = new Materia();
        materia.setId(Integer.parseInt(material.getValue().substring(0, 1)));
        prodotto.setMateria(materia);

        if(quantity != null && quantity.getText() != null && !quantity.getText().isEmpty() && !quantity.getText().isBlank()){
            prodotto.setQuantita_disp(Integer.parseInt(quantity.getText()));
        }
        else{
            prodotto.setQuantita_disp(0);
        }

        return prodotto;
    }*/

    //------------------NUMBER FIELD (TEXT FIELD WITH PATTERN)-----------------------

    @FXML
    private void patternNumberQuantity(KeyEvent event){
        String check = event.getCharacter();
        if (!check.matches("[0-9]")) {
            String currentText = quantity.getText();
            quantity.setText(currentText.replaceAll("[^0-9]", ""));
        }
        else {
            System.out.println("OK");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}