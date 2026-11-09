# Armor Cosmetic

> This mod is a fork of **CosmeticArmorReworked** by zlainsama and dmillerw. Not affiliated with or endorsed by the original authors.

Wear two sets of armor — one for function, one for fashion. Armor Cosmetic lets you equip a separate cosmetic armor set that displays over your real armor, keeping your best protective gear equipped while showing off any look you want.

## Features

- **Dual armor system**: Wear one armor set for protection stats, another purely for display. Your real armor stays equipped underneath.
- **Cosmetic inventory GUI**: Open the cosmetic armor screen from your normal inventory with a single button click.
- **Per-slot toggles**: Each cosmetic slot has an S/A toggle that hides your real armor for that slot.
- **Stack rendering (optional)**: Enable `CosArmorStackRendering` in the client config to draw cosmetic armor on top of your real armor instead of replacing it.
- **Global toggle**: Disable all cosmetic rendering with one click — useful for PvP.
- **Corail Tombstone compatible**: With Tombstone installed your cosmetic gear isn't lost on death — captured with the rest of your loot on 26.x.
- **11 languages**: English, Spanish, German, French, Japanese, Portuguese, Russian, Chinese, Korean, Turkish, Czech.
- **Fully configurable**: Button positions, sizes, and visibility can be customized in the config file.
- **Commands**: `/clearcosarmor` — clear cosmetic armor from players · `/coshat` — toggle cosmetic helmet visibility.

## Requirements

- Minecraft 26.1.2
- NeoForge 26.1.2.78+

## Build

```bash
gradlew build
```

The JAR is generated at `build/libs/armor_cosmetic-26.1.2-neoforge-<version>.jar`.

## License

Armor Cosmetic is a fork of [CosmeticArmorReworked](https://www.curseforge.com/minecraft/mc-mods/cosmetic-armor-reworked) by **zlainsama** and **dmillerw**, which is licensed under the **Minecraft Mod Public License (MMPL) version 1.0.1**. Under the MMPL, modified versions must remain under the MMPL and their source must be provided at no cost.

The optional stack-rendering mode is adapted from [CosmeticArmorReworkedForked](https://github.com/zzik2/CosmeticArmorReworkedForked) by **zzik2**, also licensed under the MMPL 1.0.1.

Armor Cosmetic is therefore also distributed under the **MMPL 1.0.1** (see [`LICENSE`](LICENSE)), and a `-sources.jar` is published with every release.
