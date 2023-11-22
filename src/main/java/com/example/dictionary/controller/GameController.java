package com.example.dictionary.controller;

import com.example.dictionary.Application;
import com.example.dictionary.game.Game2;
import com.example.dictionary.game.Game3;
import com.example.dictionary.stage.WindowEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class GameController extends MainController{
    @FXML
    Button game1Nav;
    @FXML
    Button game2Nav;
    @FXML
    Button game3Nav;
    @FXML
    ImageView bgr;

    @Override
    protected void initComponents() {
        super.initComponents();
//        BackgroundSize backgroundSize = new BackgroundSize(1200, 1000, true, true, true, false);
//        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResourceAsStream("gameBG.jpg")),
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.CENTER,
//                backgroundSize);
//        window.setBackground(new Background(backgroundImage));
        bgr.setImage(new Image(getClass().getResourceAsStream("gameBG.jpg")));
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        game2Nav.setOnAction(event -> {
            Application.getInstance().showWindow(WindowEnum.GAME_2);
            currentGameId = Game2.GAME_ID;
        });
        game1Nav.setOnAction(event -> {
            Application.getInstance().showWindow(WindowEnum.GAME_1);
            currentGameId = Game2.GAME_ID;
        });
        game3Nav.setOnAction(event -> {
            Application.getInstance().showWindow(WindowEnum.GAME_3);
            currentGameId = Game3.GAME_ID;
        });
    }
    @FXML
    public void initialize() {
        super.initialize();
        instance = this;        
    }


    public static int currentGameId;

    public static int getCurrentGameId() {
        return currentGameId;
    }

    private static GameController instance;
    public static GameController getInstance() {
        return instance;
    }
}
