package com.skd.armorcosmetic.mixins;

import java.util.List;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntityRenderer.class)
public interface MixinLivingEntityRendererAccessor {

    @Accessor("layers")
    List<RenderLayer<?, ?>> armorcosmetic$getLayers();
}
