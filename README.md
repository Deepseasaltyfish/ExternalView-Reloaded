# External View Reloaded

A Forge / NeoForge mod for Minecraft 1.16.5+ based on [External View Reload](https://www.curseforge.com/minecraft/mc-mods/external-view-reload).  
Provides adjustable third-person camera distance and optional long reach functionality.

---

## Features

* Adjustable third-person camera distance (default: **Page Up / Page Down**)
* Long Hand Mode (default: **K**) – increases interaction reach
* Camera collision option (prevents clipping through blocks)
* Fully configurable via TOML
* Works client-only (with command fallback)
* Optional server-side networking support
* Permission API support
* Languages: English, Russian, Simplified Chinese

---

## Configuration

File:

```txt
config/externalview-common.toml
```

### Camera

* `cameraSpeed` (default: `0.5`) – zoom step size
* `viewDistance` (default: `56`) – maximum camera distance
* `cameraCollision` (default: `true`) – prevent camera clipping

### Reach

* `reach` (default: `40.0`) – reach in Long Hand Mode
* `defaultReach` (default: `5.0`) – default reach (**vanilla = 5.0**)

WARNING: Changing `defaultReach` may cause issues on some servers.

### Forge 1.16.5 Note

Forge 1.16.5 config files do **not** support reliable hot reload.  
Some configuration changes may require a full game restart to take effect.

---

## Command Fallback (Client-only)

Used when the server does **not** have the mod installed.

### Forge 1.16.5

```toml
blockReachCommand = "attribute @s forge:reach_distance base set {value}"
```

### Forge 1.20.1

```toml
blockReachCommand = "attribute @s forge:block_reach base set {value}"
entityReachCommand = "attribute @s forge:entity_reach base set {value}"
```

### NeoForge 1.21+

```toml
blockReachCommand = "attribute @s minecraft:player.block_interaction_range base set {value}"
entityReachCommand = "attribute @s minecraft:player.entity_interaction_range base set {value}"
```

* `{value}` will be replaced automatically
* Invalid commands fallback to default values
* Requires permission to use `/attribute`

---

## Server Compatibility

### Client-only

* Uses commands to modify reach
* Requires OP or permission from the server
* Works on vanilla / Bukkit hybrid / most servers if `/attribute` is allowed

### With server installed

* Uses custom network packets (more reliable)
* Supports permission control
* Automatically resets reach when permission is lost

Permission node:

```txt
externalview.reach
```

Default: OP only

Compatible with permission systems such as:

* LuckPerms
* Forge Permission API
* NeoForge Permission API

---

## Version Notes

### Forge 1.16.5

Uses Forge reach attribute:

```txt
forge:reach_distance
```

### Forge 1.20.1

Uses Forge reach attributes:

```txt
forge:block_reach
forge:entity_reach
```

### NeoForge 1.21+

Uses vanilla player interaction range attributes:

```txt
minecraft:player.block_interaction_range
minecraft:player.entity_interaction_range
```

---

## Notes

* If `/attribute` is blocked, client-only mode will not work
* Reach changes affect both block and entity interaction range
* Command fallback mode is best-effort (no strict failure detection)
* Supports hybrid servers such as Mohist and Youer

---

## License

LGPL-2.1-or-later