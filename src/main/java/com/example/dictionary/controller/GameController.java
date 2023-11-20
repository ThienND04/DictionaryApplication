package com.example.dictionary.controller;

import com.example.dictionary.Application;
import com.example.dictionary.stage.WindowEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameController extends MainController{
    @FXML
    Button game1Nav;
    @FXML
    Button game2Nav;
    @FXML
    Button game3Nav;


    @Override
    protected void initComponents() {
        super.initComponents();
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        game2Nav.setOnAction(event -> Application.getInstance().showWindow(WindowEnum.GAME_2));
        game1Nav.setOnAction(event -> Application.getInstance().showWindow(WindowEnum.GAME_1));
        game3Nav.setOnAction(event -> Application.getInstance().showWindow(WindowEnum.GAME_3));
    }

    @Override
    public void initialize() {
        super.initialize();
        instance = this;;
    }

    private static GameController instance;
    public static GameController getInstance() {
        return instance;
    }
}
