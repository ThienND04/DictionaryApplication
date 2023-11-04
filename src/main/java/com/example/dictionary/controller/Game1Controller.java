package com.example.dictionary.controller;

import com.example.dictionary.DataList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game1Controller {
    @FXML
    WebView gameView;
    @FXML
    Button startBtn;

    @FXML
    public void initialize() {
        this.initComponents();
    }

    private void initComponents() {
        try {
            File file = new File("src\\main\\resources\\com\\example\\dictionary\\controller\\index.html");
            this.gameView.getEngine().setJavaScriptEnabled(true);
            this.gameView.getEngine().load(file.toURI().toURL().toString());

            startBtn.setOnAction(event -> {

                ArrayList<String> list = new ArrayList<>(DataList.getInstance().getData().keySet());
                Collections.shuffle(list);
                int r = new Random().nextInt(4);

                this.gameView.getEngine().executeScript(
                        String.format("game.start(\"%s\",[\"%s\",\"%s\",\"%s\",\"%s\"],%d)",
                                DataList.getInstance().getData().get(list.get(r)).getDef(),
                                list.get(0),
                                list.get(1),
                                list.get(2),
                                list.get(3),
                                r
                        ));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
