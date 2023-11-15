package com.example.dictionary.stage;

import com.example.dictionary.controller.Game2Controller;
import com.example.dictionary.scene.SceneConstants;
import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.scene.SuperScene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Game3Window extends Window {
    public Game3Window(Stage stage) {
        window = new Stage();
        window.setTitle("Game3");
        window.setResizable(false);
        window.setScene((new SuperScene(SceneEnum.GAME_3)).getScene());
        window.setOnHiding(windowEvent -> window.setScene((new SuperScene(SceneEnum.GAME_3)).getScene()));
    }
}
