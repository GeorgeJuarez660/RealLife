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
import org.services.Service;
import org.utility.Utility;
import java.sql.Date;

public class AccessController {
    @FXML
    private TextField name, surname, sex, phoneNumber, address, email, codeAdmin;

    @FXML
    private PasswordField password, confirmPassword;

    @FXML
    private DatePicker bornDate;

    private Service service;

    //------------------BUTTONS-----------------------

    @FXML
    private void signup(ActionEvent event) {
        System.out.println("Signing up");
        LoadPage.saveStage(event);
        LoadPage.loadingScene("REGISTRAZIONE IN CORSO...");

        Cliente user;
        service = new Service();

        if(!password.getText().equals(confirmPassword.getText())){
            LoadPage.answerScene("negative", "LE PASSWORD NON COINCIDONO");
            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(e -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.getFullScene("register");
            });
            delay.play();
        }
        else if(!Utility.getAge(Date.valueOf(bornDate.getValue()))){
            LoadPage.answerScene("negative", "DEVI AVERE ALMENO 13 ANNI PER REGISTRARTI");
            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(e -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.getFullScene("register");
            });
            delay.play();
        }
        else{
            if(codeAdmin != null && codeAdmin.getText() != null && !codeAdmin.getText().isEmpty() && !codeAdmin.getText().isBlank()){
                user = new Amministratore();
                Amministratore admin = (Amministratore) user;
                admin.setNome(Utility.getStringFirstLetterMaiusc(name.getText()));
                admin.setCognome(Utility.getStringFirstLetterMaiusc(surname.getText()));
                admin.setSesso(sex.getText());
                admin.setDataNascita(Date.valueOf(bornDate.getValue()));
                admin.setTelefono(phoneNumber.getText());
                admin.setIndirizzo(Utility.getStringFirstLetterMaiusc(address.getText()));
                admin.setEmail(email.getText().toLowerCase());
                admin.setPassword(password.getText());
                admin.setPortafoglio(Utility.insertBigDecimal("50"));
                admin.setCodeAdmin(codeAdmin.getText().toUpperCase());
                user = admin;
            }
            else{
                user = new Cliente();
                user.setNome(Utility.getStringFirstLetterMaiusc(name.getText()));
                user.setCognome(Utility.getStringFirstLetterMaiusc(surname.getText()));
                user.setSesso(sex.getText());
                user.setDataNascita(Date.valueOf(bornDate.getValue()));
                user.setTelefono(phoneNumber.getText());
                user.setIndirizzo(Utility.getStringFirstLetterMaiusc(address.getText()));
                user.setEmail(email.getText().toLowerCase());
                user.setPassword(password.getText());
                user.setPortafoglio(Utility.insertBigDecimal("50"));
            }

            service.registerUtente(user);
        }

    }

    @FXML
    private void signin(ActionEvent event) {
        System.out.println("Signing in");
        LoadPage.saveStage(event);
        LoadPage.loadingScene("ACCESSO IN CORSO...");

        Cliente user;
        service = new Service();

        if(codeAdmin != null && codeAdmin.getText() != null && !codeAdmin.getText().isEmpty() && !codeAdmin.getText().isBlank()){
            user = new Amministratore();
            Amministratore admin = (Amministratore) user;
            admin.setEmail(email.getText().toLowerCase());
            admin.setPassword(password.getText());
            admin.setCodeAdmin(codeAdmin.getText().toUpperCase());
            user = admin;
        }
        else{
            user = new Cliente();
            user.setEmail(email.getText().toLowerCase());
            user.setPassword(password.getText());
        }

        service.loginUtente(user);

    }

    @FXML
    private void back(ActionEvent event) {
        System.out.println("Going back");

        LoadPage.getFullScene("prepage");
    }


}