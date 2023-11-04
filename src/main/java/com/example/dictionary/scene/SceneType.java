package com.example.dictionary.scene;

public final class SceneType {
    public static final int HOME = 0;
    public static final int GAME = 1;    
    public static final int GAME_1 = 2;
    public static final int TRANSLATE = 3;
    public static final int GAME_2 = 4;
    public static final int GAME_3 = 5;

    public static final String[] fxmlPaths = {"home-view.fxml", "game-view.fxml", "game1-view.fxml", "translate-view.fxml", "game2-view.fxml", "game3-view.fxml"};
    public static final String[] cssPaths = {"home-view.css", "game-view.css", "game1-view.css", "translate-view.css", "game2-view.css", "game3-view.css"};
    private SceneType() {

    }
}
