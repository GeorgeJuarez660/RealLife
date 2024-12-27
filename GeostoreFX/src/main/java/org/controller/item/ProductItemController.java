package org.controller.item;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import org.models.*;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ProductItemController implements Initializable {

    @FXML
    private Label id, name, price, available, category, material, quantity;
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

    public void setValues(Prodotto prodotto){

        id.setText(prodotto.getId().toString());
        name.setText(prodotto.getNome());
        price.setText(prodotto.getPrezzo().toString());
        available.setText(prodotto.getDisponibilita().getCode());
        category.setText(prodotto.getCategoria().getNome());
        material.setText(prodotto.getMateria().getNome());
        quantity.setText(prodotto.getQuantita_disp().toString());

    }

    public void enableButtons(Boolean isAdmin){
        update.setVisible(isAdmin);
        update.setManaged(isAdmin);
        delete.setVisible(isAdmin);
        delete.setManaged(isAdmin);
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void updating(){ //button per andare alla pagina di modifica prodotto
        System.out.println("goes to update product");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("update");
        partialSceneDTO.setItemScene("product");
        partialSceneDTO.setUser(user);
        String idKey = id.getText();
        LoadPage.getPartialSceneCRU(partialSceneDTO, idKey);
    }

    @FXML
    private void deleting(){ //button per eliminare prodotto
        System.out.println("goes to delete product");
        System.out.println("Start deleting");
        LoadPage.loadingScene("ELIMINAZIONE IN CORSO...");

        Service service = new Service();

        service.eliminazioneProdotto(id.getText(), user);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}