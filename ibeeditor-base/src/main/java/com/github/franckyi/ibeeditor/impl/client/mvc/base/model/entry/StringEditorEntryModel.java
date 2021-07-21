package com.github.franckyi.ibeeditor.impl.client.mvc.base.model.entry;

import com.github.franckyi.gameadapter.api.common.text.Text;
import com.github.franckyi.ibeeditor.api.client.mvc.base.model.EditorCategoryModel;

import java.util.function.Consumer;

public class StringEditorEntryModel extends ValueEditorEntryModel<String> {
    public StringEditorEntryModel(EditorCategoryModel category, Text label, String value, Consumer<String> action) {
        super(category, label, value, action);
    }

    @Override
    public Type getType() {
        return Type.STRING;
    }
}