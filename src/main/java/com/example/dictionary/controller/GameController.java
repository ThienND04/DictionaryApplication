package com.example.dictionary.controller;

import com.example.dictionary.Application;
import com.example.dictionary.game.Game2;
import com.example.dictionary.game.Game3;
import com.example.dictionary.stage.WindowEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class GameController extends MainController{
    @FXML
    Button game1Nav;
    @FXML
    Button game2Nav;
    @FXML
    Button game3Nav;
    @FXML
    VBox window;

    @Override
    protected void initComponents() {
        super.initComponents();
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
