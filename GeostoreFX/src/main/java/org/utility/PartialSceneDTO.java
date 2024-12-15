package org.utility;

import javafx.scene.layout.BorderPane;
import org.models.Cliente;

import java.sql.Date;
import java.util.Objects;

public class PartialSceneDTO {
    private BorderPane fxmlLoader;
    private String innerScene;
    private Cliente user;

    public BorderPane getFxmlLoader() {
        return fxmlLoader;
    }

    public void setFxmlLoader(BorderPane fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    public String getInnerScene() {
        return innerScene;
    }

    public void setInnerScene(String innerScene) {
        this.innerScene = innerScene;
    }

    public Cliente getUser() {
        return user;
    }

    public void setUser(Cliente user) {
        this.user = user;
    }
}
