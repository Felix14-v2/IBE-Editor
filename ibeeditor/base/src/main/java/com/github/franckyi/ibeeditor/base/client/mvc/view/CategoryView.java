package com.github.franckyi.ibeeditor.base.client.mvc.view;

import com.github.franckyi.guapi.api.mvc.View;
import com.github.franckyi.guapi.api.node.Label;
import com.github.franckyi.guapi.api.node.Node;

import static com.github.franckyi.guapi.GuapiHelper.*;

public class CategoryView implements View {
    private Label root;

    @Override
    public void build() {
        root = label().shadow().textAlign(CENTER);
    }

    public Label getLabel() {
        return root;
    }

    @Override
    public Node getRoot() {
        return root;
    }
}
