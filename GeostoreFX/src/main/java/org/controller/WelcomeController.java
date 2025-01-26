package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.services.LoadPage;

public class WelcomeController {

    //------------------BUTTONS-----------------------
    @FXML
    private void goesToPrepage(ActionEvent event) {
        System.out.println("goes to prepage");
        LoadPage.saveStage(event);

        LoadPage.getFullScene("prepage");
    }

    /*@FXML
    private void exit(ActionEvent event) {
        System.out.println("goes to prepage");
        //regola la chiusura del programma
        stage.setOnCloseRequest(event -> {
            event.consume(); //utilizzato per ritardare la chiusura imminente
            LoadPage.saveStageForClose(event);
            LoadPage.getFullScene("goodbye");

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event2 -> {
                // Dopo 3 secondi, carica la scena goodbye
                Platform.exit();
            });
            delay.play();
        });
    }*/


    @FXML
    public void initialize() {
        System.out.println("Welcome");
    }
}