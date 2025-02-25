# Balanced Mending Mod
This mod changes the way mending enchantment works in Minecraft.
## Requirements
[Fabric API](https://modrinth.com/mod/fabric-api)
## How it works
Mending now requires an ingredient to work. The correct ingredient has to be in you inventory for mending to work.
There is a chance for the ingredient to be consumed when repairing.
When this happens you get a bonus to the amount repaired.
Rarer ingredients give a higher bonus.
### Ingredients

| Item                     | Ingredient                                 | Repair Multiplier |
|--------------------------|--------------------------------------------|-------------------|
| Wooden Tools             | Planks                                     | 1x base           |
| Shield                   | Planks                                     | 1x base           |
| Stone Tools              | Cobblestone, Blackstone, Cobbled Deepslate | 1x base           |
| Leather Armor            | Leather                                    | 1x base           |
| Flint and Steel          | Flint                                      | 1x base           |
| Bow                      | String                                     | 1x base           |
| Crossbow                 | String                                     | 1x base           |
| Fishing Rod              | String                                     | 1x base           |
| Carrot on a Stick        | String                                     | 1x base           |
| Warped Fungus on a Stick | String                                     | 1x base           |
| Brush                    | Copper Ingot                               | 1x base           |
| Shears                   | Iron Ingot                                 | 2x base           |
| Iron Tools               | Iron Ingot                                 | 2x base           |
| Iron Armor               | Iron Ingot                                 | 2x base           |
| Chain Armor              | Iron Ingot                                 | 2x base           |
| Gold Tools               | Gold Ingot                                 | 2x base           |
| Gold Armor               | Gold Ingot                                 | 2x base           |
| Turtle Helmet            | Turtle Scute                               | 2x base           |
| Trident                  | Prismarine Shard                           | 2x base           |
| Elytra                   | Phantom Membrane                           | 2x base           |
| Diamond Armor            | Diamond                                    | 4x base           |
| Diamond Tools            | Diamond                                    | 4x base           |
| Mace                     | Breeze Rod                                 | 4x base           |
| Netherite Armor          | Netherite Ingot                            | 8x base           |
| Netherite Tools          | Netherite Ingot                            | 8x base           |

### Config
Ingredient consumption chance and the base bonus multiplier are configurable in balanced_mending.json. 
The base multiplier is 16 and the consume chance is 1%. [Mod Menu](https://modrinth.com/mod/modmenu) support is planed.
