package com.example.dictionary.controller;

import com.example.dictionary.Application;
import com.example.dictionary.user.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class UserController {
    @FXML
    public Button logoutBtn;
    @FXML
    public Button removeUserBtn;
    @FXML
    Circle userImg;

    @FXML
    public void initialize() {
        initUserImage();
        logoutBtn.setOnAction(event -> handleLogOut());
        removeUserBtn.setOnAction(event -> handleRemoveUser());
    }

    private void initUserImage() {
        Image image = new Image(getClass().getResourceAsStream("userImg.jpg"));
        ImagePattern imagePattern = new ImagePattern(image);
        userImg.setFill(imagePattern);
    }

    private void handleLogOut() {
        UserManager.getInstance().writeData();
        Application.getInstance().handleLogOut();
    }

    private void handleRemoveUser() {
        UserManager.getInstance().remove();
        Application.getInstance().handleLogOut();
    }
}
