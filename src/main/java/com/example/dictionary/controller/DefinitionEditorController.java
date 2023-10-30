package com.example.dictionary.controller;

import com.example.dictionary.DataList;
import com.example.dictionary.Dictionary;
import com.example.dictionary.scene.SceneType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.HTMLEditor;

public class DefinitionEditorController {
    public static DefinitionEditorController instance;
    public static DefinitionEditorController getInstance() {
        return instance;
    }

    @FXML
    Button exitBtn;
    @FXML
    Button saveBtn;
    @FXML
    HTMLEditor definitionEditor;

    @FXML
    public void initialize() {
        instance = this;
    }

    @FXML
    public void handleExitBtn() {
        Dictionary.getInstance().setSceneType(SceneType.MY_LIST);
    }

    @FXML
    public void handleBtnSave() {
        DataList.getInstance().getMyListData().get(
                MyListController.getInstance().listView.getSelectionModel().getSelectedItem()
        ).setDef(definitionEditor.getHtmlText());
        DataList.getInstance().writeListData();
    }

    /**
     * Assign a string to the text in text editor.
     * @param s a string
     */
    public void updateEditorText(String s) {
        definitionEditor.setHtmlText(s);
    }
}
