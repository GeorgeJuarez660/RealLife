package org.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.services.LoadPage;

import java.util.Locale;

public class LanguageController {

    private Locale locale;

    //------------------BUTTONS-----------------------
    @FXML
    private void back(ActionEvent event) {
        System.out.println("Going back");
        LoadPage.getFullScene("welcome");
    }

    @FXML
    private void chooseItalian(ActionEvent event) {
        System.out.println("Going back");

        LoadPage.getFullSceneWithLang("it");
    }

    @FXML
    private void chooseEnglish(ActionEvent event) {
        System.out.println("Going back");

        LoadPage.getFullSceneWithLang("en");
    }

    @FXML
    private void chooseJapanese(ActionEvent event) {
        System.out.println("Going back");

        LoadPage.getFullSceneWithLang("ja");
    }


    @FXML
    public void initialize() {
        System.out.println("Language");
    }
}