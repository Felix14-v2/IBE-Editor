package com.github.franckyi.ibeeditor.editor;

import com.github.franckyi.guapi.Group;
import com.github.franckyi.ibeeditor.node.TexturedButton;

public interface OrderableEditablePropertyListChild extends EditablePropertyListChild {

    @Override
    OrderableListControls getControls();

    @Override
    default void build() {
        Group root = this.getNode();
        int i = root.getChildren().size() - 1;
        getControls().getUp().setPrefSize(20, 20);
        getControls().getUp().getOnMouseClickedListeners().add(event -> getControls().getCategory().swapProperties(getControls().getIndex() - 1, getControls().getIndex()));
        getControls().getDown().setPrefSize(20, 20);
        getControls().getDown().getOnMouseClickedListeners().add(event -> getControls().getCategory().swapProperties(getControls().getIndex(), getControls().getIndex() + 1));
        root.getChildren().add(i++, getControls().getUp());
        root.getChildren().add(i, getControls().getDown());
        EditablePropertyListChild.super.build();
    }

    @Override
    default void update(int newIndex) {
        EditablePropertyListChild.super.update(newIndex);
        getControls().getUp().setVisible(getControls().getIndex() != 0);
        getControls().getDown().setVisible(getControls().getCategory().getPropertyCount() != getControls().getIndex() + 1);
    }

    class OrderableListControls extends ListControls {

        private final TexturedButton up;
        private final TexturedButton down;

        public OrderableListControls(EditablePropertyList category, int index) {
            super(category, index);
            this.up = new TexturedButton("arrow_up.png", "Move up");
            this.down = new TexturedButton("arrow_down.png", "Move down");
        }

        public TexturedButton getUp() {
            return up;
        }

        public TexturedButton getDown() {
            return down;
        }

    }

}
