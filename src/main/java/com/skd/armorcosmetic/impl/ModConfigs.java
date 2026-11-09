package com.skd.armorcosmetic.impl;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class ModConfigs {

    public static BooleanValue CosArmorGuiButton_Hidden;
    public static IntValue CosArmorGuiButton_Left;
    public static IntValue CosArmorGuiButton_Top;
    public static BooleanValue CosArmorToggleButton_Hidden;
    public static IntValue CosArmorToggleButton_Left;
    public static IntValue CosArmorToggleButton_Top;
    public static BooleanValue CosArmorCreativeGuiButton_Hidden;
    public static IntValue CosArmorCreativeGuiButton_Left;
    public static IntValue CosArmorCreativeGuiButton_Top;
    public static BooleanValue CosArmorStackRendering;
    public static BooleanValue CosArmorKeepThroughDeath;
    public static BooleanValue CosArmorDisableRecipeBook;
    public static BooleanValue CosArmorDisableCosHatCommand;

    public static ModConfigSpec CLIENT_SPEC;
    public static ModConfigSpec COMMON_SPEC;

    public static void registerConfigs(ModContainer container) {
        ModConfigSpec.Builder clientBuilder = new ModConfigSpec.Builder();
        clientBuilder.comment("These settings only affects client").push("Client");
        CosArmorGuiButton_Hidden = clientBuilder.comment("Whether or not to hide the button for opening CosmeticArmorInventory")
                .translation("cos.config.CosArmorGuiButton_Hidden")
                .define("CosArmorGuiButton_Hidden", false);
        CosArmorGuiButton_Left = clientBuilder.comment("The horizontal pixel distance from the origin point of player inventory gui")
                .translation("cos.config.CosArmorGuiButton_Left")
                .defineInRange("CosArmorGuiButton_Left", 75, Integer.MIN_VALUE, Integer.MAX_VALUE);
        CosArmorGuiButton_Top = clientBuilder.comment("The vertical pixel distance from the origin point of player inventoy gui")
                .translation("cos.config.CosArmorGuiButton_Top")
                .defineInRange("CosArmorGuiButton_Top", 50, Integer.MIN_VALUE, Integer.MAX_VALUE);
        CosArmorToggleButton_Hidden = clientBuilder.comment("Whether or not to hide the button for toggling the mod temporarily on client side")
                .translation("cos.config.CosArmorToggleButton_Hidden")
                .define("CosArmorToggleButton_Hidden", false);
        CosArmorToggleButton_Left = clientBuilder.comment("The horizontal pixel distance from the origin point of player inventory gui")
                .translation("cos.config.CosArmorToggleButton_Left")
                .defineInRange("CosArmorToggleButton_Left", 77, Integer.MIN_VALUE, Integer.MAX_VALUE);
        CosArmorToggleButton_Top = clientBuilder.comment("The vertical pixel distance from the origin point of player inventory gui")
                .translation("cos.config.CosArmorToggleButton_Top")
                .defineInRange("CosArmorToggleButton_Top", 45, Integer.MIN_VALUE, Integer.MAX_VALUE);
        CosArmorCreativeGuiButton_Hidden = clientBuilder.comment("Whether or not to hide the button for opening CosmeticArmorInventory in CreativeInventory")
                .translation("cos.config.CosArmorCreativeGuiButton_Hidden")
                .define("CosArmorCreativeGuiButton_Hidden", false);
        CosArmorCreativeGuiButton_Left = clientBuilder.comment("The horizontal pixel distance from the origin point of creative inventory gui")
                .translation("cos.config.CosArmorCreativeGuiButton_Left")
                .defineInRange("CosArmorCreativeGuiButton_Left", 95, Integer.MIN_VALUE, Integer.MAX_VALUE);
        CosArmorCreativeGuiButton_Top = clientBuilder.comment("The vertical pixel distance from the origin point of creative inventoy gui")
                .translation("cos.config.CosArmorCreativeGuiButton_Top")
                .defineInRange("CosArmorCreativeGuiButton_Top", 38, Integer.MIN_VALUE, Integer.MAX_VALUE);
        CosArmorStackRendering = clientBuilder.comment("Stack the render for cosmetic armor on top of regular armor, instead of replacing it")
                .translation("cos.config.CosArmorStackRendering")
                .define("CosArmorStackRendering", false);
        clientBuilder.pop();
        CLIENT_SPEC = clientBuilder.build();

        ModConfigSpec.Builder commonBuilder = new ModConfigSpec.Builder();
        commonBuilder.comment("These settings affects both server and client").push("Common");
        CosArmorKeepThroughDeath = commonBuilder.comment("Whether or not to keep items in cosmetic armor slots in the event of player death")
                .translation("cos.config.CosArmorKeepThroughDeath")
                .define("CosArmorKeepThroughDeath", false);
        CosArmorDisableRecipeBook = commonBuilder.comment("Whether or not to disable the RecipeBook in the CosmeticArmorInventory")
                .translation("cos.config.CosArmorDisableRecipeBook")
                .define("CosArmorDisableRecipeBook", true);
        CosArmorDisableCosHatCommand = commonBuilder.comment("Whether or not to disable the coshat command")
                .translation("cos.config.CosArmorDisableCosHatCommand")
                .define("CosArmorDisableCosHatCommand", false);
        commonBuilder.pop();
        COMMON_SPEC = commonBuilder.build();

        container.registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC, "armor_cosmetic/client.toml");
        container.registerConfig(ModConfig.Type.COMMON, COMMON_SPEC, "armor_cosmetic/common.toml");
    }

}
