package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.UtenteRepository;
import org.services.LoadPage;
import org.utility.Utility;
import java.sql.Date;

public class AccessController {
    @FXML
    private TextField name, surname, sex, bornDate, phoneNumber, address, email, codeAdmin;

    @FXML
    private PasswordField password, confirmPassword;


    @FXML
    private void signup(ActionEvent event) {
        System.out.println("Signing up");
        LoadPage.saveStage(event);
        LoadPage.loadingScene("REGISTRAZIONE IN CORSO...");

        Cliente user = new Cliente();

        user.setNome(name.getText());
        user.setCognome(surname.getText());
        user.setSesso(sex.getText());
        user.setDataNascita(Date.valueOf(bornDate.getText()));
        user.setTelefono(phoneNumber.getText());
        user.setIndirizzo(address.getText());
        user.setEmail(email.getText());

        if(password.getText().equals(confirmPassword.getText())){
            user.setPassword(password.getText());
        }
        else{
            LoadPage.answerScene("negative", "LE PASSWORD NON COINCIDONO");
        }

        UtenteRepository uRe = new UtenteRepository();
        int num = 0;

        user.setPortafoglio(Utility.insertBigDecimal("50"));

        if (codeAdmin != null && codeAdmin.getText() != null){
            Amministratore admin = (Amministratore) user;
            admin.setCodeAdmin(codeAdmin.getText());

            num = uRe.insertUtenteWithDB(admin.getId(), admin);
        }
        else{
            num = uRe.insertUtenteWithDB(user.getId(), user);
        }

        Utility.sendResponse(num);
    }

    @FXML
    private void signin(ActionEvent event) {
        System.out.println("Signing in");

        LoadPage.saveStage(event);

        LoadPage.loadingScene("ACCESSO IN CORSO...");

        Cliente user = new Cliente();

        user.setEmail(email.getText());
        user.setPassword(password.getText());

        UtenteRepository uRe = new UtenteRepository();
        int num = 0;

        if (codeAdmin != null && codeAdmin.getText() != null){
            Amministratore admin = (Amministratore) user;
            admin.setCodeAdmin(codeAdmin.getText());

            admin = uRe.checkAdmin(admin.getEmail(), admin.getPassword(), admin.getCodeAdmin());

            if(admin.getEmail() != null){
                num = 1;
            }
        }
        else{
            user = uRe.checkCliente(user.getEmail(), user.getPassword());

            if(user.getEmail() != null){
                num = 1;
            }
        }

        Utility.sendResponse(num);
    }

    @FXML
    private void back(ActionEvent event) {
        System.out.println("Going back");

        LoadPage.getFullScene("prepage");
    }


}