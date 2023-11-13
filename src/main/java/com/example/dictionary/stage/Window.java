package com.example.dictionary.stage;

import javafx.stage.Stage;

public abstract class Window {
    protected Stage window;

    public void show() {
        window.show();
    }

    public void show(double x, double y) {
        window.show();
    }

    public void hide() {
        window.hide();
    }

    public Stage getWindow() {
        return window;
    }

    public Window() {

    }
}
