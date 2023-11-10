package com.example.dictionary.stage;

import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.scene.SuperScene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditorWindow extends Window {
    public EditorWindow(Stage stage) {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(new SuperScene(SceneEnum.EDITOR).getScene());
        window.setTitle("Editor");
        window.setResizable(false);
    }
}
