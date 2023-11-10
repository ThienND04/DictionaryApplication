package com.example.dictionary.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class SuperScene {
    protected javafx.scene.Scene scene;
    public javafx.scene.Scene getScene() {
        return scene;
    }
    public SuperScene(SceneEnum type) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root;
        try {
            root = fxmlLoader.load(getClass().getResourceAsStream(SceneConstants.fxmlPaths.get(type)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            scene = new javafx.scene.Scene(root);
            String css = getClass().getResource(SceneConstants.cssPaths.get(type)).toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e) {
        }
    }
    public SuperScene() {

    }
}
