package com.example.dictionary;

import com.example.dictionary.stage.*;
import javafx.stage.Stage;

import java.util.HashMap;

public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        javafx.application.Application.launch(args);
    }

    private final HashMap<WindowEnum, Window> windows = new HashMap<>();
    private static Application instance;

    public static Application getInstance() {
        return instance;
    }

    public void showWindow(WindowEnum type) {
        windows.get(type).show();
    }

    public void hideWindow(WindowEnum type) {
        windows.get(type).hide();
    }

    @Override
    public void start(Stage stage) {
        instance = this;
        windows.put(WindowEnum.GAME_1, new Game1Window(stage));
        windows.put(WindowEnum.GAME_2, new Game2Window(stage));
        windows.put(WindowEnum.GAME_3, new Game3Window(stage));
        windows.put(WindowEnum.WAITING, new WaitingWindow(stage));
        windows.put(WindowEnum.DICTIONARY, new DictionaryWindow(stage));
        windows.put(WindowEnum.PRIMARY, new PrimaryWindow(stage));
        windows.put(WindowEnum.EDITOR, new EditorWindow(stage));
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            Data.getInstance().writeData();
            System.exit(0);
        });
    }
}

