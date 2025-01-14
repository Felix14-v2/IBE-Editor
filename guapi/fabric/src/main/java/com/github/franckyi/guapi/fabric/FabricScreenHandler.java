package com.github.franckyi.guapi.fabric;

import com.github.franckyi.gameadapter.api.client.IMatrices;
import com.github.franckyi.guapi.api.ScreenHandler;
import com.github.franckyi.guapi.base.AbstractScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public final class FabricScreenHandler extends AbstractScreenHandler {
    public static final ScreenHandler INSTANCE = new FabricScreenHandler();
    private final Screen screen = new ScreenImpl();
    private Screen oldScreen;

    private FabricScreenHandler() {
    }

    @Override
    protected void openScreen() {
        oldScreen = MinecraftClient.getInstance().currentScreen;
        MinecraftClient.getInstance().setScreen(screen);
    }

    @Override
    protected void closeScreen() {
        MinecraftClient.getInstance().setScreen(oldScreen);
    }

    @Override
    public Screen getScreen() {
        return screen;
    }

    private final class ScreenImpl extends Screen {
        private ScreenImpl() {
            super(new LiteralText(""));
        }

        @Override
        public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
            if (!currentSceneProperty().hasValue()) return;
            if (getCurrentScene().isTexturedBackground()) {
                renderBackgroundTexture(0);
            } else {
                renderBackground(matrices);
            }
            FabricScreenHandler.this.render((IMatrices) matrices, mouseX, mouseY, partialTicks);
        }

        @Override
        public void tick() {
            FabricScreenHandler.this.tick();
        }

        @Override
        protected void init() {
            FabricScreenHandler.this.updateSize(width, height);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            return FabricScreenHandler.this.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return FabricScreenHandler.this.mouseReleased(mouseX, mouseY, button);
        }

        @Override
        public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
            return FabricScreenHandler.this.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }

        @Override
        public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
            return FabricScreenHandler.this.mouseScrolled(mouseX, mouseY, amount);
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            return FabricScreenHandler.this.keyPressed(keyCode, scanCode, modifiers);
        }

        @Override
        public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
            return FabricScreenHandler.this.keyReleased(keyCode, scanCode, modifiers);
        }

        @Override
        public boolean charTyped(char chr, int modifiers) {
            return FabricScreenHandler.this.charTyped(chr, modifiers);
        }

        @Override
        public void mouseMoved(double mouseX, double mouseY) {
            FabricScreenHandler.this.mouseMoved(mouseX, mouseY);
        }
    }
}
