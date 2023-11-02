package com.example.dictionary.scene;

import com.example.dictionary.Dictionary;

public final class Game1Scene extends AScene {
    public static Game1Scene instance;
    public static Game1Scene getInstance() {
        return instance;
    }

    public Game1Scene(Dictionary dictionary) throws Exception {
        super(dictionary, SceneType.GAME_1);
        instance = this;
    }
}
