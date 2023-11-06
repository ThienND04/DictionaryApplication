package com.example.dictionary.controller;

import com.example.dictionary.Application;
import com.example.dictionary.stage.WindowEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameController {
    @FXML
    Button game1Nav;
    @FXML
    Button game2Nav;
    @FXML
    Button game3Nav;

    @FXML
    public void initialize() {
        game2Nav.setOnAction(event -> Application.getInstance().showWindow(WindowEnum.GAME_2));
        game1Nav.setOnAction(event -> Application.getInstance().showWindow(WindowEnum.GAME_1));
        game3Nav.setOnAction(event -> Application.getInstance().showWindow(WindowEnum.GAME_3));
    }
}
