package org.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.models.Disponibilita;
import org.models.Prodotto;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetsController {// Questo è il BorderPane di menu.fxml

    @FXML
    private Label titleGets;

    @FXML
    private VBox list;

    public void setTitle(String title) {
        if (titleGets != null) {
            titleGets.setText(title);
        }
    }

    public void iteming(){
        Prodotto product = new Prodotto();
        product.setNome("test");
        Disponibilita disponibilita = new Disponibilita();
        disponibilita.setCode("alias");
        product.setDisponibilita(disponibilita);

        Prodotto product2 = new Prodotto();
        product2.setNome("test2");
        Disponibilita disponibilita2 = new Disponibilita();
        disponibilita2.setCode("alias2");
        product2.setDisponibilita(disponibilita2);

        List<Prodotto> productList = new ArrayList<>();

        productList.add(product);
        productList.add(product2);

        for (Prodotto value : productList) {
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("productInfoGets.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl);
                HBox productItem = loader.load();
                ProductInfoController productInfoController = loader.getController();
                productInfoController.setValues(value);
                list.getChildren().add(productItem);

                // Carica il file FXML
                // Imposta la scena caricata come contenuto centrale del BorderPane

            } catch (Exception e) {
                System.out.println("No page found. Please check FXMLLoader.");
                e.printStackTrace();
            }
        }
    }

    /*@FXML
    private void caricaScene2() {
        Pane scene2 = getPage("searchUsers.fxml"); // Usa il metodo generico per caricare la scena
        if (scene2 != null) {
            root.setCenter(scene2); // Imposta la scena nel centro del BorderPane
        }
    }*/
}