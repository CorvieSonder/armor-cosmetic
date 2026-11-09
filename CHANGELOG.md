# Armor Cosmetic - Changelog

## [Unreleased]

### Added

- **Stack rendering (`CosArmorStackRendering`, client, off by default)**: cosmetic armor is drawn on top of real armor instead of replacing it. Approach adapted from [CosmeticArmorReworkedForked](https://github.com/zzik2/CosmeticArmorReworkedForked) by **zzik2** (MMPL 1.0.1), ported to the 26.1 render state/submit pipeline.
  - The cosmetic set is rendered from a second render state built with the cosmetic pieces equipped, so armor that prepares its own render data during extraction (e.g. GeckoLib armor such as Advanced AE Quantum armor) renders correctly as cosmetic.
  - The cosmetic pass also re-runs other armor-type layers on the player (vanilla wings and mod layers such as LTXI's Wonderland armor layer), and copies the live pose (including the inventory preview's head rotation) onto the cosmetic state.
- **Inflate stacked cosmetic armor (`CosArmorInflateCosmetic`, client, on by default)**: when stacking, vanilla-style cosmetic armor is drawn with a slightly larger model so it always shows over real armor of the same shape regardless of draw order.

## [1.1.5] - 2026-10-30

### Changed

- **Variables de CurseForge**: `docs/curseforge/project_vars.md` pasa a `docs/curseforge/project.env` (formato env: `PROJECT_ID`, `GAME_VERSIONS`, `RELEASE_TYPE`, `RELATIONS`). La documentación genérica se centraliza en `codex-docs/reference/CURSEFORGE.md` y los scripts de subida y consulta leen el nuevo fichero.
- **Sin cambios en el mod**: solo documentación y tooling. Versión **exenta de subida a CurseForge** (no hay JAR nuevo que publicar); se versiona por ser un cambio de proceso importante.

## [1.1.4] - 2026-10-29

### Changed

- **Tokens de CurseForge**: el token de subida y la clave Core ya no se guardan en `docs/curseforge/project_vars.md`; se leen de las variables de entorno de usuario `API_MOD_UPLOAD_CURSEFORGE` y `API_MOD_GET_CURSEFORGE`. Los scripts de subida y consulta se han adaptado y la documentación se ha actualizado.
- **Sin cambios en el mod**: solo documentación y tooling. Versión **exenta de subida a CurseForge** (no hay JAR nuevo que publicar); se versiona por ser un cambio de proceso importante.

## [1.1.3] - 2026-10-28

### Fix

- **Paquetes de recursos con EMF/ETF**: versión estable de `1.1.3-beta.1`, probada en juego con EMF, ETF y Fantasy 3D Armors (ver entrada de la beta).
- **Nombre del JAR**: `archivesName` incluye ahora `${neo_version}` (`armor_cosmetic-26.1.2-neoforge-26.1.2.78-1.1.3.jar`), igual que el resto de variantes NeoForge.

## [1.1.3-beta.1] - 2026-10-27

### Fix

- **Paquetes de recursos con EMF/ETF** (p. ej. Fantasy 3D Armors): la armadura colocada en las ranuras cosméticas no recibía el cambio visual del paquete porque EMF/ETF leían el NBT de la armadura real. Nuevo mixin cliente `MixinNbtPredicate` sobre `NbtPredicate.getEntityTagToCompare(Entity)` que sustituye el compuesto `equipment` de los jugadores cliente por la armadura cosmética (la armadura skin se trata como vacía). Respeta el interruptor de desactivar y no toca el servidor.
- Versión **beta**: no probada a fondo en juego.

## [1.1.2] - 2026-10-11

### Fix

- **Volver al inventario normal**: la pantalla del inventario normal se abre ahora cuando el servidor ha cerrado el contenedor cosmético (nuevo payload servidor→cliente `PayloadOpenNormalInventoryScreen`), en vez de abrirse en el cliente antes de que el servidor cierre el contenedor. La posición del ratón se conserva.

## [1.1.1] - 2026-09-29

### Fix

- **Fondos de las ranuras**: vuelven a dibujarse las siluetas de casco/peto/pantalones/botas/escudo en las ranuras vacías del inventario cosmético (armadura real, mano secundaria y ranuras cosméticas), como en 1.21.1.

## [1.1.0] - 2026-09-29

### Change

- **Interfaz igual que en 1.21.1**: textura original, botón cosmético de 10×10 con etiqueta al pasar el ratón, toggles S/A de 5×5, vista del jugador vanilla y libro de recetas opcional; eliminados el botón ✕ y la vista previa propia.
- **Configuración como en 1.21.1**: mismas claves, valores por defecto y reparto cliente/común (`CosArmorKeepThroughDeath`, `CosArmorDisableRecipeBook`, posiciones de botones); eliminadas las claves de ancho/alto.
- **Muerte**: no se sueltan los objetos cosméticos con `keepInventory`, con `CosArmorKeepThroughDeath` o con Corail Tombstone instalado; ya no se mueve la armadura real a los drops.
- **`/coshat`**: intercambia la mano principal con la ranura cosmética del casco, como en 1.21.1.

## [1.0.23] - 2026-08-04

### Arreglos
- **Datos de armadura cosmética no se guardaban al desconectarse/parar el servidor** (`IllegalStateException: Can't access registry ResourceKey[minecraft:root / minecraft:enchantment]`): `CAStacksBase.serializeNBT`/`deserializeNBT` codificaba/decodificaba el `ItemStack` con el codec `NbtOps.INSTANCE` puro en vez de usar el `HolderLookup.Provider` recibido, así que los datos del ítem que dependen de registries (p. ej. encantamientos) no se podían resolver y el guardado fallaba silenciosamente. Ahora usa `provider.createSerializationContext(NbtOps.INSTANCE)`.

## [1.0.22] - 2026-07-29

### Cambios
- **Valores por defecto de configuración**: ancho/alto del botón cosmético normal ahora `20x20` (antes `10x10`) y botón del inventario creativo visible por defecto (`CosArmorCreativeGuiButton_Hidden=false`), alineados con las estadísticas fijas de referencia.
- **Auditoría de proyecto**: eliminado `TEMPLATE_LICENSE.txt`, movidos templates a `src/main/resources/templates/`, añadido `temp/` al `.gitignore`.
- **WORKFLOW actualizado**: renombrado a `WORKFLOW_ARMOR_COSMETIC_26-1-2.md` siguiendo el formato del genérico v1.0.0.
- **Logo**: renombrado a `armor_cosmetic.png` según convención `<mod_id>.png`.
- **build.gradle**: actualizada ruta de templates a `src/main/resources/templates/`.

### Arreglos
- **Traducción es_es corrupta**: corregida codificación de caracteres especiales (`Ã³`→`ó`, `Ã©`→`é`) en `es_es.json`, que afectaba a la pantalla de configuración en español.

## [1.0.21] - 2026-07-16

### Arreglos
- Reintento de subida a CurseForge con changelog en markdown correctamente formateado.

## [1.0.20] - 2026-07-16

### Arreglos
- **Armadura real perdida al morir con Tombstone**: ahora la armadura real se añade a `LivingDropsEvent` junto a la cosmética. Tombstone captura ambas en la tumba.
- Al morir, la armadura real se vacía del jugador y se mete en `event.getDrops()`, garantizando que Tombstone la capture.

## [1.0.19] - 2026-07-14

### Cambios
- Reestructurada rama `main` (solo commit inicial) → desarrollo en `minecraft/<mc-version>/neoforge-<neo-version>/production`.
- Formato de tags actualizado a `<mc-version>-neoforge-<version>` (ej: `26.1.2-neoforge-1.0.18`).
- JAR renombrado a `<mod_id>-<minecraft_version>-<framework>-<mod_version>.jar`.
- WORKFLOW.md actualizado con ramas, convención de JAR y política de idiomas.

## [1.0.18] - 2026-07-11

### Arreglos
- Los toggles individuales (S/A) ahora ocultan la **armadura real** en lugar de la cosmética.
  - Toggle ON (verde/S) = oculta la armadura real → se ve la piel (o cosmética si equipada).
  - Toggle OFF (gris/A) = comportamiento normal (armadura real visible, cosmética encima).
  - Ahora puedes llevar armadura funcional puesta mientras muestras solo tu skin o armadura cosmética.

## [1.0.17] - 2026-07-10

### Arreglos
- Actualizados los 11 archivos de idioma: el botón "Back to Normal" ahora pone "Close" / "Cerrar".
- Tooltip actualizado: "Close Cosmetic Inventory" / "Cerrar inventario cosmético".

## [1.0.16] - 2026-07-10

### Arreglos
- Añadido `META-INF/mods.toml` junto a `neoforge.mods.toml` para que CurseForge detecte el mod como NeoForge.
- Corregido `pack.mcmeta` (tenía una variable de template sin expandir).
- Actualizadas las traducciones al inglés y español del botón/tooltip.

## [1.0.15] - 2026-07-10

### Arreglos
- **Bloqueo del inventario**: al cerrar el inventario cosmético ahora vuelve correctamente al control normal del inventario.
- **Armadura real perdida al morir**: corregido. La armadura real ahora se suelta correctamente en lugar de desaparecer.
- **Compatibilidad con Corail Tombstone**: los objetos cosméticos ahora son capturados por la tumba al morir.
- **Crash (NullPointerException) al morir**: el contenedor se cierra correctamente mediante `doCloseContainer()`.

## [1.0.11] - 2026-07-09

### Cambios
- Eliminada la opción de configuración `keepThroughDeath`. La armadura cosmética siempre se suelta al morir.

## [1.0.10] - 2026-07-09

### Changed
- Split config into COMMON + CLIENT sides for proper server death handling.

## [1.0.9] - 2026-07-08

### Changed
- Updated chestplate icon.

## [1.0.8] - 2026-07-08

### Added
- Tooltips on all buttons.
- Full translations in 11 languages (en, es, de, fr, ja, pt, ru, zh, ko, tr, cs).

## [1.0.7] - 2026-07-08

### Changed
- Chestplate icon that scales to button size.

## [1.0.6] - 2026-07-08

### Changed
- Icon scales to fit button size.
- Default button size 16x16 to fit icon.

## [1.0.1] - 2026-07-06

### Changed
- Updated default config values for better out-of-box experience
- Toggle button hidden by default, creative button hidden by default
- Recipe book and /coshat command disabled by default
- Skin armor toggle buttons repositioned and resized
- Cosmetic armor button now 10x10 (compact, like original mod)
- Removed unused icon size configuration options
- Buttons render text labels only (no sprites/icons)

## [1.0.0] - 2026-07-06

### First Stable Release
- Initial release of ArmorCosmetic for NeoForge 26.1.2.76 (Minecraft 26.1.2)
- Replica of CosmeticArmorReworked by zlainsama, ported by Stalking Dragons

### Features
- Dual armor system: wear one set for stats, another for display
- Cosmetic armor inventory GUI with 2x2 crafting grid
- Global toggle button to enable/disable cosmetic armor rendering (useful for PvP)
- Per-slot skin armor toggle buttons with configurable position and size
- Cosmetic armor data persists across sessions (per-player NBT files)
- Death drop handling for cosmetic armor (configurable)
- Hidden flags API for addon mods
- Commands: /clearcosarmor, /coshat
- Full translations in 11 languages (en, es, de, fr, ja, pt, ru, zh, ko, tr, cs)
- Fully configurable GUI button positions, sizes, and visibility
- Equipment type filtering per cosmetic armor slot
- Client-server sync for multiplayer compatibility

### Known Issues
- Player model not displayed in cosmetic inventory screen
- Item tooltip may show incorrect armor stats while in cosmetic view
- Recipe book may remain open when switching to cosmetic inventory

### Credits
- Original mod: CosmeticArmorReworked by zlainsama, dmillerw
- Ported to NeoForge 26.1.2.76 by Stalking Dragons
- Inspired by baubles (azanor)
