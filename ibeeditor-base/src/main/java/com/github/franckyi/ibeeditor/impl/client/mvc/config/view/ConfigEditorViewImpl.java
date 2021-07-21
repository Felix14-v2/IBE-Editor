package com.github.franckyi.ibeeditor.impl.client.mvc.config.view;

import com.github.franckyi.gameadapter.api.common.text.TranslatedText;
import com.github.franckyi.guapi.api.node.Node;
import com.github.franckyi.ibeeditor.api.client.mvc.config.view.ConfigEditorView;
import com.github.franckyi.ibeeditor.impl.client.mvc.base.view.AbstractListEditorView;

import static com.github.franckyi.guapi.GuapiHelper.*;

public class ConfigEditorViewImpl extends AbstractListEditorView implements ConfigEditorView {
    @Override
    public void build() {
        super.build();
        ((TranslatedText) getDoneButton().getLabel()).setTranslate("ibeeditor.gui.save");
    }

    @Override
    protected Node createHeader() {
        return label(translated("ibeeditor.gui.settings").aqua().bold(), true).textAlign(CENTER).prefHeight(20);
    }
}