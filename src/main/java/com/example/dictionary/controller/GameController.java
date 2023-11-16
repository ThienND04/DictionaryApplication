package com.example.dictionary.controller;

import com.example.dictionary.Application;
import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.stage.PrimaryWindow;
import com.example.dictionary.stage.WindowEnum;
import com.example.dictionary.user.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class GameController {
    @FXML
    Circle userNav;
    @FXML
    Label homeNav;
    @FXML
    Label translateNav;
    @FXML
    Button game1Nav;
    @FXML
    Button game2Nav;
    @FXML
    Button game3Nav;

    @FXML
    public void initialize() {
        instance = this;
        translateNav.setOnMouseClicked(e -> PrimaryWindow.getInstance().changeScene(SceneEnum.TRANSLATE));
        homeNav.setOnMouseClicked(e -> PrimaryWindow.getInstance().changeScene(SceneEnum.HOME));
        userNav.setOnMouseClicked(e -> PrimaryWindow.getInstance().changeScene(SceneEnum.USER));
        game2Nav.setOnAction(event -> Application.getInstance().showWindow(WindowEnum.GAME_2));
        game1Nav.setOnAction(event -> Application.getInstance().showWindow(WindowEnum.GAME_1));
        game3Nav.setOnAction(event -> Application.getInstance().showWindow(WindowEnum.GAME_3));
    }

    public void handleLogin() {
        initUserImage();
    }

    public void initUserImage() {
        ImagePattern imagePattern = new ImagePattern(UserManager.getInstance().getCurrentUser().getImage());
        userNav.setFill(imagePattern);
    }

    private static GameController instance;
    public static GameController getInstance() {
        return instance;
    }
}
