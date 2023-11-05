package com.example.dictionary.stage;

import com.example.dictionary.scene.SceneConstants;
import com.example.dictionary.scene.SuperScene;
import javafx.stage.Stage;

public class DictionaryWindow extends Window {
    public DictionaryWindow(Stage stage) {
        window = new Stage();
        window.setTitle("Offline Dictionary");
        window.setScene((new SuperScene(SceneConstants.DICTIONARY)).getScene());
    }
}
