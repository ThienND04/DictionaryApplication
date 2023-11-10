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

        cssPaths.put(SceneEnum.HOME, "home-view.css");
        cssPaths.put(SceneEnum.GAME, "game-view.css");
        cssPaths.put(SceneEnum.GAME_1, "game1-view.css");
        cssPaths.put(SceneEnum.GAME_2, "game2-view.css");
        cssPaths.put(SceneEnum.GAME_3, "game3-view.css");
        cssPaths.put(SceneEnum.TRANSLATE, "translate-view.css");
        cssPaths.put(SceneEnum.DICTIONARY, "dictionary-view.css");
    }
    private SceneConstants() {

    }
}
