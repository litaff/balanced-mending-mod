package net.fabricmc.balanced_mending.ingredient;

import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;

import java.util.Arrays;
import java.util.List;

public class RepairIngredientTiers {
    public static RepairIngredientTier Tier1 = new RepairIngredientTier(
            Arrays.asList(
                    Items.FLINT,
                    Items.STRING,
                    Items.COPPER_INGOT),
            Arrays.asList(
                    ItemTags.WOODEN_TOOL_MATERIALS,
                    ItemTags.STONE_TOOL_MATERIALS,
                    ItemTags.REPAIRS_LEATHER_ARMOR));
    public static RepairIngredientTier Tier2 = new RepairIngredientTier(
            Arrays.asList(
                    Items.PRISMARINE_SHARD,
                    Items.PHANTOM_MEMBRANE),
            Arrays.asList(
                    ItemTags.IRON_TOOL_MATERIALS,
                    ItemTags.REPAIRS_IRON_ARMOR,
                    ItemTags.REPAIRS_CHAIN_ARMOR,
                    ItemTags.GOLD_TOOL_MATERIALS,
                    ItemTags.REPAIRS_GOLD_ARMOR,
                    ItemTags.REPAIRS_TURTLE_HELMET,
                    ItemTags.REPAIRS_WOLF_ARMOR));
    public static RepairIngredientTier Tier3 = new RepairIngredientTier(
            Arrays.asList(
                    Items.BREEZE_ROD),
            Arrays.asList(
                    ItemTags.DIAMOND_TOOL_MATERIALS,
                    ItemTags.REPAIRS_DIAMOND_ARMOR));
    public static RepairIngredientTier Tier4 = new RepairIngredientTier(
            Arrays.asList(),
            Arrays.asList(
                    ItemTags.NETHERITE_TOOL_MATERIALS,
                    ItemTags.REPAIRS_NETHERITE_ARMOR));

    public static List<RepairIngredientTier> Tiers = Arrays.asList(
            Tier1,
            Tier2,
            Tier3,
            Tier4);
}
