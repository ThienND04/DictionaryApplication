package com.example.dictionary.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class SuperScene {
    protected Scene scene;

    public Scene getScene() {
        return scene;
    }

    public SuperScene(SceneEnum type) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            String path = type.getValue();
            AnchorPane root = fxmlLoader.load(getClass().getResourceAsStream(path));
            scene = new Scene(root);

            String css = null;
            try {
                css = getClass().getResource(path.replace("fxml", "css")).toExternalForm();
            } catch (Exception e) {
                css = getClass().getResource("common.css").toExternalForm();
            } finally {
                scene.getStylesheets().add(css);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SuperScene() {

    }
}
