package com.example.dictionary.scene;

import com.example.dictionary.stage.PrimaryWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.io.IOException;

public class PrimaryScene extends SuperScene {


    public PrimaryScene(PrimaryWindow primaryWindow, SceneEnum sceneType) {
        super(sceneType);
        AnchorPane root = (AnchorPane) scene.getRoot();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            HBox navBar = fxmlLoader.load(getClass().getResourceAsStream("navBar.fxml"));
            ((VBox) root.getChildren().get(0)).getChildren().add(0, navBar);
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (sceneType) {
            case HOME -> {
                scene.lookup("#homeNav").getStyleClass().add("btn-22");
                scene.lookup("#gameNav").getStyleClass().add("btn-1");
                scene.lookup("#translateNav").getStyleClass().add("btn-1");
            }
            case TRANSLATE -> {
                scene.lookup("#homeNav").getStyleClass().add("btn-1");
                scene.lookup("#gameNav").getStyleClass().add("btn-1");
                scene.lookup("#translateNav").getStyleClass().add("btn-22");
            }
            case GAME -> {
                scene.lookup("#homeNav").getStyleClass().add("btn-1");
                scene.lookup("#gameNav").getStyleClass().add("btn-22");
                scene.lookup("#translateNav").getStyleClass().add("btn-1");
            }
            case USER -> {
                scene.lookup("#homeNav").getStyleClass().add("btn-1");
                scene.lookup("#gameNav").getStyleClass().add("btn-1");
                scene.lookup("#translateNav").getStyleClass().add("btn-1");
            }
        }

        scene.lookup("#gameNav").setOnMouseClicked(event -> primaryWindow.setSceneType(SceneEnum.GAME));
        scene.lookup("#homeNav").setOnMouseClicked(event -> primaryWindow.setSceneType(SceneEnum.HOME));
        scene.lookup("#translateNav").setOnMouseClicked(event -> primaryWindow.setSceneType(SceneEnum.TRANSLATE));
        scene.lookup("#userNav").setOnMouseClicked(event -> primaryWindow.setSceneType(SceneEnum.USER));


        Image image = new Image(getClass().getResourceAsStream("userImg.jpg"));
        ImagePattern imagePattern = new ImagePattern(image);
        ((Circle) scene.lookup("#userNav")).setFill(imagePattern);
    }
}
