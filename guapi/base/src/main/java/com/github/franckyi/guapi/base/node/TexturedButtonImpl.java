package com.github.franckyi.guapi.base.node;

import com.github.franckyi.gameadapter.api.common.IIdentifier;
import com.github.franckyi.guapi.api.node.builder.TexturedButtonBuilder;
import com.github.franckyi.guapi.api.util.NodeType;

public class TexturedButtonImpl extends AbstractTexturedButton implements TexturedButtonBuilder {
    public TexturedButtonImpl(IIdentifier textureId, boolean drawButton) {
        super(textureId, drawButton);
    }

    public TexturedButtonImpl(IIdentifier textureId, int imageWidth, int imageHeight, boolean drawButton) {
        super(textureId, imageWidth, imageHeight, drawButton);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected NodeType<?> getType() {
        return NodeType.TEXTURED_BUTTON;
    }

    @Override
    public String toString() {
        return "TexturedButton{\"" + getTextureId() + "\"}";
    }
}
