package com.example.dictionary.scene;

import com.example.dictionary.DataList;
import com.example.dictionary.Dictionary;
import com.example.dictionary.Word;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;

public final class MyListScene extends AScene {
    @FXML
    private Button homeNav;
    @FXML
    private Button gameNav;
    @FXML
    private Button myListNav;
    @FXML
    ListView<String> listView;
    @FXML
    WebView definitionView;

    public MyListScene(Dictionary dictionary) throws Exception{
        super(dictionary, "my-list-view.fxml");

        this.gameNav = (Button) this.scene.lookup("#gameNav");
        this.homeNav = (Button) this.scene.lookup("#homeNav");
        this.myListNav = (Button) this.scene.lookup("#myListNav");
        this.listView = (ListView<String>) this.scene.lookup("#listView");
        this.definitionView = (WebView) this.scene.lookup("#definitionView");
        gameNav.setOnAction(event -> dictionary.setSceneType(SceneType.GAME));
        homeNav.setOnAction(event -> dictionary.setSceneType(SceneType.HOME));

        instance = this;
    }

    public String getSelectedWord() {
        String selectedWord = listView.getSelectionModel().getSelectedItem();
        if(selectedWord == null) selectedWord = "";
        return selectedWord;
    }

    @Override
    public void update() {
        Word selectedWord = DataList.getInstance().getMyListData().get(getSelectedWord());
        if(selectedWord != null) {
            String definition = selectedWord.getDef();
            definitionView.getEngine().loadContent(definition, "text/html");
        }
    }

    private static MyListScene instance;
    public static MyListScene getInstance() {
        return instance;
    }
}
