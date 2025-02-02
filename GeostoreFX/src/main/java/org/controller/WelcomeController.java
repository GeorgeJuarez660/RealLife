package org.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.services.LoadPage;

public class WelcomeController {

    private Stage stage;

    //------------------BUTTONS-----------------------
    @FXML
    private void goesToPrepage(ActionEvent event) {
        System.out.println("goes to prepage");
        LoadPage.saveStage(event);

        LoadPage.getFullScene("prepage");
    }

    @FXML
    private void exit(ActionEvent event) {

        //trasforma l'evento in uno stage
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        System.out.println("closing...");
        //regola la chiusura del programma
        stage.setOnCloseRequest(e -> {
            e.consume(); //utilizzato per ritardare la chiusura imminente
            LoadPage.saveStageForClose(e);
            LoadPage.getFullScene("goodbye");

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event2 -> {
                // Dopo 3 secondi, carica la scena goodbye
                Platform.exit();
            });
            delay.play();
        });

        // Simula una richiesta di chiusura della finestra (questo succede perchè non viene intercettato window close event come nel tasto X)
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    private void goesToChangeLanguage(MouseEvent event) {
        System.out.println("goes to change language");
        LoadPage.saveStage(event);

        LoadPage.getFullScene("language");
    }


    @FXML
    public void initialize() {
        System.out.println("Welcome");
    }
}