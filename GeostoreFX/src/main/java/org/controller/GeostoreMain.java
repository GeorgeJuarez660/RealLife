package org.controller;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.util.Duration;
import org.services.LoadPage;

import java.io.IOException;

public class GeostoreMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Locale locale = new Locale("it"); // Setti il linguaggio di default da prendere il resource
        ResourceBundle bundle = ResourceBundle.getBundle("org.language.language", locale);
        FXMLLoader fxmlLoader = new FXMLLoader(GeostoreMain.class.getResource("/org/scenes/welcome.fxml"), bundle);
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        WelcomeController welcomeController = (WelcomeController) fxmlLoader.getController(); //Ottieni il controller della scena caricata
        welcomeController.setLanguage("it");

        stage.setTitle("GeoStore");
        stage.setScene(scene);
        stage.show();

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
    }

    public static void main(String[] args) {
        launch();
    }
}