package com.github.franckyi.gamehooks.impl.client;

import com.github.franckyi.gamehooks.api.client.Matrices;
import net.minecraft.client.util.math.MatrixStack;

public class FabricMatrices implements Matrices {
    private static FabricMatrices last;
    private final MatrixStack matrices;

    private FabricMatrices(MatrixStack matrices) {
        this.matrices = matrices;
        last = this;
    }

    public static Matrices of(MatrixStack matrices) {
        return last != null && matrices == last.matrices ? last : new FabricMatrices(matrices);
    }

    @Override
    @SuppressWarnings("unchecked")
    public MatrixStack getMatrixStack() {
        return matrices;
    }
}
