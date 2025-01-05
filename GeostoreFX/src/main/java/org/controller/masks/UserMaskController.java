package org.controller.masks;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.Utente;
import org.services.Service;
import org.utility.Utility;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

public class UserMaskController implements Initializable {

    @FXML
    private DatePicker bornDate;
    @FXML
    private TextField name, surname, sex, address, phoneNumber, email, adminCode, wallet;
    @FXML
    private PasswordField password;

    private Service service;
    private String IDkey; //usato per la ricerca/modifica/rimozione

    //------------------INITIALIZE-----------------------

    //per la modifica utente
    public void getValues(String IDkey){

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
            wallet.setText(Utility.formatValueBigDecimal(admin.getPortafoglio()));
        }
        else{
            Cliente cliente = (Cliente) utente;
            email.setText(cliente.getEmail());
            password.setText(cliente.getPassword());
            wallet.setText(Utility.formatValueBigDecimal(cliente.getPortafoglio()));
        }

        this.IDkey = IDkey;
    }

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per la creazione utente
    public Cliente setValues() throws ParseException { //recuperato da mask
        Cliente cliente;
        if(adminCode != null && adminCode.getText() != null && !adminCode.getText().isEmpty() && !adminCode.getText().isBlank()){
            cliente = new Amministratore();
            Amministratore admin = (Amministratore) cliente;
            admin.setCodeAdmin(adminCode.getText());
            admin.setNome(name.getText());
            admin.setCognome(surname.getText());
            admin.setSesso(sex.getText());
            admin.setDataNascita(Date.valueOf(bornDate.getValue()));
            admin.setIndirizzo(address.getText());
            admin.setTelefono(phoneNumber.getText());
            admin.setEmail(email.getText());
            admin.setPassword(password.getText());
            if(wallet != null && wallet.getText() != null && !wallet.getText().isEmpty() && !wallet.getText().isBlank()) {
                admin.setPortafoglio(Utility.formatValueString(wallet.getText()));
            }
            else {
                admin.setPortafoglio(new BigDecimal(0));
            }
        }
        else {
            cliente = new Cliente();
            cliente.setNome(name.getText());
            cliente.setCognome(surname.getText());
            cliente.setSesso(sex.getText());
            cliente.setDataNascita(Date.valueOf(bornDate.getValue()));
            cliente.setIndirizzo(address.getText());
            cliente.setTelefono(phoneNumber.getText());
            cliente.setEmail(email.getText());
            cliente.setPassword(password.getText());
            if (wallet != null && wallet.getText() != null && !wallet.getText().isEmpty() && !wallet.getText().isBlank()) {
                cliente.setPortafoglio(Utility.formatValueString(wallet.getText()));
            } else {
                cliente.setPortafoglio(new BigDecimal(0));
            }
        }
        return cliente;
    }

    //per la modifica utente
    public Cliente setValuesWithID() throws ParseException { //recuperato da mask
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
            if(wallet != null && wallet.getText() != null && !wallet.getText().isEmpty() && !wallet.getText().isBlank()) {
                admin.setPortafoglio(Utility.formatValueString(wallet.getText()));
            }
            else {
                admin.setPortafoglio(new BigDecimal(0));
            }
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
            if(wallet != null && wallet.getText() != null && !wallet.getText().isEmpty() && !wallet.getText().isBlank()) {
                cliente.setPortafoglio(Utility.formatValueString(wallet.getText()));
            }
            else {
                cliente.setPortafoglio(new BigDecimal(0));
            }
        }
        return cliente;
    }

    //------------------NUMBER FIELD (TEXT FIELD WITH PATTERN)-----------------------

    @FXML
    private void patternNumberWallet(KeyEvent event){
        String check = event.getCharacter();
        if (!check.matches("[0-9,]")) {
            String currentText = wallet.getText();
            wallet.setText(currentText.replaceAll("[^0-9,]", ""));
        }
        else {
            System.out.println("OK");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}