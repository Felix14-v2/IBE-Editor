package com.github.franckyi.gameadapter.fabric.mixin;

import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Slot.class)
public interface FabricSlotMixin {
    @Accessor("index")
    int getIndex();
}