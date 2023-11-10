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
            String src = SceneConstants.cssPaths.get(type);
            String css;
            if(src != null) {
                css = getClass().getResource(src).toExternalForm();
            } else {
                css = getClass().getResource("common.css").toExternalForm();
            }
            scene.getStylesheets().add(css);
        } catch (Exception e) {

        }
    }
    public SuperScene() {

    }
}
