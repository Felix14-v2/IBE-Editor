package com.github.franckyi.ibeeditor.base.client.mvc.model.entry;

import com.github.franckyi.databindings.api.BooleanProperty;
import com.github.franckyi.databindings.api.IntegerProperty;
import com.github.franckyi.gameadapter.api.common.IIdentifier;
import com.github.franckyi.gameadapter.api.common.RegistryHandler;
import com.github.franckyi.gameadapter.api.common.text.IText;
import com.github.franckyi.ibeeditor.base.client.ClientCache;
import com.github.franckyi.ibeeditor.base.client.mvc.model.CategoryModel;
import com.github.franckyi.ibeeditor.base.client.mvc.model.ListSelectionItemModel;
import com.github.franckyi.ibeeditor.base.common.ModTexts;

import java.util.List;

public class PotionEffectEntryModel extends SelectionEntryModel {
    private final IntegerProperty amplifierProperty, durationProperty;
    private final BooleanProperty ambientProperty, showParticlesProperty, showIconProperty;
    private final PotionEffectConsumer callback;

    public PotionEffectEntryModel(CategoryModel category, int id, int amplifier, int duration, boolean ambient, boolean showParticles, boolean showIcon, PotionEffectConsumer callback) {
        super(category, null, RegistryHandler.get().getEffectRegistry().getKeyFromId(id).toString(), s -> {
        });
        amplifierProperty = IntegerProperty.create(amplifier);
        durationProperty = IntegerProperty.create(duration);
        ambientProperty = BooleanProperty.create(ambient);
        showParticlesProperty = BooleanProperty.create(showParticles);
        showIconProperty = BooleanProperty.create(showIcon);
        this.callback = callback;
    }

    @Override
    public void apply() {
        callback.consume(RegistryHandler.get().getEffectRegistry().getIdFromKey(IIdentifier.parse(getValue())), getAmplifier(), getDuration(), isAmbient(), isShowParticles(), isShowIcon());
    }

    public int getAmplifier() {
        return amplifierProperty().getValue();
    }

    public IntegerProperty amplifierProperty() {
        return amplifierProperty;
    }

    public void setAmplifier(int value) {
        amplifierProperty().setValue(value);
    }

    public int getDuration() {
        return durationProperty().getValue();
    }

    public IntegerProperty durationProperty() {
        return durationProperty;
    }

    public void setDuration(int value) {
        durationProperty().setValue(value);
    }

    public boolean isAmbient() {
        return ambientProperty().getValue();
    }

    public BooleanProperty ambientProperty() {
        return ambientProperty;
    }

    public void setAmbient(boolean value) {
        ambientProperty().setValue(value);
    }

    public boolean isShowParticles() {
        return showParticlesProperty().getValue();
    }

    public BooleanProperty showParticlesProperty() {
        return showParticlesProperty;
    }

    public void setShowParticles(boolean value) {
        showParticlesProperty().setValue(value);
    }

    public boolean isShowIcon() {
        return showIconProperty().getValue();
    }

    public BooleanProperty showIconProperty() {
        return showIconProperty;
    }

    public void setShowIcon(boolean value) {
        showIconProperty().setValue(value);
    }

    @Override
    public Type getType() {
        return Type.POTION_EFFECT;
    }

    @Override
    public List<String> getSuggestions() {
        return ClientCache.getEffectSuggestions();
    }

    @Override
    public IText getSuggestionScreenTitle() {
        return ModTexts.EFFECTS;
    }

    @Override
    public List<? extends ListSelectionItemModel> getSelectionItems() {
        return ClientCache.getEffectSelectionItems();
    }

    @FunctionalInterface
    public interface PotionEffectConsumer {
        void consume(int id, int amplifier, int duration, boolean ambient, boolean showParticles, boolean showIcon);
    }
}
