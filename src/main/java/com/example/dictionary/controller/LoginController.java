package com.example.dictionary.controller;

import com.example.dictionary.user.UserManager;
import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.stage.PrimaryWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    Button createBtn;
    @FXML
    private TextField username;
    @FXML
    private Button loginBtn;
    @FXML
    private PasswordField password;

    @FXML
    void initialize() {
        loginBtn.setOnAction(event -> {
            boolean res = UserManager.getInstance().login(username.getText(), password.getText());
            if (!res) {
                new Alert(Alert.AlertType.WARNING, "Tên đăng nhập hoặc mật khẩu không chính xác").show();
            }
        });

        createBtn.setOnAction(event -> {
            PrimaryWindow.getInstance().changeScene(SceneEnum.SIGNUP);
        });
    }
}