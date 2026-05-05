# External View Reloaded

A NeoForge 1.21 port of External View Reload.
Provides adjustable third-person camera distance and optional long reach functionality.

---

## Features

* Adjustable third-person camera distance (Page Up / Page Down)
* Long Hand Mode (default: **K**) – increases interaction reach
* Camera collision option (prevents clipping through blocks)
* Fully configurable via TOML
* Works client-only (with command fallback)
* Languages: English, Russian, Simplified Chinese

---

## Configuration

File: `config/externalview-common.toml`

### Camera

* `cameraSpeed` (0.5) – zoom step size
* `viewDistance` (56) – maximum camera distance
* `cameraCollision` (true) – prevent camera clipping

### Reach

* `reach` (40.0) – reach in Long Hand Mode
* `defaultReach` (5.0) – default reach (**vanilla = 5.0**)

WARNING: Changing `defaultReach` may cause issues on some servers.

---

### Command Fallback (Client-only)

Used when the server does **not** have the mod:

```toml
blockReachCommand = "attribute @s minecraft:player.block_interaction_range base set {value}"
entityReachCommand = "attribute @s minecraft:player.entity_interaction_range base set {value}"
```

* `{value}` will be replaced automatically
* Invalid commands fallback to default

Requires permission to use `/attribute`.

---

## Server Compatibility

### Client-only

* Uses commands to modify reach
* Requires OP or permission (e.g. LuckPerms)

### With server installed

* Uses network packets (more reliable)
* Supports permission control via NeoForge

Permission node:

```
externalview.reach
```

(Default: OP only)

---

## Notes

* If `/attribute` is blocked, client-only mode will not work
* Affects both block and entity interaction range
* Command fallback is best-effort (no strict failure detection)

---

## License

LGPL-2.1-or-later
