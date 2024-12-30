package org.controller.item;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.Ordine;
import org.models.Prodotto;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;
import org.utility.Utility;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class OrderItemController implements Initializable {

    @FXML
    private Label id, userEmail, productName, orderDate, status, orderQuantity, productPrice;
    @FXML
    private Button update, delete;

    private Cliente user;
    private Boolean isAdmin;
    private BorderPane fxmlLoader;

    //------------------INITIALIZE-----------------------

    public void save(BorderPane fxmlLoader, Cliente utente){

        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) utente;
            user = admin;
            isAdmin = admin.getCodeAdmin() != null && !admin.getCodeAdmin().isEmpty() && !admin.getCodeAdmin().isBlank();
        }
        else{
            user = utente;
            isAdmin = false;
        }

        this.fxmlLoader = fxmlLoader;
    }

    public void setValues(Ordine ordine){

        id.setText(ordine.getId().toString());

        if(ordine.getUtente() instanceof Amministratore){
            Amministratore admin = (Amministratore) ordine.getUtente();
            userEmail.setText(admin.getEmail());
        }
        else{
            Cliente cliente = (Cliente) ordine.getUtente();
            userEmail.setText(cliente.getEmail());
        }

        productName.setText(ordine.getProdotto().getNome());

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(ordine.getData_ordine());
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        orderDate.setText(giorno+"/"+mese+"/"+anno);
        status.setText(ordine.getStato().getCode());
        orderQuantity.setText(ordine.getQuantita().toString());
        productPrice.setText(Utility.formatValueBigDecimal(ordine.getPrezzo_unitario()) + " C");

    }

    public void enableButtons(Boolean isAdmin){
        update.setVisible(isAdmin);
        update.setManaged(isAdmin);
        delete.setVisible(isAdmin);
        delete.setManaged(isAdmin);
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void updating(){ //button per andare alla pagina di modifica ordine
        System.out.println("goes to update order");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("update");
        partialSceneDTO.setItemScene("order");
        partialSceneDTO.setUser(user);
        String idKey = id.getText();
        LoadPage.getPartialSceneCRU(partialSceneDTO, idKey);
    }

    /*@FXML
    private void deleting(){ //button per eliminare ordine
        System.out.println("goes to delete order");
        System.out.println("Start deleting");
        LoadPage.loadingScene("ELIMINAZIONE IN CORSO...");

        Service service = new Service();

        service.eliminazioneProdotto(id.getText(), user);
    }*/


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}