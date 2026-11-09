package com.skd.armorcosmetic.impl.client;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.armorcosmetic.ArmorCosmetic;
import com.skd.armorcosmetic.impl.ModConfigs;
import com.skd.armorcosmetic.mixins.MixinLivingEntityRendererAccessor;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.WingsLayer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class CosmeticStackArmorLayer extends RenderLayer<AvatarRenderState, PlayerModel> {

    private static final Set<String> EQUIPMENT_FIELDS = Set.of("headEquipment", "chestEquipment", "legsEquipment", "feetEquipment");
    private static final List<Field> POSE_FIELDS = collectPoseFields();

    private final AvatarRenderer<?> renderer;
    private final HumanoidArmorLayer<AvatarRenderState, PlayerModel, PlayerModel> armorLayer;
    private final HumanoidArmorLayer<AvatarRenderState, PlayerModel, PlayerModel> inflatedArmorLayer;
    private List<RenderLayer<AvatarRenderState, PlayerModel>> extraLayers;

    public CosmeticStackArmorLayer(AvatarRenderer<?> renderer,
                                   HumanoidArmorLayer<AvatarRenderState, PlayerModel, PlayerModel> armorLayer,
                                   HumanoidArmorLayer<AvatarRenderState, PlayerModel, PlayerModel> inflatedArmorLayer) {
        super(renderer);
        this.renderer = renderer;
        this.armorLayer = armorLayer;
        this.inflatedArmorLayer = inflatedArmorLayer;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords,
                       AvatarRenderState state, float yRot, float xRot) {
        AvatarRenderState cosmeticState = state.getRenderData(StackedCosmetics.KEY);
        if (cosmeticState == null) return;
        copyPose(state, cosmeticState);

        HumanoidArmorLayer<AvatarRenderState, PlayerModel, PlayerModel> armor =
                ModConfigs.CosArmorInflateCosmetic.get() ? inflatedArmorLayer : armorLayer;
        armor.submit(poseStack, submitNodeCollector, lightCoords, cosmeticState, yRot, xRot);

        for (RenderLayer<AvatarRenderState, PlayerModel> layer : List.copyOf(getExtraLayers())) {
            try {
                layer.submit(poseStack, submitNodeCollector, lightCoords, cosmeticState, yRot, xRot);
            } catch (RuntimeException e) {
                ArmorCosmetic.LOGGER.error("Cosmetic pass failed for layer {}, skipping it from now on", layer.getClass().getName(), e);
                extraLayers.remove(layer);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private List<RenderLayer<AvatarRenderState, PlayerModel>> getExtraLayers() {
        if (extraLayers == null) {
            List<RenderLayer<AvatarRenderState, PlayerModel>> found = new ArrayList<>();
            for (RenderLayer<?, ?> layer : ((MixinLivingEntityRendererAccessor) renderer).armorcosmetic$getLayers()) {
                if (layer instanceof CosmeticStackArmorLayer || layer instanceof HumanoidArmorLayer) continue;
                if (layer instanceof WingsLayer || layer.getClass().getSimpleName().contains("Armor")) {
                    found.add((RenderLayer<AvatarRenderState, PlayerModel>) layer);
                }
            }
            extraLayers = new ArrayList<>(found);
            ArmorCosmetic.LOGGER.info("Cosmetic armor pass will also run layers: {}",
                    extraLayers.stream().map(l -> l.getClass().getName()).toList());
        }
        return extraLayers;
    }

    private static void copyPose(AvatarRenderState from, AvatarRenderState to) {
        to.ageInTicks = from.ageInTicks;
        to.outlineColor = from.outlineColor;
        for (Field field : POSE_FIELDS) {
            try {
                field.set(to, field.get(from));
            } catch (ReflectiveOperationException | RuntimeException e) {
                ArmorCosmetic.LOGGER.debug("Could not copy render state field {}", field.getName(), e);
            }
        }
    }

    private static List<Field> collectPoseFields() {
        List<Field> fields = new ArrayList<>();
        for (Class<?> type : List.of(LivingEntityRenderState.class, HumanoidRenderState.class, AvatarRenderState.class)) {
            for (Field field : type.getDeclaredFields()) {
                int mods = field.getModifiers();
                if (!Modifier.isPublic(mods) || Modifier.isStatic(mods) || Modifier.isFinal(mods)) continue;
                if (EQUIPMENT_FIELDS.contains(field.getName())) continue;
                fields.add(field);
            }
        }
        return List.copyOf(fields);
    }
}
