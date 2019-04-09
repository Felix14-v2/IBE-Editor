package com.github.franckyi.ibeeditor.editor.property;

import com.github.franckyi.ibeeditor.node.TexturedButton;
import net.minecraft.util.text.TextFormatting;

import java.util.function.Consumer;

public class FormattedTextProperty extends StringProperty {

    protected TexturedButton formatButton;

    public FormattedTextProperty(String name, String value, Consumer<String> action) {
        super(name, value.startsWith("§r") ? value.substring(2) : value, action);
    }

    @Override
    protected void build() {
        super.build();
        this.getNode().getChildren().add(formatButton = new TexturedButton("format.png", TextFormatting.AQUA + "Format"));
        formatButton.getOnMouseClickedListeners().add(e -> {
            textField.setText(textField.getText() + "§");
            textField.getView().setFocused(true);
        });
    }

    @Override
    public void apply() {
        action.accept("§r" + this.getValue());
    }

    @Override
    protected void updateSize(int listWidth) {
        textField.setPrefWidth(listWidth - 136);
    }

}
