package com.example.dictionary.controller;

import javafx.fxml.FXML;

public abstract class SuperController {
    @FXML
    public void initialize() {
        initEvents();
        initComponents();
    }
    protected abstract void initComponents();
    protected abstract void initEvents();
}
