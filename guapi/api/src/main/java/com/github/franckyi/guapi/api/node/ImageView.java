package com.github.franckyi.guapi.api.node;

import com.github.franckyi.databindings.api.IntegerProperty;
import com.github.franckyi.databindings.api.ObjectProperty;
import com.github.franckyi.gameadapter.api.common.IIdentifier;

public interface ImageView extends Control {
    default IIdentifier getTextureId() {
        return textureIdProperty().getValue();
    }

    ObjectProperty<IIdentifier> textureIdProperty();

    default void setTextureId(IIdentifier value) {
        textureIdProperty().setValue(value);
    }

    default int getImageX() {
        return imageXProperty().getValue();
    }

    IntegerProperty imageXProperty();

    default void setImageX(int value) {
        imageXProperty().setValue(value);
    }

    default int getImageY() {
        return imageYProperty().getValue();
    }

    IntegerProperty imageYProperty();

    default void setImageY(int value) {
        imageYProperty().setValue(value);
    }

    default int getImageWidth() {
        return imageWidthProperty().getValue();
    }

    IntegerProperty imageWidthProperty();

    default void setImageWidth(int value) {
        imageWidthProperty().setValue(value);
    }

    default int getImageHeight() {
        return imageHeightProperty().getValue();
    }

    IntegerProperty imageHeightProperty();

    default void setImageHeight(int value) {
        imageHeightProperty().setValue(value);
    }
}
