package com.example.dictionary;

import com.example.dictionary.scene.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class Dictionary extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }
    @FXML
    private Stage window;
    @FXML
    public static Stage loadingDialog;

    private void initLoadingDialog() {
        loadingDialog = new Stage();
        loadingDialog.initModality(Modality.APPLICATION_MODAL);
        loadingDialog.initStyle(StageStyle.TRANSPARENT);
        ProgressIndicator progressIndicator = new ProgressIndicator();
        StackPane rootPane = new StackPane(progressIndicator);
        rootPane.setBackground(null);
        Scene scene = new Scene(rootPane);
        loadingDialog.setScene(scene);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.initLoadingDialog();
        this.initScenes();
        this.window = primaryStage;
        this.setSceneType(0);
        primaryStage.setTitle("Dictionary Demonstration");
        primaryStage.show();
    }

    private void initScenes() throws Exception {
        this.scenes.add(new HomeScene(this));
        this.scenes.add(new GameScene(this));        
        this.scenes.add(new Game1Scene(this));
        this.scenes.add(new TranslateScene(this));
        this.scenes.add(new Game2Scene(this));
        this.scenes.add(new Game3Scene(this));
    }
    private final ArrayList<AScene> scenes = new ArrayList<AScene>();
    public void setSceneType(int sceneType) {
        this.window.setScene(scenes.get(sceneType).getScene());
    }
}

