package com.example.dictionary.controller;

import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.stage.PrimaryWindow;
import com.example.dictionary.user.UserManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ThemeController extends MainController{
    private static ThemeController instance;
    public static ThemeController getInstance() {
        return instance;
    }
    @FXML
    private HBox container;
    @FXML
    private ImageView img1;
    @FXML
    private ImageView img2;
    @FXML
    private ImageView img3;
    @FXML
    private ImageView img4;
    @FXML
    private ImageView img5;
    @FXML
    private ImageView img6;
    @FXML
    private Button btn;
    private int t = 0;

    @Override
    public void initialize() {
        super.initialize();
        instance = this;
    }

    @Override
    protected void handleUserChange() {

        super.handleUserChange();
        container.getChildren().get(t).getStyleClass().clear();
        t = UserManager.getInstance().getCurrentUser().getTheme();
        container.getChildren().get(t).getStyleClass().add("btn-11");
    }

    @Override
    protected void initComponents() {
        super.initComponents();
        img1.setImage(new Image(getClass().getResourceAsStream("theme1.png")));
        img2.setImage(new Image(getClass().getResourceAsStream("theme1.png")));
        img3.setImage(new Image(getClass().getResourceAsStream("theme1.png")));
        img4.setImage(new Image(getClass().getResourceAsStream("theme1.png")));
        img5.setImage(new Image(getClass().getResourceAsStream("theme1.png")));
        img6.setImage(new Image(getClass().getResourceAsStream("theme1.png")));
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        for(int i = 0; i < container.getChildren().size(); i++) {
            Node temp  = container.getChildren().get(i);
            int finalI = i;
            temp.setOnMouseClicked(e -> {
                container.getChildren().get(t).getStyleClass().clear();
                temp.getStyleClass().add("btn-11");
                t = finalI;
            });
        }

        btn.setOnAction(e -> {
            UserManager.getInstance().getCurrentUser().setTheme(t);
            PrimaryWindow.getInstance().changeScene(SceneEnum.HOME);
        });
    }
}
