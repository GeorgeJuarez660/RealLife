package org.controller.item;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.News;
import org.models.Utente;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class UserItemController implements Initializable {

    @FXML
    private Label id, name, surname, sex, bornDate, phoneNumber, address, email, password, adminCode, wallet;
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

    public void setValues(Utente utente){

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(utente.getDataNascita());
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) utente;
            id.setText(admin.getId().toString());
            name.setText(admin.getNome());
            surname.setText(admin.getCognome());
            sex.setText(admin.getSesso());
            bornDate.setText(giorno+"/"+mese+"/"+anno);
            phoneNumber.setText(admin.getTelefono());
            address.setText(admin.getIndirizzo());
            email.setText(admin.getEmail());
            password.setText(admin.getPassword());
            adminCode.setText(admin.getCodeAdmin());
            wallet.setText(admin.getPortafoglio().toString()+" C");
        }
        else{
            Cliente cliente = (Cliente) utente;
            id.setText(cliente.getId().toString());
            name.setText(cliente.getNome());
            surname.setText(cliente.getCognome());
            sex.setText(cliente.getSesso());
            bornDate.setText(giorno+"/"+mese+"/"+anno);
            phoneNumber.setText(cliente.getTelefono());
            address.setText(cliente.getIndirizzo());
            email.setText(cliente.getEmail());
            password.setText(cliente.getPassword());
            adminCode.setText("-");
            wallet.setText(cliente.getPortafoglio().toString()+" C");
        }
    }

    public void enableButtons(Boolean isAdmin){
        update.setVisible(isAdmin);
        update.setManaged(isAdmin);
        delete.setVisible(isAdmin);
        delete.setManaged(isAdmin);
    }

    //------------------BUTTONS-----------------------

    /*@FXML
    private void updating(){ //button per andare alla pagina di modifica notizia
        System.out.println("goes to update news");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("update");
        partialSceneDTO.setUser(user);
        String idKey = id.getText().replace("#", "");
        LoadPage.getPartialSceneCRU(partialSceneDTO, idKey);
    }

    @FXML
    private void deleting(){ //button per andare alla pagina di modifica notizia
        System.out.println("goes to delete news");
        System.out.println("Start deleting");
        LoadPage.loadingScene("ELIMINAZIONE IN CORSO...");

        Service service = new Service();

        service.eliminazioneNotizia(id.getText().replace("#", ""), user);
    }*/


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}