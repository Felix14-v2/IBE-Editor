package com.github.franckyi.ibeeditor.base.client.mvc.model.entry;

import com.github.franckyi.ibeeditor.base.client.mvc.model.CategoryModel;
import com.github.franckyi.ibeeditor.base.client.mvc.model.category.ItemHideFlagsCategoryModel;
import com.github.franckyi.ibeeditor.base.common.ModTexts;

import java.util.function.Consumer;

import static com.github.franckyi.guapi.GuapiHelper.text;

public class HideFlagEntryModel extends BooleanEntryModel {
    private final ItemHideFlagsCategoryModel.HideFlag hideFlag;

    public HideFlagEntryModel(CategoryModel category, ItemHideFlagsCategoryModel.HideFlag hideFlag, boolean value, Consumer<Boolean> action) {
        super(category, ModTexts.hide(hideFlag.getName()).with(label -> {
            if (hideFlag == ItemHideFlagsCategoryModel.HideFlag.OTHER) {
                label.extra(text("*"));
            }
        }), value, action);
        this.hideFlag = hideFlag;
    }

    public ItemHideFlagsCategoryModel.HideFlag getHideFlag() {
        return hideFlag;
    }

    @Override
    public Type getType() {
        return Type.HIDE_FLAG;
    }
}
