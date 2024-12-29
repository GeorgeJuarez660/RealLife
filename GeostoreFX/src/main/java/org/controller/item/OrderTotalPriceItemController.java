package org.controller.item;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.Ordine;
import org.utility.Utility;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class OrderTotalPriceItemController implements Initializable {

    @FXML
    private Label userName, userSurname, totalOrderDate, totalOrderPrice;

    //------------------INITIALIZE-----------------------

    public void setValues(Ordine ordine){

        userName.setText(ordine.getUtente().getNome());
        userSurname.setText(ordine.getUtente().getCognome());

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(ordine.getData_ordine());
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        totalOrderDate.setText(giorno+"/"+mese+"/"+anno);
        totalOrderPrice.setText(Utility.formatValueBigDecimal(ordine.getPrezzo_unitario()) + " C");

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}