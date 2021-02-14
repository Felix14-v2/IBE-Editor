package com.github.franckyi.ibeeditor.impl.client.mvc.view;

import com.github.franckyi.guapi.api.node.Label;
import com.github.franckyi.ibeeditor.api.client.mvc.view.CategoryView;

import static com.github.franckyi.guapi.GUAPIFactory.CENTER;
import static com.github.franckyi.guapi.GUAPIFactory.label;

public class CategoryViewImpl implements CategoryView {
    private final Label root;

    public CategoryViewImpl() {
        root = label().textAlign(CENTER);
    }

    @Override
    public Label getRoot() {
        return root;
    }
}