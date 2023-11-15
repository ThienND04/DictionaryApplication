package com.example.dictionary.controller;

import com.example.dictionary.user.UserManager;
import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.stage.PrimaryWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController {
    @FXML
    private Button loginBtn;
    @FXML
    private Button createBtn;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password1;
    @FXML
    private PasswordField password2;

    @FXML
    void initialize() {
        createBtn.setOnAction(event -> {
            if (username.getText().equals("") || password1.getText().equals("") || password2.getText().equals("")) {
                new Alert(Alert.AlertType.WARNING, "Không được để trống").show();
                return;
            }

            if (password1.getText().equals(password2.getText())) {
                boolean res = UserManager.getInstance().create(username.getText(), password1.getText());
                if (res) {
                    HomeController.getInstance().loadData();
                    PrimaryWindow.getInstance().setSceneType(SceneEnum.HOME);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Tên đăng nhập đã tồn tại").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Mật khẩu không khớp").show();
            }
        });

        loginBtn.setOnAction(event -> {
            PrimaryWindow.getInstance().setSceneType(SceneEnum.LOGIN);
        });
    }

}
