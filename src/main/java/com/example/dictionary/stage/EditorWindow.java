package com.example.dictionary.stage;

import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.scene.SuperScene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditorWindow extends Window {
    @Override
    protected void initScenes() {
        scenes.put(SceneEnum.EDITOR, new SuperScene(SceneEnum.EDITOR));
    }

    public EditorWindow(Stage stage) {
        super("Editor");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        changeScene(SceneEnum.EDITOR);
    }
}
