# External View Reloaded

A NeoForge 1.21 port of [External View Reload](https://www.curseforge.com/minecraft/mc-mods/external-view-reload).  
Allows dynamic third-person camera distance and toggleable long reach mode.

## Features

- Adjustable camera distance (Page Up / Page Down)
- Long hand mode (K key) – increases block interaction range
- Fully configurable via TOML file
- Languages: English, Russian, Simplified Chinese

## Configuration

File: `config/externalview-common.toml`

- `cameraSpeed` (0.5) – zoom step size
- `viewDistance` (56) – max camera distance
- `reach` (40) – reach in long hand mode
- `cameraCollision` (true) – prevent camera from clipping through blocks

## Server Usage

On dedicated servers, **the ability to change the reach distance (Long Hand Mode) is restricted**:
- The mod needs be installed on **the server side**.
- Only players with **OP privileges** (`op`) can modify the reach distance.
- Non-OP players cannot change the reach distance, even if they have the mod installed on their client.

## License

LGPL-2.1-or-later