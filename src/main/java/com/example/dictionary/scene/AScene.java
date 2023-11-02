package com.example.dictionary.scene;

import com.example.dictionary.Dictionary;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public abstract class AScene {
    @FXML
    private Button homeNav;
    @FXML
    private Button myListNav;
    @FXML
    private Button gameNav;    
    @FXML
    private Button translateNav;

    private final Scene scene;

    public Scene getScene() {
        return this.scene;
    }

    private final Dictionary dictionary;

    public Dictionary getDictionary() {
        return dictionary;
    }

    public AScene(Dictionary dictionary, int sceneType) throws Exception {
        this.dictionary = dictionary;
        FXMLLoader fxmlLoader = new FXMLLoader();
        AnchorPane root = fxmlLoader.load(getClass().getResourceAsStream(SceneType.paths[sceneType]));
        this.scene = new Scene(root);
        this.init();
    }

    protected void init() {
        this.gameNav = (Button) this.scene.lookup("#gameNav");
        this.homeNav = (Button) this.scene.lookup("#homeNav");        
        this.myListNav = (Button) this.scene.lookup("#myListNav");
        this.translateNav = (Button) this.scene.lookup("#translateNav");
        this.gameNav.setOnAction(event -> dictionary.setSceneType(SceneType.GAME));        
        this.homeNav.setOnAction(event -> dictionary.setSceneType(SceneType.HOME));
        this.myListNav.setOnAction(event -> dictionary.setSceneType(SceneType.MY_LIST));
        this.translateNav.setOnAction(event -> dictionary.setSceneType(SceneType.TRANSLATE));
    }

    /**
     * Update scene state
     * (Some data can be changed).
     */
    public void update() {}
}
