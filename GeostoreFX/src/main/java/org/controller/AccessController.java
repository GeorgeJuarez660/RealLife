package org.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
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

    private PopOver popOver;

    private Service service;

    //------------------BUTTONS-----------------------

    @FXML
    private void signup(ActionEvent event) {
        System.out.println("Signing up");
        LoadPage.saveStage(event);
        LoadPage.loadingSceneWithLang("LOAD-REG", null);

        Cliente user;
        service = new Service();

        if(!password.getText().equals(confirmPassword.getText())){
            LoadPage.answerSceneWithLang("negative", "PWD-NOMATCH", null);
            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(e -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.getFullSceneWithLang("register", null);
            });
            delay.play();
        }
        else if(bornDate.getValue() == null){
            LoadPage.answerSceneWithLang("negative", "BD-ERR", null);
            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(e -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.getFullSceneWithLang("register", null);
            });
            delay.play();
        }
        else if(!Utility.getAge(Date.valueOf(bornDate.getValue()))){
            LoadPage.answerSceneWithLang("negative", "AGE-ERR", null);
            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(e -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.getFullSceneWithLang("register", null);
            });
            delay.play();
        }
        else{
            if(codeAdmin != null && codeAdmin.getText() != null && !codeAdmin.getText().isEmpty() && !codeAdmin.getText().isBlank()){
                user = new Amministratore();
                Amministratore admin = (Amministratore) user;
                admin.setNome(Utility.getStringFirstLetterMaiusc(name.getText()));
                admin.setCognome(Utility.getStringFirstLetterMaiusc(surname.getText()));
                admin.setSesso(sex.getText().toUpperCase());
                if(admin.checkCorrectBornDate(bornDate.getEditor().getText())){
                    admin.setDataNascita(Date.valueOf(bornDate.getValue()));
                }
                else{
                    admin.setDataNascita(null);
                }
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
                user.setSesso(sex.getText().toUpperCase());
                if(user.checkCorrectBornDate(bornDate.getEditor().getText())){
                    user.setDataNascita(Date.valueOf(bornDate.getValue()));
                }
                else{
                    user.setDataNascita(null);
                }
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
        LoadPage.loadingSceneWithLang("LOAD-LOG", null);

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

        LoadPage.getFullSceneWithLang("prepage", null);
    }

    //------------------POP OVER (ON MOUSE ENTERED AND EXITED)-----------------------

    @FXML
    private void showPopOver(MouseEvent event){
        if(popOver == null){ //controlla se è vuoto
            Label info = new Label(); // Crea un label
            info.setText("CAMPO OBBLIGATORIO"); // Testo da visualizzare
            info.setTextFill(Color.rgb(35, 82, 164));
            info.setFont(new Font("Press Start 2P", 9));
            info.setWrapText(true);
            info.setTextAlignment(TextAlignment.CENTER);

            // Crea il VBox per il popover
            VBox vBox = new VBox(info);
            vBox.setPrefWidth(130);
            vBox.setPrefHeight(10); // Altezza per includere anche la freccia
            vBox.setAlignment(Pos.CENTER);

            // Crea il popover
            popOver = new PopOver(vBox);
            popOver.setAnimated(false); // Disabilita l'animazione
            popOver.setCornerRadius(10);
        }
        popOver.show((Node) event.getSource()); //verrà mostrato solo quando il cursore si trova sopra al text area
    }

    @FXML
    private void showPopOverSex(MouseEvent event){
        if(popOver == null){ //controlla se è vuoto
            Label info = new Label(); // Crea un label
            info.setText("CAMPO OBBLIGATORIO"); // Testo da visualizzare
            info.setTextFill(Color.rgb(35, 82, 164));
            info.setFont(new Font("Press Start 2P", 9));
            info.setWrapText(true);
            info.setTextAlignment(TextAlignment.CENTER);

            Label info2 = new Label(); // Crea un label
            info2.setText("SOLO: M, F, P o N"); // Testo da visualizzare
            info2.setTextFill(Color.rgb(35, 82, 164));
            info2.setFont(new Font("Press Start 2P", 8));
            info2.setWrapText(true);
            info2.setTextAlignment(TextAlignment.CENTER);

            // Crea il VBox per il popover
            VBox vBox = new VBox(6);
            vBox.setPrefWidth(130);
            vBox.setPrefHeight(10); // Altezza per includere anche la freccia
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(info, info2);

            // Crea il popover
            popOver = new PopOver(vBox);
            popOver.setAnimated(false); // Disabilita l'animazione
            popOver.setCornerRadius(10);
        }
        popOver.show((Node) event.getSource()); //verrà mostrato solo quando il cursore si trova sopra al text area
    }

    @FXML
    private void showPopOverBornDate(MouseEvent event){
        if(popOver == null){ //controlla se è vuoto
            Label info = new Label(); // Crea un label
            info.setText("CAMPO OBBLIGATORIO"); // Testo da visualizzare
            info.setTextFill(Color.rgb(35, 82, 164));
            info.setFont(new Font("Press Start 2P", 9));
            info.setWrapText(true);
            info.setTextAlignment(TextAlignment.CENTER);

            Label info2 = new Label(); // Crea un label
            info2.setText("FORMATO: DD/MM/YYYY"); // Testo da visualizzare
            info2.setTextFill(Color.rgb(35, 82, 164));
            info2.setFont(new Font("Press Start 2P", 8));
            info2.setWrapText(true);
            info2.setTextAlignment(TextAlignment.CENTER);

            // Crea il VBox per il popover
            VBox vBox = new VBox(6);
            vBox.setPrefWidth(130);
            vBox.setPrefHeight(10); // Altezza per includere anche la freccia
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(info, info2);

            // Crea il popover
            popOver = new PopOver(vBox);
            popOver.setAnimated(false); // Disabilita l'animazione
            popOver.setCornerRadius(10);
        }
        popOver.show((Node) event.getSource()); //verrà mostrato solo quando il cursore si trova sopra al text area
    }

    @FXML
    private void showPopOverEmail(MouseEvent event){
        if(popOver == null){ //controlla se è vuoto
            Label info = new Label(); // Crea un label
            info.setText("CAMPO OBBLIGATORIO"); // Testo da visualizzare
            info.setTextFill(Color.rgb(35, 82, 164));
            info.setFont(new Font("Press Start 2P", 9));
            info.setWrapText(true);
            info.setTextAlignment(TextAlignment.CENTER);

            Label info2 = new Label(); // Crea un label
            info2.setText("FORMATO: nome@casella.com"); // Testo da visualizzare
            info2.setTextFill(Color.rgb(35, 82, 164));
            info2.setFont(new Font("Press Start 2P", 8));
            info2.setWrapText(true);
            info2.setTextAlignment(TextAlignment.CENTER);

            // Crea il VBox per il popover
            VBox vBox = new VBox(6);
            vBox.setPrefWidth(130);
            vBox.setPrefHeight(10); // Altezza per includere anche la freccia
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(info, info2);

            // Crea il popover
            popOver = new PopOver(vBox);
            popOver.setAnimated(false); // Disabilita l'animazione
            popOver.setCornerRadius(10);
        }
        popOver.show((Node) event.getSource()); //verrà mostrato solo quando il cursore si trova sopra al text area
    }

    @FXML
    private void showPopOverConfirmPwd(MouseEvent event){
        if(popOver == null){ //controlla se è vuoto
            Label info = new Label(); // Crea un label
            info.setText("CAMPO OBBLIGATORIO"); // Testo da visualizzare
            info.setTextFill(Color.rgb(35, 82, 164));
            info.setFont(new Font("Press Start 2P", 9));
            info.setWrapText(true);
            info.setTextAlignment(TextAlignment.CENTER);

            Label info2 = new Label(); // Crea un label
            info2.setText("DEVONO COINCIDERE"); // Testo da visualizzare
            info2.setTextFill(Color.rgb(35, 82, 164));
            info2.setFont(new Font("Press Start 2P", 8));
            info2.setWrapText(true);
            info2.setTextAlignment(TextAlignment.CENTER);

            // Crea il VBox per il popover
            VBox vBox = new VBox(6);
            vBox.setPrefWidth(130);
            vBox.setPrefHeight(10); // Altezza per includere anche la freccia
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(info, info2);

            // Crea il popover
            popOver = new PopOver(vBox);
            popOver.setAnimated(false); // Disabilita l'animazione
            popOver.setCornerRadius(10);
        }
        popOver.show((Node) event.getSource()); //verrà mostrato solo quando il cursore si trova sopra al text area
    }

    @FXML
    private void showPopOverCode(MouseEvent event){
        if(popOver == null){ //controlla se è vuoto
            Label info = new Label(); // Crea un label
            info.setText("CAMPO OBBLIGATORIO"); // Testo da visualizzare
            info.setTextFill(Color.rgb(35, 82, 164));
            info.setFont(new Font("Press Start 2P", 9));
            info.setWrapText(true);
            info.setTextAlignment(TextAlignment.CENTER);

            Label info2 = new Label(); // Crea un label
            info2.setText("FORMATO: GSX123"); // Testo da visualizzare
            info2.setTextFill(Color.rgb(35, 82, 164));
            info2.setFont(new Font("Press Start 2P", 8));
            info2.setWrapText(true);
            info2.setTextAlignment(TextAlignment.CENTER);

            // Crea il VBox per il popover
            VBox vBox = new VBox(6);
            vBox.setPrefWidth(130);
            vBox.setPrefHeight(10); // Altezza per includere anche la freccia
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(info, info2);

            // Crea il popover
            popOver = new PopOver(vBox);
            popOver.setAnimated(false); // Disabilita l'animazione
            popOver.setCornerRadius(10);
        }
        popOver.show((Node) event.getSource()); //verrà mostrato solo quando il cursore si trova sopra al text area
    }

    @FXML
    private void hidePopOver(MouseEvent event){
        if(popOver != null && popOver.isShowing()){  //controllo se non è vuoto e se sta mostrando
            popOver.hide(); //verrà nascosto solo quando il cursore non si trova sopra al text area
            popOver = null; //per pulire l'istanza che cosi posso utilizzare gli altri popOver
        }
    }

}