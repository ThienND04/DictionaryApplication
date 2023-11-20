package com.example.dictionary.controller;

import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.stage.PrimaryWindow;

public class Controller {
    public static void handleChangeUser() {
        GameController.getInstance().handleUserChange();
        HomeController.getInstance().handleUserChange();
        TranslateController.getInstance().handleUserChange();
        UserController.getInstance().handleUserChange();
        ThemeController.getInstance().handleUserChange();
        PrimaryWindow.getInstance().changeScene(SceneEnum.HOME);
    }
    public static void handleChangeImage() {
        HomeController.getInstance().initUserImage();
        GameController.getInstance().initUserImage();
        TranslateController.getInstance().initUserImage();
        UserController.getInstance().initUserImage();
    }

    public static void handleChangeStreak() {
        HomeController.getInstance().initStreak();
        GameController.getInstance().initStreak();
        TranslateController.getInstance().initStreak();
        UserController.getInstance().initStreak();
    }

    public static void handleChangeStatics() {
        UserController.getInstance().initAchievements();
        HomeController.getInstance().initDailyTask();
    }
}
