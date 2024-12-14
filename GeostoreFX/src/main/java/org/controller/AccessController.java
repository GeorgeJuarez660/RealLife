package org.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.UtenteRepository;
import org.services.LoadPage;
import org.utility.Utility;
import java.sql.Date;

public class AccessController {
    @FXML
    private TextField name, surname, sex, phoneNumber, address, email, codeAdmin;

    @FXML
    private PasswordField password, confirmPassword;

    @FXML
    private DatePicker bornDate;


    @FXML
    private void signup(ActionEvent event) {
        System.out.println("Signing up");
        LoadPage.saveStage(event);
        LoadPage.loadingScene("REGISTRAZIONE IN CORSO...");

        Cliente user = new Cliente();

        user.setNome(name.getText());
        user.setCognome(surname.getText());
        user.setSesso(sex.getText());
        user.setDataNascita(Date.valueOf(bornDate.getValue()));
        user.setTelefono(phoneNumber.getText());
        user.setIndirizzo(address.getText());
        user.setEmail(email.getText());

        if(password.getText().equals(confirmPassword.getText())){
            user.setPassword(password.getText());
            user.setPortafoglio(Utility.insertBigDecimal("50"));
            insertUser(user);
        }
        else{
            LoadPage.answerScene("negative", "LE PASSWORD NON COINCIDONO");
            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(e -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.getFullScene("register");
            });
            delay.play();
        }
    }

    private void insertUser(Cliente user){
        UtenteRepository uRe = new UtenteRepository();
        int num = 0;


        if (codeAdmin != null && codeAdmin.getText() != null && !codeAdmin.getText().isEmpty()){
            Amministratore admin = (Amministratore) user;
            admin.setCodeAdmin(codeAdmin.getText());

            num = uRe.insertUtenteWithDB(admin.getId(), admin);
        }
        else{
            num = uRe.insertUtenteWithDB(user.getId(), user);
        }

        Utility.sendResponseRegister(num);
    }

    @FXML
    private void signin(ActionEvent event) {
        System.out.println("Signing in");

        LoadPage.saveStage(event);

        LoadPage.loadingScene("ACCESSO IN CORSO...");

        Cliente user;

        UtenteRepository uRe = new UtenteRepository();
        int num = 0;

        if (codeAdmin != null && codeAdmin.getText() != null){
            user = new Amministratore();
            Amministratore admin = (Amministratore) user;
            admin.setEmail(email.getText());
            admin.setPassword(password.getText());
            admin.setCodeAdmin(codeAdmin.getText());

            admin = uRe.checkAdmin(admin.getEmail(), admin.getPassword(), admin.getCodeAdmin());

            if(admin.getEmail() != null){
                num = 1;
            }

            user = admin;
        }
        else{
            user = new Cliente();
            user.setEmail(email.getText());
            user.setPassword(password.getText());

            user = uRe.checkCliente(user.getEmail(), user.getPassword());

            if(user.getEmail() != null){
                num = 1;
            }
        }

        Utility.sendResponseLogin(num, user);
    }

    @FXML
    private void back(ActionEvent event) {
        System.out.println("Going back");

        LoadPage.getFullScene("prepage");
    }


}