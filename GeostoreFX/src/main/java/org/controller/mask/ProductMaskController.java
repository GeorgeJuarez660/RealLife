package org.controller.mask;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import org.models.*;
import org.services.Service;
import org.utility.Utility;

import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ProductMaskController implements Initializable {

    @FXML
    private DatePicker bornDate;
    @FXML
    private TextField name, price, quantity;
    @FXML
    private PasswordField password;
    @FXML
    private ChoiceBox<String> available, category, material;

    private Service service;
    private String IDkey; //usato per la ricerca/modifica/rimozione

    //------------------INITIALIZE-----------------------

    //per la creazione prodotto
    public void setAvailable(){
        service = new Service();
        Map<Integer, Disponibilita> disp = new HashMap<>();
        disp = service.ottieniDisponibilita();

        for(Disponibilita disponibilita : disp.values()){
            available.getItems().add(disponibilita.getId() + " - " + disponibilita.getCode());
        }
    }

    public void setCategory(){
        service = new Service();
        Map<Integer, Categoria> cat = new HashMap<>();
        cat = service.ottieniCategorie();

        for(Categoria categoria : cat.values()){
            category.getItems().add(categoria.getId() + " - " + categoria.getNome());
        }
    }

    public void setMaterial(){
        service = new Service();
        Map<Integer, Materia> mat = new HashMap<>();
        mat = service.ottieniMaterie();

        for(Materia materia : mat.values()){
            material.getItems().add(materia.getId() + " - " + materia.getNome());
        }
    }

    //per la modifica utente
    /*public void getValues(String IDkey){

        service = new Service();
        Utente utente;
        utente = service.ottieniProfiloUtente(Integer.parseInt(IDkey));

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(utente.getDataNascita());
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        name.setText(utente.getNome());
        surname.setText(utente.getCognome());
        sex.setText(utente.getSesso());
        bornDate.setValue(LocalDate.of(anno, mese, giorno));
        address.setText(utente.getIndirizzo());
        phoneNumber.setText(utente.getTelefono());

        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) utente;
            email.setText(admin.getEmail());
            password.setText(admin.getPassword());
            adminCode.setText(admin.getCodeAdmin());
            wallet.setText(admin.getPortafoglio().toString());
        }
        else{
            Cliente cliente = (Cliente) utente;
            email.setText(cliente.getEmail());
            password.setText(cliente.getPassword());
            wallet.setText(cliente.getPortafoglio().toString());
        }

        this.IDkey = IDkey;
    }*/

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per la creazione prodotto
    public Prodotto setValues() throws ParseException { //recuperato da mask
        Prodotto prodotto = new Prodotto();
        service = new Service();

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
    }

    //per la modifica prodotto
    /*public Cliente setValuesWithID() throws ParseException { //recuperato da mask
        Cliente cliente;
        if(adminCode != null && adminCode.getText() != null && !adminCode.getText().isEmpty() && !adminCode.getText().isBlank()){
            cliente = new Amministratore();
            Amministratore admin = (Amministratore) cliente;
            admin.setId(Integer.parseInt(IDkey));
            admin.setCodeAdmin(adminCode.getText());
            admin.setNome(name.getText());
            admin.setCognome(surname.getText());
            admin.setSesso(sex.getText());
            admin.setDataNascita(Date.valueOf(bornDate.getValue()));
            admin.setIndirizzo(address.getText());
            admin.setTelefono(phoneNumber.getText());
            admin.setEmail(email.getText());
            admin.setPassword(password.getText());
            admin.setPortafoglio(new BigDecimal(wallet.getText()));
        }
        else{
            cliente = new Cliente();
            cliente.setId(Integer.parseInt(IDkey));
            cliente.setNome(name.getText());
            cliente.setCognome(surname.getText());
            cliente.setSesso(sex.getText());
            cliente.setDataNascita(Date.valueOf(bornDate.getValue()));
            cliente.setIndirizzo(address.getText());
            cliente.setTelefono(phoneNumber.getText());
            cliente.setEmail(email.getText());
            cliente.setPassword(password.getText());
            cliente.setPortafoglio(new BigDecimal(wallet.getText()));
        }
        return cliente;
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

    @FXML
    private void patternNumberPrice(KeyEvent event){
        String check = event.getCharacter();
        if (!check.matches("[0-9,]")) {
            String currentText = price.getText();
            price.setText(currentText.replaceAll("[^0-9,]", ""));
        }
        else {
            System.out.println("OK");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}