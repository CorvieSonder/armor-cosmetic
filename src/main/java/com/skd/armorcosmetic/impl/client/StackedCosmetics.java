package com.skd.armorcosmetic.impl.client;

import com.skd.armorcosmetic.ArmorCosmetic;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.util.context.ContextKey;
import net.minecraft.resources.Identifier;

public final class StackedCosmetics {
    public static final ContextKey<AvatarRenderState> KEY;

    static {
        KEY = new ContextKey<>(Identifier.fromNamespaceAndPath(ArmorCosmetic.MODID, "stacked_cosmetics"));
    }

    private StackedCosmetics() {}

}
