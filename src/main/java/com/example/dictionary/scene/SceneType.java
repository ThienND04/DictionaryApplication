package com.example.dictionary.scene;

public final class SceneType {
    public static final int HOME = 0;
    public static final int GAME = 1;    
    public static final int GAME_1 = 2;
    public static final int TRANSLATE = 3;
    public static int GAME_OVER = 4;
    public static int TEXT_EDITOR = 5;
    public static int MY_LIST = 6;
    public static final String[] paths =
            {"home-view.fxml", "game-view.fxml", "game1-view.fxml", "translate-view.fxml", "gameover-view.fxml", "definition-editor.fxml", "my-list-view.fxml"};

    private SceneType() {

    }
}
