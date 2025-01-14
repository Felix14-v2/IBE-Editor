package com.github.franckyi.guapi.base;

import com.github.franckyi.databindings.api.IntegerProperty;
import com.github.franckyi.databindings.api.ObjectProperty;
import com.github.franckyi.gameadapter.Game;
import com.github.franckyi.gameadapter.api.client.IMatrices;
import com.github.franckyi.guapi.Guapi;
import com.github.franckyi.guapi.api.ScreenHandler;
import com.github.franckyi.guapi.api.event.ScreenEvent;
import com.github.franckyi.guapi.api.node.Scene;
import com.github.franckyi.guapi.api.util.ScreenEventType;
import com.github.franckyi.guapi.base.event.*;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class AbstractScreenHandler implements ScreenHandler {
    private final Deque<Scene> scenes = new ArrayDeque<>();
    private final ObjectProperty<Scene> currentSceneProperty = ObjectProperty.create();
    private final IntegerProperty widthProperty = IntegerProperty.create();
    private final IntegerProperty heightProperty = IntegerProperty.create();

    public AbstractScreenHandler() {
        currentSceneProperty().addListener((oldVal, newVal) -> {
            if (newVal == null) {
                if (oldVal != null) {
                    oldVal.widthProperty().unbind();
                    oldVal.heightProperty().unbind();
                    oldVal.hide();
                }
                closeScreen();
            } else {
                if (oldVal == null) {
                    openScreen();
                } else {
                    oldVal.hide();
                }
                newVal.widthProperty().unbind();
                newVal.widthProperty().bind(widthProperty);
                newVal.heightProperty().unbind();
                newVal.heightProperty().bind(heightProperty);
                newVal.show();
            }
        });
    }

    @Override
    public void showScene(Scene scene) {
        scenes.push(scene);
        setCurrentScene(scene);
    }

    @Override
    public void hideScene() {
        if (!scenes.isEmpty()) {
            scenes.pop();
            setCurrentScene(scenes.peek());
        }
    }

    public Scene getCurrentScene() {
        return currentSceneProperty().getValue();
    }

    public ObjectProperty<Scene> currentSceneProperty() {
        return currentSceneProperty;
    }

    public void setCurrentScene(Scene value) {
        currentSceneProperty().setValue(value);
    }

    protected abstract void openScreen();

    protected abstract void closeScreen();

    public void render(IMatrices matrices, int mouseX, int mouseY, float delta) {
        try {
            getCurrentScene().render(matrices, mouseX, mouseY, delta);
        } catch (Exception e) {
            Guapi.getExceptionHandler().handleRenderException(e, matrices, mouseX, mouseY, delta, getCurrentScene());
            Game.getDefaultLogger().error(Guapi.LOG_MARKER, "Error while rendering GUAPI Scene", e);
            hideScene();
        }
    }

    public void tick() {
        if (currentSceneProperty().hasValue()) {
            try {
                getCurrentScene().tick();
            } catch (Exception e) {
                Guapi.getExceptionHandler().handleTickException(e, getCurrentScene());
                Game.getDefaultLogger().error(Guapi.LOG_MARKER, "Error while ticking GUAPI Scene", e);
                hideScene();
            }
        }
    }

    public void updateSize(int width, int height) {
        widthProperty.setValue(width);
        heightProperty.setValue(height);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return handleEvent(ScreenEventType.MOUSE_CLICKED, new MouseButtonEventImpl(mouseX, mouseY, button));
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return handleEvent(ScreenEventType.MOUSE_RELEASED, new MouseButtonEventImpl(mouseX, mouseY, button));
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return handleEvent(ScreenEventType.MOUSE_DRAGGED, new MouseDragEventImpl(mouseX, mouseY, button, deltaX, deltaY));
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return handleEvent(ScreenEventType.MOUSE_SCOLLED, new MouseScrollEventImpl(mouseX, mouseY, amount));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return handleEvent(ScreenEventType.KEY_PRESSED, new KeyEventImpl(keyCode, scanCode, modifiers));
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return handleEvent(ScreenEventType.KEY_RELEASED, new KeyEventImpl(keyCode, scanCode, modifiers));
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return handleEvent(ScreenEventType.CHAR_TYPED, new TypeEventImpl(chr, modifiers));
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        handleEvent(ScreenEventType.MOUSE_MOVED, new MouseEventImpl(mouseX, mouseY));
    }

    protected <E extends ScreenEvent> boolean handleEvent(ScreenEventType<E> type, E event) {
        if (!currentSceneProperty().hasValue()) return false;
        try {
            getCurrentScene().handleEvent(type, event);
        } catch (Exception e) {
            Guapi.getExceptionHandler().handleEventException(e, type, event, getCurrentScene());
            Game.getDefaultLogger().error(Guapi.LOG_MARKER, "Error while handling " + type.getName() + " event on GUAPI Scene", e);
            hideScene();
        }
        return event.isConsumed();
    }
}
