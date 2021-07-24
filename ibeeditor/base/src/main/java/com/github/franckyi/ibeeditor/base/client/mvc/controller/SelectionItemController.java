package com.github.franckyi.ibeeditor.base.client.mvc.controller;

import com.github.franckyi.guapi.api.mvc.AbstractController;
import com.github.franckyi.ibeeditor.base.client.mvc.model.SelectionItemModel;
import com.github.franckyi.ibeeditor.base.client.mvc.view.SelectionItemView;

import static com.github.franckyi.guapi.GuapiHelper.*;

public class SelectionItemController<M extends SelectionItemModel, V extends SelectionItemView> extends AbstractController<M, V> {
    public SelectionItemController(M model, V view) {
        super(model, view);
    }

    @Override
    public void bind() {
        view.getNameLabel().setLabel(translated(model.getName()).bold());
        view.getIdLabel().setLabel(text(model.getId()).italic());
    }
}