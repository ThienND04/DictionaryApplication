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
    ListView<String> listView;
    @FXML
    WebView definitionView;

    public MyListScene(Dictionary dictionary) throws Exception{
        // Bug
        super(dictionary, SceneType.MY_LIST);
        this.listView = (ListView<String>) getScene().lookup("#listView");
        this.definitionView = (WebView) getScene().lookup("#definitionView");
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
