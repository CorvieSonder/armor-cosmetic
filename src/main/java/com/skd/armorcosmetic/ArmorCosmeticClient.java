package com.skd.armorcosmetic;

import com.skd.armorcosmetic.impl.client.CosmeticStackArmorLayer;
import com.skd.armorcosmetic.impl.client.PlayerRenderHandler;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.PlayerModelType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.renderstate.AvatarRenderStateModifier;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;

@Mod(value = ArmorCosmetic.MODID, dist = Dist.CLIENT)
public class ArmorCosmeticClient {

    private static final ArmorModelSet<ModelLayerLocation> INFLATED_ARMOR = new ArmorModelSet<>(
            inflatedLayer("helmet"), inflatedLayer("chestplate"), inflatedLayer("leggings"), inflatedLayer("boots"));

    private static ModelLayerLocation inflatedLayer(String part) {
        return new ModelLayerLocation(ArmorCosmetic.id("player_cosmetic_armor"), part);
    }

    public ArmorCosmeticClient(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modEventBus.addListener(ArmorCosmeticClient::registerLayerDefinitions);
        modEventBus.addListener(ArmorCosmeticClient::addLayers);
        modEventBus.addListener(ArmorCosmeticClient::registerRenderStateModifiers);
    }

    private static void registerRenderStateModifiers(RegisterRenderStateModifiersEvent event) {
        event.registerAvatarEntityModifier(new AvatarRenderStateModifier() {
            @Override
            public <T extends Avatar & ClientAvatarEntity> void accept(T avatar, AvatarRenderState renderState) {
                if (avatar instanceof AbstractClientPlayer player) {
                    PlayerRenderHandler.INSTANCE.attachStackedCosmetics(player, renderState);
                }
            }
        });
    }

    private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ArmorModelSet<LayerDefinition> definitions = PlayerModel
                .createArmorMeshSet(new CubeDeformation(0.6F), new CubeDeformation(1.1F))
                .map(mesh -> LayerDefinition.create(mesh, 64, 32));
        for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            LayerDefinition definition = definitions.get(slot);
            event.registerLayerDefinition(INFLATED_ARMOR.get(slot), () -> definition);
        }
    }

    private static void addLayers(EntityRenderersEvent.AddLayers event) {
        EntityRendererProvider.Context context = event.getContext();
        for (PlayerModelType type : event.getSkins()) {
            AvatarRenderer<AbstractClientPlayer> renderer = event.getPlayerRenderer(type);
            if (renderer == null) continue;
            boolean slim = type == PlayerModelType.SLIM;
            HumanoidArmorLayer<AvatarRenderState, PlayerModel, PlayerModel> armor = new HumanoidArmorLayer<>(
                    renderer,
                    ArmorModelSet.bake(slim ? ModelLayers.PLAYER_SLIM_ARMOR : ModelLayers.PLAYER_ARMOR, context.getModelSet(), part -> new PlayerModel(part, slim)),
                    context.getEquipmentRenderer());
            HumanoidArmorLayer<AvatarRenderState, PlayerModel, PlayerModel> inflatedArmor = new HumanoidArmorLayer<>(
                    renderer,
                    ArmorModelSet.bake(INFLATED_ARMOR, context.getModelSet(), part -> new PlayerModel(part, slim)),
                    context.getEquipmentRenderer());
            renderer.addLayer(new CosmeticStackArmorLayer(renderer, armor, inflatedArmor));
        }
    }
}
