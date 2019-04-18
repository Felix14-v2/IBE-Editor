package com.github.franckyi.guapi.node;

import com.github.franckyi.guapi.Node;
import com.github.franckyi.guapi.Parent;
import com.github.franckyi.guapi.ScreenEventListener;
import com.github.franckyi.guapi.event.EventListener;
import com.github.franckyi.guapi.gui.GuiView;
import com.github.franckyi.guapi.math.Insets;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiSlot;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListExtended<E extends GuiListExtended.IGuiListEntry & ScreenEventListener> extends Node<ListExtended.GuiListExtendedView> implements Parent {

    private int lazySlotHeight;

    public ListExtended(int slotHeight) {
        super(new GuiListExtendedView<>(slotHeight));
        this.lazySlotHeight = slotHeight;
        this.computeSize();
        this.updateSize();
    }

    public int getSlotHeight() {
        return lazySlotHeight;
    }

    public void setSlotHeight(int slotHeight) {
        if (lazySlotHeight != slotHeight) {
            try {
                this.getView().setSlotHeight(slotHeight);
                lazySlotHeight = slotHeight;
                this.computeHeight();
                this.updateHeight();
            } catch (ObfuscationReflectionHelper.UnableToFindFieldException | ObfuscationReflectionHelper.UnableToAccessFieldException e) {
                e.printStackTrace();
            }
        }
    }

    public Insets getOffset() {
        return this.getView().getOffset();
    }

    public void setOffset(Insets offset) {
        this.getView().setOffset(offset);
    }

    @Override
    public void updateChildrenPos() {
        this.computeSize();
        this.updateSize();
    }

    @SuppressWarnings("unchecked")
    public List<E> getChildren() {
        return this.getView().getChildren();
    }

    @Override
    protected void computeWidth() {
        this.setComputedWidth(200);
    }

    @Override
    protected void computeHeight() {
        this.setComputedHeight(200);
    }

    public static class GuiListExtendedView<E extends GuiListExtended.IGuiListEntry<E>> extends GuiListExtended<E> implements GuiView {

        private Insets offset;
        private int realHeight;

        public GuiListExtendedView(int slotHeight) {
            super(ScreenEventListener.mc, 0, 0, 0, 0, slotHeight);
            offset = Insets.NONE;
        }

        public void setSlotHeight(int slotHeight) {
            ObfuscationReflectionHelper.setPrivateValue(GuiSlot.class, this, slotHeight, "field_148149_f");
        }

        public Insets getOffset() {
            return offset;
        }

        public void setOffset(Insets offset) {
            int x = this.getViewX();
            int y = this.getViewY();
            this.offset = offset;
            left = x + offset.getLeft();
            right = x + width - offset.getRight();
            top = y + offset.getTop();
            bottom = y + realHeight - offset.getBottom();
        }

        @Override
        public int getViewX() {
            return left - offset.getLeft();
        }

        @Override
        public void setViewX(int x) {
            left = x + offset.getLeft();
            right = x + width - offset.getRight();
        }

        @Override
        public int getViewY() {
            return top - offset.getTop();
        }

        @Override
        public void setViewY(int y) {
            top = y + offset.getTop();
            bottom = y + realHeight - offset.getBottom();
        }

        @Override
        public int getViewWidth() {
            return width;
        }

        @Override
        public void setViewWidth(int width) {
            this.width = width;
            right = left + width;
        }

        @Override
        public int getViewHeight() {
            return realHeight;
        }

        @Override
        public void setViewHeight(int height) {
            this.realHeight = height;
            bottom = top + height;
        }

        @Override
        public boolean isViewVisible() {
            return this.isVisible();
        }

        @Override
        public void setViewVisible(boolean visible) {
            this.visible = visible;
        }

        @Override
        public int getListWidth() {
            return width;
        }

        @Override
        protected int getScrollBarX() {
            return right - 7;
        }

        @Override
        public void renderView(int mouseX, int mouseY, float partialTicks) {
            super.drawScreen(mouseX, mouseY, partialTicks);
        }

        @Override
        public boolean keyReleased(int p_keyReleased_1_, int p_keyReleased_2_, int p_keyReleased_3_) {
            return super.keyReleased(p_keyReleased_1_, p_keyReleased_2_, p_keyReleased_3_);
        }

    }

    public static class NodeEntry<T extends Node> extends GuiListExtended.IGuiListEntry<NodeEntry<T>> implements ScreenEventListener, Parent {

        private final Set<EventListener<GuiScreenEvent.MouseClickedEvent>> onMouseClickedListeners;
        private final Set<EventListener<GuiScreenEvent.MouseReleasedEvent>> onMouseReleasedListeners;
        private final Set<EventListener<GuiScreenEvent.MouseDragEvent>> onMouseDraggedListeners;
        private final Set<EventListener<GuiScreenEvent.MouseScrollEvent>> onMouseScrolledListeners;
        private final Set<EventListener<GuiScreenEvent.KeyboardKeyPressedEvent>> onKeyPressedListeners;
        private final Set<EventListener<GuiScreenEvent.KeyboardKeyReleasedEvent>> onKeyReleasedListeners;
        private final Set<EventListener<GuiScreenEvent.KeyboardCharTypedEvent>> onCharTypedListeners;

        private final T node;

        public NodeEntry(T node) {
            this.node = node;
            node.setParent(this);
            onMouseClickedListeners = new HashSet<>();
            onMouseReleasedListeners = new HashSet<>();
            onMouseDraggedListeners = new HashSet<>();
            onMouseScrolledListeners = new HashSet<>();
            onKeyPressedListeners = new HashSet<>();
            onKeyReleasedListeners = new HashSet<>();
            onCharTypedListeners = new HashSet<>();
        }

        public T getNode() {
            return node;
        }

        @Override
        protected ListExtended.GuiListExtendedView<NodeEntry<T>> getList() {
            return ((GuiListExtendedView<NodeEntry<T>>) super.getList());
        }

        @Override
        public void drawEntry(int entryWidth, int entryHeight, int mouseX, int mouseY, boolean p_194999_5_, float partialTicks) {
            GuiListExtendedView list = this.getList();
            int width = entryWidth - list.getOffset().getHorizontal() - 9;
            if (this.getX() != node.getX() || this.getY() != node.getY())
                node.setPosition(this.getX(), this.getY());
            if (width != node.getWidth() || entryHeight != node.getHeight())
                node.setPrefSize(width, entryHeight);
            node.render(mouseX, mouseY, partialTicks);
        }

        public Set<EventListener<GuiScreenEvent.MouseClickedEvent>> getOnMouseClickedListeners() {
            return onMouseClickedListeners;
        }

        public Set<EventListener<GuiScreenEvent.MouseReleasedEvent>> getOnMouseReleasedListeners() {
            return onMouseReleasedListeners;
        }

        public Set<EventListener<GuiScreenEvent.MouseDragEvent>> getOnMouseDraggedListeners() {
            return onMouseDraggedListeners;
        }

        public Set<EventListener<GuiScreenEvent.MouseScrollEvent>> getOnMouseScrolledListeners() {
            return onMouseScrolledListeners;
        }

        public Set<EventListener<GuiScreenEvent.KeyboardKeyPressedEvent>> getOnKeyPressedListeners() {
            return onKeyPressedListeners;
        }

        public Set<EventListener<GuiScreenEvent.KeyboardKeyReleasedEvent>> getOnKeyReleasedListeners() {
            return onKeyReleasedListeners;
        }

        public Set<EventListener<GuiScreenEvent.KeyboardCharTypedEvent>> getOnCharTypedListeners() {
            return onCharTypedListeners;
        }

        @Override
        public boolean onMouseClicked(GuiScreenEvent.MouseClickedEvent event) {
            if (node.getView().isViewVisible() && node.getView().mouseClicked(event.getMouseX(), event.getMouseY(), event.getButton())) {
                ScreenEventListener.super.onMouseClicked(event);
                node.onMouseClicked(event);
                return true;
            }
            return false;
        }

        @Override
        public boolean onMouseReleased(GuiScreenEvent.MouseReleasedEvent event) {
            if (node.getView().isViewVisible() && node.getView().mouseReleased(event.getMouseX(), event.getMouseY(), event.getButton())) {
                ScreenEventListener.super.onMouseReleased(event);
                node.onMouseReleased(event);
                return true;
            }
            return false;
        }

        @Override
        public boolean onMouseDragged(GuiScreenEvent.MouseDragEvent event) {
            if (node.getView().isViewVisible() && node.getView().mouseDragged(event.getMouseX(), event.getMouseY(), event.getMouseButton(), event.getDragX(), event.getDragY())) {
                ScreenEventListener.super.onMouseDragged(event);
                node.onMouseDragged(event);
                return true;
            }
            return false;
        }

        @Override
        public boolean onMouseScrolled(GuiScreenEvent.MouseScrollEvent event) {
            if (node.getView().isViewVisible() && node.getView().mouseScrolled(event.getScrollDelta())) {
                ScreenEventListener.super.onMouseScrolled(event);
                node.onMouseScrolled(event);
                return true;
            }
            return false;
        }

        @Override
        public boolean onKeyPressed(GuiScreenEvent.KeyboardKeyPressedEvent event) {
            if (node.getView().isViewVisible() && node.getView().keyPressed(event.getKeyCode(), event.getScanCode(), event.getModifiers())) {
                ScreenEventListener.super.onKeyPressed(event);
                node.onKeyPressed(event);
                return true;
            }
            return false;
        }

        @Override
        public boolean onKeyReleased(GuiScreenEvent.KeyboardKeyReleasedEvent event) {
            if (node.getView().isViewVisible() && node.getView().keyReleased(event.getKeyCode(), event.getScanCode(), event.getModifiers())) {
                ScreenEventListener.super.onKeyReleased(event);
                node.onKeyReleased(event);
                return true;
            }
            return false;
        }

        @Override
        public boolean onCharTyped(GuiScreenEvent.KeyboardCharTypedEvent event) {
            if (node.getView().isViewVisible() && node.getView().charTyped(event.getCodePoint(), event.getModifiers())) {
                ScreenEventListener.super.onCharTyped(event);
                node.onCharTyped(event);
                return true;
            }
            return false;
        }

        @Override
        public void updateChildrenPos() {
            this.getNode().computeSize();
            this.getNode().updateSize();
            if (this.getNode() instanceof Parent) {
                ((Parent) this.getNode()).updateChildrenPos();
            }
        }

        @Override
        public List<? extends ScreenEventListener> getChildren() {
            return Collections.singletonList(node);
        }
    }
}
