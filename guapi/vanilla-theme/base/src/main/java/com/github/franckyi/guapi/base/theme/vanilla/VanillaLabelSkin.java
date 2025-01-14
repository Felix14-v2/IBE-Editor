package com.github.franckyi.guapi.base.theme.vanilla;

import com.github.franckyi.gameadapter.api.client.IMatrices;
import com.github.franckyi.gameadapter.api.client.IRenderer;
import com.github.franckyi.gameadapter.api.common.text.IText;
import com.github.franckyi.guapi.api.node.Label;
import com.github.franckyi.guapi.api.theme.Skin;
import com.github.franckyi.guapi.api.util.Align;
import com.github.franckyi.guapi.base.theme.AbstractSkin;

public class VanillaLabelSkin extends AbstractSkin<Label> {
    public static final Skin<Label> INSTANCE = new VanillaLabelSkin();

    private VanillaLabelSkin() {
    }

    @Override
    public void render(Label node, IMatrices matrices, int mouseX, int mouseY, float delta) {
        super.render(node, matrices, mouseX, mouseY, delta);
        renderText(node, matrices, mouseX, mouseY, delta);
    }

    protected void renderText(Label node, IMatrices matrices, int mouseX, int mouseY, float delta) {
        IRenderer renderer = IRenderer.get();
        IText text = node.getLabel();
        int x = Align.getAlignedX(node.getTextAlign().getHorizontalAlign(), node, renderer.getFontWidth(text));
        int y = Align.getAlignedY(node.getTextAlign().getVerticalAlign(), node, renderer.getFontHeight(text));
        renderer.drawString(matrices, text, x, y, 0xffffff, node.hasShadow());
    }

    @Override
    public int computeWidth(Label node) {
        return IRenderer.get().getFontWidth(node.getLabel());
    }

    @Override
    public int computeHeight(Label node) {
        return IRenderer.get().getFontHeight(node.getLabel()) - 1;
    }
}
