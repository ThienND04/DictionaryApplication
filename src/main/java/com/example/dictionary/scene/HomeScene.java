package com.example.dictionary.scene;

import com.example.dictionary.Dictionary;
import com.example.dictionary.Word;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class HomeScene extends AScene{
    private static final String DATA_FILE_PATH = "data/E_V.txt";
    private static final String SPLITTING_CHARACTERS = "<html>";
    private Map<String, Word> data = new HashMap<>();
    @FXML
    private ListView<String> listView;
    @FXML
    private WebView definitionView;
    @FXML
    private Button homeNav;
    @FXML
    private Button gameNav;
    @FXML
    private Button myListNav;
    public HomeScene(Dictionary dictionary) throws Exception {
        super(dictionary, "dictionary-view.fxml");
        this.initComponents();
        this.readData();
        this.loadWordList();

        this.gameNav = (Button) this.scene.lookup("#gameNav");
        this.homeNav = (Button) this.scene.lookup("#homeNav");
        this.myListNav = (Button) this.scene.lookup("#myListNav");

        gameNav.setOnAction(event -> this.dictionary.setSceneType(SceneType.GAME));
        myListNav.setOnAction(event -> this.dictionary.setSceneType(SceneType.MY_LIST));
    }

    private void initComponents() {
        this.definitionView = (WebView) this.scene.lookup("#definitionView");
        this.listView = (ListView<String>) this.scene.lookup("#listView");
        HomeScene context = this;
        this.listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    Word selectedWord = data.get(newValue.trim());
                    String definition = selectedWord.getDef();
                    context.definitionView.getEngine().loadContent(definition, "text/html");
                }
        );
    }

    private void loadWordList() {
        this.listView.getItems().addAll(data.keySet());
    }

    private void readData() throws IOException {
        FileReader fis = new FileReader(DATA_FILE_PATH);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(SPLITTING_CHARACTERS);
            String word = parts[0];
            String definition = SPLITTING_CHARACTERS + parts[1];
            Word wordObj = new Word(word, definition);
            data.put(word, wordObj);
        }
    }

}
