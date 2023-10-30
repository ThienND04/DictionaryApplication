package com.example.dictionary;

import com.example.dictionary.scene.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Dictionary extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }
    @FXML
    private Stage window;
    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        this.window = primaryStage;
        DataList.getInstance().readData();
        initScenes();
        this.setSceneType(0);
        primaryStage.setTitle("Dictionary Demonstration");
        primaryStage.show();
    }

    private void initScenes() throws Exception {
        this.scenes = new ArrayList<AScene>();
        scenes.add(new HomeScene(this));
        scenes.add(new GameScene(this));
        scenes.add(new MyListScene(this));
        scenes.add(new Game1Scene(this));
        scenes.add(new GameOverScene(this));
        scenes.add(new DefinitionEditorScene(this));
    }
    private ArrayList<AScene> scenes;
    public void setSceneType(int sceneType) {
        scenes.get(sceneType).update();
        this.window.setScene(scenes.get(sceneType).getScene());
    }
    public static Dictionary instance;
    public static Dictionary getInstance() {
        return instance;
    }
}

