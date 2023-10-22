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
        DataList.getInstance().readData();
        this.initScenes();
        this.window = primaryStage;
        this.setSceneType(0);
        primaryStage.setTitle("Dictionary Demonstration");
        primaryStage.show();
    }

    private void initScenes() throws Exception {
        this.scenes.add(new HomeScene(this));
        this.scenes.add(new GameScene(this));
        this.scenes.add(new MyListScene(this));
        this.scenes.add(new Game1Scene(this));
        this.scenes.add(new TranslateScene(this));
    }
    private final ArrayList<AScene> scenes = new ArrayList<AScene>();
    public void setSceneType(int sceneType) {
        this.window.setScene(scenes.get(sceneType).getScene());
    }
}

