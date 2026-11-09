package com.skd.armorcosmetic.impl.client;

import java.util.ArrayDeque;
import java.util.Deque;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.skd.armorcosmetic.impl.ModConfigs;
import com.skd.armorcosmetic.impl.ModObjects;
import com.skd.armorcosmetic.impl.inventory.InventoryCosArmor;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.common.NeoForge;

public enum PlayerRenderHandler {
    INSTANCE;

    private static final EquipmentSlot[] SLOTS = new EquipmentSlot[]{
            EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD
    };
    public static boolean Disabled = false;

    private final LoadingCache<Object, Deque<Runnable>> cache = CacheBuilder.newBuilder()
            .weakKeys()
            .build(new CacheLoader<>() {
                @Override
                public Deque<Runnable> load(Object key) {
                    return new ArrayDeque<>();
                }
            });

    public void onExtractPlayerRenderState(AbstractClientPlayer player, AvatarRenderState renderState, float partialTick) {
        Deque<Runnable> restores = cache.getUnchecked(player);
        restoreItems(restores);

        Inventory playerInventory = PlayerInventoryHelper.getPlayerInventory(player);

        for (EquipmentSlot slot : SLOTS) {
            PlayerInventoryHelper.getPlayerEquipmentSlotIndex(slot).ifPresent(slotIndex -> {
                ItemStack original = playerInventory.getItem(slotIndex).copy();
                restores.add(() -> playerInventory.setItem(slotIndex, original));
            });
        }

        if (Disabled) return;

        InventoryCosArmor cosInventory = getCosInventory(player);
        boolean stacking = ModConfigs.CosArmorStackRendering.get();

        for (int i = 0; i < SLOTS.length; i++) {
            final int slot = i;
            PlayerInventoryHelper.getPlayerEquipmentSlotIndex(SLOTS[slot]).ifPresent(slotIndex -> {
                if (cosInventory.isSkinArmor(slot)) {
                    playerInventory.setItem(slotIndex, ItemStack.EMPTY);
                } else {
                    ItemStack stack = cosInventory.getStackInSlot(slot).copy();
                    if (!stack.isEmpty() && !stacking) {
                        playerInventory.setItem(slotIndex, stack);
                    }
                }
            });
        }
    }

    public void attachStackedCosmetics(AbstractClientPlayer player, AvatarRenderState renderState) {
        if (Disabled || !ModConfigs.CosArmorStackRendering.get()) return;

        InventoryCosArmor cosInventory = getCosInventory(player);
        ItemStack[] stacked = new ItemStack[SLOTS.length];
        boolean any = false;

        for (int slot = 0; slot < SLOTS.length; slot++) {
            if (cosInventory.isSkinArmor(slot)) continue;
            ItemStack stack = cosInventory.getStackInSlot(slot);
            if (!stack.isEmpty()) {
                stacked[slot] = stack.copy();
                any = true;
            }
        }

        if (any) {
            renderState.setRenderData(StackedCosmetics.KEY, stacked);
        }
    }

    private static InventoryCosArmor getCosInventory(AbstractClientPlayer player) {
        return (InventoryCosArmor) ((com.skd.armorcosmetic.impl.InventoryManager) ModObjects.invMan)
                .getCosArmorInventoryClient(player.getUUID());
    }

    public void onFinishPlayerRenderState(AbstractClientPlayer player, AvatarRenderState renderState, float partialTick) {
        restoreItems(cache.getUnchecked(player));
    }

    private void handleLoggedOut(ClientPlayerNetworkEvent.LoggingOut event) {
        Disabled = false;
    }

    public void registerEvents() {
        NeoForge.EVENT_BUS.addListener(this::handleLoggedOut);
    }

    private void restoreItems(Deque<Runnable> restores) {
        Runnable r;
        while ((r = restores.poll()) != null) {
            try {
                r.run();
            } catch (Throwable t) {
                ModObjects.logger.error("Failed in restoring client player items", t);
            }
        }
    }
}
