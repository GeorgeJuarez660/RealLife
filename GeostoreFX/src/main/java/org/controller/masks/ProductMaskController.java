package org.controller.masks;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import org.models.*;
import org.services.Service;
import org.utility.Utility;

import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ProductMaskController implements Initializable {

    @FXML
    private TextField name, price, quantity;
    @FXML
    private ChoiceBox<String> available, category, material;

    private Service service;
    private String IDkey; //usato per la ricerca/modifica/rimozione

    //------------------INITIALIZE-----------------------

    //per la creazione/modifica prodotto
    public void setAvailable(){
        service = new Service();
        Map<Integer, Disponibilita> disp = new HashMap<>();
        disp = service.ottieniDisponibilita();

        for(Disponibilita disponibilita : disp.values()){
            available.getItems().add(disponibilita.getId() + " - " + disponibilita.getCode());
        }
    }

    public void setCategory(){
        service = new Service();
        Map<Integer, Categoria> cat = new HashMap<>();
        cat = service.ottieniCategorie();

        for(Categoria categoria : cat.values()){
            category.getItems().add(categoria.getId() + " - " + categoria.getNome());
        }
    }

    public void setMaterial(){
        service = new Service();
        Map<Integer, Materia> mat = new HashMap<>();
        mat = service.ottieniMaterie();

        for(Materia materia : mat.values()){
            material.getItems().add(materia.getId() + " - " + materia.getNome());
        }
    }

    //per la modifica prodotto
    public void getValues(String IDkey){

        service = new Service();
        Prodotto prodotto;
        prodotto = service.ottieniProdotto(Integer.parseInt(IDkey));

        name.setText(prodotto.getNome());
        price.setText(Utility.formatValueBigDecimal(prodotto.getPrezzo()));
        available.setValue(prodotto.getDisponibilita().getId() + " - " + prodotto.getDisponibilita().getCode());
        category.setValue(prodotto.getCategoria().getId() + " - " + prodotto.getCategoria().getNome());
        material.setValue(prodotto.getMateria().getId() + " - " + prodotto.getMateria().getNome());
        quantity.setText(prodotto.getQuantita_disp().toString());

        this.IDkey = IDkey;
    }

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per la creazione prodotto
    public Prodotto setValues() throws ParseException { //recuperato da mask
        Prodotto prodotto = new Prodotto();
        service = new Service();

        prodotto.setNome(name.getText());

        if(price != null && price.getText() != null && !price.getText().isEmpty() && !price.getText().isBlank()) {
            prodotto.setPrezzo(Utility.formatValueString(price.getText()));
        }
        else{
            prodotto.setPrezzo(new BigDecimal(0));
        }

        Disponibilita disponibilita = new Disponibilita();

        disponibilita.setId(Integer.parseInt(available.getValue().replaceAll("[^0-9]", "")));
        disponibilita.setCode(available.getValue().replaceAll(".*[^a-zA-Z]", ""));
        prodotto.setDisponibilita(disponibilita);

        Categoria categoria = new Categoria();
        categoria.setId(Integer.parseInt(category.getValue().replaceAll("[^0-9]", "")));
        prodotto.setCategoria(categoria);

        Materia materia = new Materia();
        materia.setId(Integer.parseInt(material.getValue().replaceAll("[^0-9]", "")));
        prodotto.setMateria(materia);

        if(quantity != null && quantity.getText() != null && !quantity.getText().isEmpty() && !quantity.getText().isBlank()){
            prodotto.setQuantita_disp(Integer.parseInt(quantity.getText()));
        }
        else{
            prodotto.setQuantita_disp(0);
        }

        return prodotto;
    }

    //per la modifica prodotto
    public Prodotto setValuesWithID() throws ParseException { //recuperato da mask
        Prodotto prodotto = new Prodotto();
        service = new Service();

        prodotto.setId(Integer.parseInt(IDkey));
        prodotto.setNome(name.getText());

        if(price != null && price.getText() != null && !price.getText().isEmpty() && !price.getText().isBlank()) {
            prodotto.setPrezzo(Utility.formatValueString(price.getText()));
        }
        else{
            prodotto.setPrezzo(new BigDecimal(0));
        }

        Disponibilita disponibilita = new Disponibilita();
        disponibilita.setId(Integer.parseInt(available.getValue().replaceAll("[^0-9]", "")));
        prodotto.setDisponibilita(disponibilita);

        Categoria categoria = new Categoria();
        categoria.setId(Integer.parseInt(category.getValue().replaceAll("[^0-9]", "")));
        prodotto.setCategoria(categoria);

        Materia materia = new Materia();
        materia.setId(Integer.parseInt(material.getValue().replaceAll("[^0-9]", "")));
        prodotto.setMateria(materia);

        if(quantity != null && quantity.getText() != null && !quantity.getText().isEmpty() && !quantity.getText().isBlank()){
            prodotto.setQuantita_disp(Integer.parseInt(quantity.getText()));
        }
        else{
            prodotto.setQuantita_disp(0);
        }

        return prodotto;
    }

    //------------------NUMBER FIELD (TEXT FIELD WITH PATTERN)-----------------------

    @FXML
    private void patternNumberQuantity(KeyEvent event){
        String check = event.getCharacter();
        if (!check.matches("[0-9]")) {
            String currentText = quantity.getText();
            quantity.setText(currentText.replaceAll("[^0-9]", ""));
        }
        else {
            System.out.println("OK");
        }
    }

    @FXML
    private void patternNumberPrice(KeyEvent event){
        String check = event.getCharacter();
        if (!check.matches("[0-9,]")) {
            String currentText = price.getText();
            price.setText(currentText.replaceAll("[^0-9,]", ""));
        }
        else {
            System.out.println("OK");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}