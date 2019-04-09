package com.github.franckyi.ibeeditor.editor;

import com.github.franckyi.guapi.group.HBox;
import com.github.franckyi.guapi.node.ListExtended;
import com.github.franckyi.ibeeditor.node.TexturedButton;
import net.minecraft.util.text.TextFormatting;

import java.util.function.Consumer;

public abstract class AbstractProperty<T> extends ListExtended.NodeEntry<HBox> {

    protected final TexturedButton resetButton;
    protected final T initialValue;
    protected final Consumer<T> action;

    public AbstractProperty(T initialValue, Consumer<T> action) {
        super(new HBox());
        this.initialValue = initialValue;
        this.action = action;
        this.build();
        this.setValue(initialValue);
        this.getNode().getChildren().add(resetButton = new TexturedButton("reset.png", TextFormatting.YELLOW + "Reset to default"));
        resetButton.getOnMouseClickedListeners().add(e -> this.setValue(initialValue));
    }

    @Override
    public void updateChildrenPos() {
        super.updateChildrenPos();
        if (this.getList() != null) {
            this.updateSize(this.getList().getWidth());
        }
    }

    protected void updateSize(int listWidth) {
    }

    protected abstract T getValue();

    protected abstract void setValue(T value);

    protected abstract void build();

    public void apply() {
        action.accept(this.getValue());
    }

}
