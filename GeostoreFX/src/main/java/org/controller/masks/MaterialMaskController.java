package org.controller.masks;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.controlsfx.control.PopOver;
import org.models.Categoria;
import org.models.Materia;
import org.services.Service;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class MaterialMaskController implements Initializable {

    @FXML
    private TextField name;

    private PopOver popOver;

    private Service service;
    private String IDkey; //usato per la ricerca/modifica/rimozione

    //------------------INITIALIZE-----------------------

    //per la modifica materia
    public void getValues(String IDkey){

        service = new Service();
        Materia materia;
        materia = service.ottieniMateria(Integer.parseInt(IDkey));

        name.setText(materia.getNome());

        this.IDkey = IDkey;
    }

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per la creazione materia
    public Materia setValues() throws ParseException { //recuperato da mask
        Materia materia = new Materia();

        materia.setNome(name.getText().toUpperCase());

        return materia;
    }

    //per la modifica materia
    public Materia setValuesWithID() throws ParseException { //recuperato da mask
        Materia materia = new Materia();

        materia.setId(Integer.parseInt(IDkey));
        materia.setNome(name.getText().toUpperCase());

        return materia;
    }

    //------------------POP OVER (ON MOUSE ENTERED AND EXITED)-----------------------

    @FXML
    private void showPopOver(MouseEvent event){
        if(popOver == null){ //controlla se è vuoto
            Label info = new Label(); // Crea un label
            info.setText("CAMPO OBBLIGATORIO"); // Testo da visualizzare
            info.setTextFill(Color.rgb(63, 81, 181));
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
    private void hidePopOver(MouseEvent event){
        if(popOver != null && popOver.isShowing()){  //controllo se non è vuoto e se sta mostrando
            popOver.hide(); //verrà nascosto solo quando il cursore non si trova sopra al text area
            popOver = null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}