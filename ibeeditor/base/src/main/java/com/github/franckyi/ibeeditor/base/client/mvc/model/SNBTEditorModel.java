package com.github.franckyi.ibeeditor.base.client.mvc.model;

import com.github.franckyi.databindings.api.StringProperty;
import com.github.franckyi.gameadapter.api.common.text.IText;
import com.github.franckyi.guapi.api.mvc.Model;

import java.util.function.Consumer;

public class SNBTEditorModel implements Model {
    private final StringProperty valueProperty;
    private final Consumer<String> action;
    private final IText disabledTooltip;

    public SNBTEditorModel(String value, Consumer<String> action, IText disabledTooltip) {
        valueProperty = StringProperty.create(value);
        this.action = action;
        this.disabledTooltip = disabledTooltip;
    }

    public String getValue() {
        return valueProperty().getValue();
    }

    public StringProperty valueProperty() {
        return valueProperty;
    }

    public void setValue(String value) {
        valueProperty().setValue(value);
    }

    public Consumer<String> getAction() {
        return action;
    }

    public IText getDisabledTooltip() {
        return disabledTooltip;
    }

    public boolean canSave() {
        return getDisabledTooltip() == null;
    }

    public void apply() {
        action.accept(getValue());
    }
}
