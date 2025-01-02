package org.controller.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.models.*;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ProductTypeButtonController implements Initializable {

    //category and material buttons

    @FXML
    private Label id, name;
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

    public void setCategoryValues(Categoria categoria){
        id.setText("#" + categoria.getId().toString());
        name.setText(categoria.getNome());
    }

    public void setMaterialValues(Materia materia){
        id.setText("#" + materia.getId().toString());
        name.setText(materia.getNome());
    }

    public void enableButtons(Boolean isAdmin){
        update.setVisible(isAdmin);
        update.setManaged(isAdmin);
        delete.setVisible(isAdmin);
        delete.setManaged(isAdmin);
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void updating(){ //button per andare alla pagina di modifica categoria
        System.out.println("goes to update category");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("update");
        partialSceneDTO.setUser(user);
        String idKey = id.getText().replace("#", "");
        LoadPage.getPartialSceneCRU(partialSceneDTO, idKey);
    }

    @FXML
    private void deleting(){ //button per eliminare categoria
        System.out.println("goes to delete category");
        System.out.println("Start deleting");
        LoadPage.loadingScene("ELIMINAZIONE IN CORSO...");

        Service service = new Service();

        service.eliminazioneNotizia(id.getText().replace("#", ""), user);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}