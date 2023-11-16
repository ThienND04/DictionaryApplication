package com.example.dictionary.stage;

import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.scene.SuperScene;
import javafx.stage.Stage;

public class DictionaryWindow extends Window {
    public DictionaryWindow(Stage stage) {
        super("Offline Dictionary");
        changeScene(SceneEnum.DICTIONARY);
    }

    @Override
    protected void initScenes() {
        scenes.put(SceneEnum.DICTIONARY, new SuperScene(SceneEnum.DICTIONARY));
    }
}
