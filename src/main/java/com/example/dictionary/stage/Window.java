package com.example.dictionary.stage;

import javafx.stage.Stage;


public abstract class Window {
    protected Stage window;
    public void show() {
        window.show();
    }
    public void hide() {
        window.hide();
    }
}
