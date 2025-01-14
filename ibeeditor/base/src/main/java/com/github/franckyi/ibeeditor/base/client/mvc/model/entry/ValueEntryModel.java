package com.github.franckyi.ibeeditor.base.client.mvc.model.entry;

import com.github.franckyi.databindings.api.ObjectProperty;
import com.github.franckyi.gameadapter.api.common.text.IText;
import com.github.franckyi.ibeeditor.base.client.mvc.model.CategoryModel;

import java.util.function.Consumer;

public abstract class ValueEntryModel<T> extends LabeledEntryModel {
    protected T defaultValue;
    private final ObjectProperty<T> valueProperty;
    protected final Consumer<T> action;

    public ValueEntryModel(CategoryModel category, IText label, T value, Consumer<T> action) {
        super(category, label);
        defaultValue = value;
        valueProperty = ObjectProperty.create(value);
        this.action = action;
    }

    @Override
    public void reset() {
        valueProperty.setValue(defaultValue);
    }

    public T getValue() {
        return valueProperty().getValue();
    }

    public ObjectProperty<T> valueProperty() {
        return valueProperty;
    }

    public void setValue(T value) {
        valueProperty().setValue(value);
    }

    @Override
    public void apply() {
        action.accept(getValue());
        defaultValue = getValue();
    }
}
