package net.fabricmc.balanced_mending.ingredient;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.RepairableComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntryList;

import java.util.*;

public class RepairIngredients {

    private static final Map<Item, Item> ingredientsMap = new HashMap<>() {{
        put(Items.TRIDENT, Items.PRISMARINE_SHARD);
        put(Items.FISHING_ROD, Items.STRING);
        put(Items.BOW, Items.STRING);
        put(Items.CARROT_ON_A_STICK, Items.STRING);
        put(Items.WARPED_FUNGUS_ON_A_STICK, Items.STRING);
        put(Items.CROSSBOW, Items.STRING);
        put(Items.FLINT_AND_STEEL, Items.FLINT);
        put(Items.BRUSH, Items.COPPER_INGOT);
        put(Items.SHEARS, Items.IRON_INGOT);
    }};

    public static RepairIngredient getRepairIngredient(Item toRepair) {
        RepairableComponent repairable = toRepair.getComponents().get(DataComponentTypes.REPAIRABLE);
        if (repairable != null) {
            RegistryEntryList<Item> itemEntries = repairable.items();
            List<Item> items = new ArrayList<>();
            for (var itemEntry : itemEntries) {
                items.add(itemEntry.value());
            }
            return new RepairIngredient(items);
        }

        return new RepairIngredient(ingredientsMap.get(toRepair));
    }
}
