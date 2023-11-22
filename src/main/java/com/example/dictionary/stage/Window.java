package com.example.dictionary.stage;

import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.scene.SuperScene;
import javafx.stage.Stage;
import java.util.HashMap;

public abstract class Window {
    protected Stage window;

    protected final HashMap<SceneEnum, SuperScene> scenes = new HashMap<>();

    public void changeScene(SceneEnum sceneType) {
        window.setScene(scenes.get(sceneType).getScene());
        window.setMaximized(false);

        switch (sceneType) {
            case GAME, GAME_1, GAME_2, GAME_3 -> {
                window.setResizable(false);
            }
            default -> {
                window.setResizable(true);
            }
        }
    }

    public void changeTheme(int theme) {
        scenes.forEach((k, v) -> {
            v.getScene().getStylesheets().clear();
            v.initTheme(k, theme);
        });
    }

    protected abstract void initScenes();

    public void show() {
        window.show();
    }

    public void show(double x, double y) {
        window.show();
    }

    public void hide() {
        window.hide();
    }

    public Stage getWindow() {
        return window;
    }

    public Window() {
        window = new Stage();
        initScenes();
    }

    public Window(String title) {
        window = new Stage();
        window.setTitle(title);
        initScenes();
    }

    public Window(Stage stage) {
        window = stage;
        initScenes();
    }

    public Window(Stage stage, String title) {
        window = stage;
        window.setTitle(title);
        initScenes();
    }
}
