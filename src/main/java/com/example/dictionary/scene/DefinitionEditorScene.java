package com.example.dictionary.scene;

import com.example.dictionary.DataList;
import com.example.dictionary.Dictionary;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.HTMLEditor;

public class DefinitionEditorScene extends AScene {
    public static DefinitionEditorScene instance;
    public static DefinitionEditorScene getInstance() {
        return instance;
    }

    @FXML
    HTMLEditor definitionEditor;

    public DefinitionEditorScene(Dictionary dictionary) throws Exception {
        super(dictionary, SceneType.TEXT_EDITOR);
        this.definitionEditor = (HTMLEditor) getScene().lookup("#definitionEditor");
        instance = this;
    }

    @Override
    public void update() {
        this.definitionEditor.setHtmlText(
                DataList.getInstance().getMyListData().get(
                        MyListScene.getInstance().getSelectedWord()
                ).getDef());
    }
}
