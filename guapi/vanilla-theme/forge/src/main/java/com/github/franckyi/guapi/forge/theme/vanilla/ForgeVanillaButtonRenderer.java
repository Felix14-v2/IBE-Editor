package com.github.franckyi.guapi.forge.theme.vanilla;

import com.github.franckyi.guapi.api.node.Button;
import com.github.franckyi.guapi.api.node.EnumButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;

public class ForgeVanillaButtonRenderer extends ExtendedButton implements ForgeVanillaDelegateRenderer {
    private final boolean isEnumButton;

    public ForgeVanillaButtonRenderer(Button node) {
        super(node.getX(), node.getY(), node.getWidth(), node.getHeight(), (ITextComponent) node.getLabel(), button -> {
        });
        initLabeled(node, this);
        isEnumButton = node instanceof EnumButton;
    }

    @Override
    protected boolean isValidClickButton(int button) {
        return super.isValidClickButton(button) || (isEnumButton && button == 1);
    }
}
