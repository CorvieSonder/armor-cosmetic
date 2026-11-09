package com.skd.armorcosmetic.impl.client;

import com.skd.armorcosmetic.ArmorCosmetic;
import net.minecraft.util.context.ContextKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public final class StackedCosmetics {
    public static final ContextKey<ItemStack[]> KEY;

    static {
        KEY = new ContextKey<>(Identifier.fromNamespaceAndPath(ArmorCosmetic.MODID,"stacked_cosmetics"));
    }

    private StackedCosmetics() {}

}
