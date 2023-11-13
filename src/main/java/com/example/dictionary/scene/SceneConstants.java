package com.example.dictionary.scene;

import java.util.HashMap;
import java.util.Map;

public final class SceneConstants {
    public static Map<SceneEnum, String> fxmlPaths = new HashMap<>();
    public static Map<SceneEnum, String> cssPaths = new HashMap<>();

    static {
        fxmlPaths.put(SceneEnum.HOME, "home-view.fxml");
        fxmlPaths.put(SceneEnum.GAME, "game-view.fxml");
        fxmlPaths.put(SceneEnum.GAME_1, "game1-view.fxml");
        fxmlPaths.put(SceneEnum.GAME_2, "game2-view.fxml");
        fxmlPaths.put(SceneEnum.GAME_3, "game3-view.fxml");
        fxmlPaths.put(SceneEnum.TRANSLATE, "translate-view.fxml");
        fxmlPaths.put(SceneEnum.DICTIONARY, "dictionary-view.fxml");
        fxmlPaths.put(SceneEnum.EDITOR, "editor-view.fxml");
        fxmlPaths.put(SceneEnum.USER, "user-view.fxml");
        fxmlPaths.put(SceneEnum.LOGIN, "login-view.fxml");
        fxmlPaths.put(SceneEnum.SIGNUP, "signup-view.fxml");
    }
    private SceneConstants() {

    }
}
