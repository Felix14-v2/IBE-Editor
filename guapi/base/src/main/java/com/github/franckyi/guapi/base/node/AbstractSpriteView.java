package com.github.franckyi.guapi.base.node;

import com.github.franckyi.databindings.api.IntegerProperty;
import com.github.franckyi.databindings.api.ObjectProperty;
import com.github.franckyi.gameadapter.api.client.ISprite;
import com.github.franckyi.guapi.api.node.SpriteView;

import java.util.function.Supplier;

public abstract class AbstractSpriteView extends AbstractControl implements SpriteView {
    private final ObjectProperty<Supplier<ISprite>> spriteFactoryProperty = ObjectProperty.create();
    private final IntegerProperty imageWidthProperty = IntegerProperty.create();
    private final IntegerProperty imageHeightProperty = IntegerProperty.create();
    private ISprite cachedSprite;

    protected AbstractSpriteView() {
        spriteFactoryProperty().addListener(() -> cachedSprite = null);
    }

    protected AbstractSpriteView(Supplier<ISprite> spriteFactory) {
        this();
        setSpriteFactory(spriteFactory);
    }

    protected AbstractSpriteView(Supplier<ISprite> spriteFactory, int imageWidth, int imageHeight) {
        this(spriteFactory);
        setImageWidth(imageWidth);
        setImageHeight(imageHeight);
        setPrefWidth(imageWidth);
        setPrefHeight(imageHeight);
    }

    @Override
    public ObjectProperty<Supplier<ISprite>> spriteFactoryProperty() {
        return spriteFactoryProperty;
    }

    @Override
    public ISprite getSprite() {
        if (cachedSprite == null) {
            cachedSprite = getSpriteFactory().get();
        }
        return cachedSprite;
    }

    @Override
    public IntegerProperty imageWidthProperty() {
        return imageWidthProperty;
    }

    @Override
    public IntegerProperty imageHeightProperty() {
        return imageHeightProperty;
    }
}
