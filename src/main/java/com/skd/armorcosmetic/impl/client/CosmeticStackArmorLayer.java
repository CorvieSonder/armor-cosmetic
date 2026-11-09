package com.skd.armorcosmetic.impl.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.item.ItemStack;

public class CosmeticStackArmorLayer extends RenderLayer<AvatarRenderState, PlayerModel> {

    private final HumanoidArmorLayer<AvatarRenderState, PlayerModel, PlayerModel> delegate;

    public CosmeticStackArmorLayer(RenderLayerParent<AvatarRenderState, PlayerModel> renderer,
                                   HumanoidArmorLayer<AvatarRenderState, PlayerModel, PlayerModel> delegate) {
        super(renderer);
        this.delegate = delegate;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords,
                       AvatarRenderState state, float yRot, float xRot) {
        ItemStack[] cos = state.getRenderData(StackedCosmetics.KEY);
        if (cos == null) return;

        ItemStack feet = state.feetEquipment;
        ItemStack legs = state.legsEquipment;
        ItemStack chest = state.chestEquipment;
        ItemStack head = state.headEquipment;
        try {
            state.feetEquipment = orEmpty(cos[0]);
            state.legsEquipment = orEmpty(cos[1]);
            state.chestEquipment = orEmpty(cos[2]);
            state.headEquipment = orEmpty(cos[3]);
            delegate.submit(poseStack, submitNodeCollector, lightCoords, state, yRot, xRot);
        } finally {
            state.feetEquipment = feet;
            state.legsEquipment = legs;
            state.chestEquipment = chest;
            state.headEquipment = head;
        }
    }

    private static ItemStack orEmpty(ItemStack stack) {
        return stack == null ? ItemStack.EMPTY : stack;
    }
}
