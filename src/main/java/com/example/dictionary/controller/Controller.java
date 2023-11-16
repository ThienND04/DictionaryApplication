package com.example.dictionary.controller;

import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.stage.PrimaryWindow;

public class Controller {
    public static void handleChangeUser() {
        HomeController.getInstance().handleLogin();
        GameController.getInstance().handleLogin();
        HomeController.getInstance().handleLogin();
        TranslateController.getInstance().handleLogin();
        UserController.getInstance().handleLogin();

        PrimaryWindow.getInstance().changeScene(SceneEnum.HOME);
    }
    public static void handleChangeImage() {
        HomeController.getInstance().initUserImage();
        GameController.getInstance().initUserImage();
        TranslateController.getInstance().initUserImage();
        UserController.getInstance().initUserImage();
    }

    public static void handleChangeStatics() {
        UserController.getInstance().initAchievements();
    }
}
