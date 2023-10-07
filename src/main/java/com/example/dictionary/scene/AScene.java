package com.example.dictionary.scene;

import com.example.dictionary.Dictionary;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public abstract class AScene {
    public Scene scene;
    public Scene getScene() {
        return this.scene;
    }
    public Dictionary dictionary;
    public AScene(Dictionary dictionary, String path) throws Exception {
        this.dictionary = dictionary;
        FXMLLoader fxmlLoader = new FXMLLoader();
        AnchorPane root = fxmlLoader.load(getClass().getResourceAsStream(path));
        this.scene = new Scene(root);
    }
}
