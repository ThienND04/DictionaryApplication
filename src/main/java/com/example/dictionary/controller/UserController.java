package com.example.dictionary.controller;

import com.example.dictionary.Application;
import com.example.dictionary.achievement.AchievementEnum;
import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.stage.PrimaryWindow;
import com.example.dictionary.user.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class UserController {
    @FXML
    Label username;

    public static UserController getInstance() {
        return instance;
    }

    private static UserController instance;
    @FXML
    private Button changeImgBtn;
    @FXML
    private VBox achievements;
    @FXML
    Circle userNav;
    @FXML
    Label homeNav;
    @FXML
    Label gameNav;
    @FXML
    Label translateNav;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button removeUserBtn;
    @FXML
    private Circle userImg;
    @FXML
    private Circle menuBar;
    @FXML
    private Button changePasswordBtn;
    @FXML
    public void initialize() {
        menuBar.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("menu.png"))));
        instance = this;
        logoutBtn.setOnAction(event -> handleLogOut());
        removeUserBtn.setOnAction(event -> handleRemoveUser());
        changeImgBtn.setOnAction(event -> handleChooseImg());
        menuBar.setOnMouseClicked(e -> handleClickMenu());
        homeNav.setOnMouseClicked(e -> PrimaryWindow.getInstance().changeScene(SceneEnum.HOME));
        translateNav.setOnMouseClicked(e -> PrimaryWindow.getInstance().changeScene(SceneEnum.TRANSLATE));
        gameNav.setOnMouseClicked(e -> PrimaryWindow.getInstance().changeScene(SceneEnum.GAME));
    }

    public void handleClickMenu() {
        if (logoutBtn.isVisible()) {
            logoutBtn.setVisible(false);
            removeUserBtn.setVisible(false);
            changeImgBtn.setVisible(false);
            changePasswordBtn.setVisible(false);
        } else {
            logoutBtn.setVisible(true);
            removeUserBtn.setVisible(true);
            changeImgBtn.setVisible(true);
            changePasswordBtn.setVisible(true);
        }
    }

    public void initAchievements() {
        achievements.getChildren().clear();
        for(AchievementEnum value : AchievementEnum.values()) {
////            HBox hbox = new HBox();
////            hbox.setMaxHeight(100);
////            hbox.setMinHeight(100);
////            hbox.setSpacing(10);
////
////            hbox.getChildren().add(new ImageView(value.getImage()));
////
////            VBox vBox = new VBox();
////            vBox.setSpacing(10);
////            vBox.setAlignment(Pos.CENTER_LEFT);
////            HBox.setHgrow(vBox, Priority.ALWAYS);
////
////            Label name = new Label(value.getName());
////            name.getStyleClass().add("name");
////            HBox hbox2 = new HBox(new Label(value.getCurrent() + " / " + value.getMax()));
////            HBox.setHgrow(hbox2, Priority.ALWAYS);
////            hbox2.setAlignment(Pos.CENTER_RIGHT);
////            HBox hBox1 = new HBox(name,hbox2);
////
////            ProgressBar progressBar = new ProgressBar();
////            progressBar.setProgress((double) value.getCurrent() /value.getMax());
////            progressBar.setMaxWidth(Double.MAX_VALUE);
////
////
////            vBox.getChildren().add(hBox1);
////            vBox.getChildren().add(progressBar);
////            vBox.getChildren().add(new Label(value.getDescription()));
////
////            hbox.getChildren().add(vBox);
//
//            achievements.getChildren().add(hbox);


            FXMLLoader fxmlLoader = new FXMLLoader();
            try {
                HBox hBox = fxmlLoader.load(getClass().getResourceAsStream("achievement.fxml"));
                ((ImageView) hBox.lookup("#img")).setImage(value.getImage());
                ((Label) hBox.lookup("#name")).setText(value.getName());
                ((Label) hBox.lookup("#description")).setText(value.getDescription());
                ((Label) hBox.lookup("#detail")).setText(value.getCurrent() + "/" + value.getMax());
                ((ProgressBar) hBox.lookup("#bar")).setProgress(1.0 * value.getCurrent() / value.getMax());
                achievements.getChildren().add(hBox);
            } catch (Exception e) {

            }
        }
    }

    public void handleLogin() {
        initUserImage();
        username.setText(UserManager.getInstance().getCurrentUser().getUsername());
        initAchievements();
    }

    public void initUserImage() {
        ImagePattern imagePattern = new ImagePattern(UserManager.getInstance().getCurrentUser().getImage());
        userImg.setFill(imagePattern);
        userNav.setFill(imagePattern);
    }

    private void handleLogOut() {
        Application.getInstance().handleLogOut();
    }

    private void handleRemoveUser() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có muốn xóa tài khoản này không?");
        Optional<ButtonType> result = a.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            UserManager.getInstance().remove();
            Application.getInstance().handleLogOut();
        }
    }

    private void handleChooseImg() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        File file = fileChooser.showOpenDialog(new Stage());
        UserManager.getInstance().getCurrentUser().setImage(file.getPath());
    }
}
