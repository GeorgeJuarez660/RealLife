package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.services.LoadPage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HomepageController {

    @FXML
    private Label title;

    @FXML
    public void title(String nomeUtente) {
        System.out.println("homepage");

        String startMorning = "06:00";
        String endMorning = "12:00";
        String startAfternoon = "12:00";
        String endAfternoon = "17:00";
        String startEvening = "17:00";
        String endEvening = "23:00";
        String startNight = "23:00";
        String endNight = "06:00";

        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formatTime = currentTime.format(formatter);

        String greetings = "";

        if(formatTime.compareTo(startMorning) >= 0 && formatTime.compareTo(endMorning) < 0){
            greetings = "Buongiorno ";
        }
        else if(formatTime.compareTo(startAfternoon) >= 0 && formatTime.compareTo(endAfternoon) < 0){
            greetings = "Buon pomeriggio ";
        }
        else if(formatTime.compareTo(startEvening) >= 0 && formatTime.compareTo(endEvening) < 0){
            greetings = "Buonasera ";
        }
        else if(formatTime.compareTo(startNight) >= 0 || formatTime.compareTo(endNight) < 0){
            greetings = "Buona permanenza notturna ";
        }
        greetings += nomeUtente;
        title.setText(greetings);

        //LoadPage.access("register");
    }

    // Metodo per cambiare il testo di titleGets


    /*@FXML
    private void caricaScene2() {
        Pane scene2 = getPage("searchUsers.fxml"); // Usa il metodo generico per caricare la scena
        if (scene2 != null) {
            root.setCenter(scene2); // Imposta la scena nel centro del BorderPane
        }
    }*/
}