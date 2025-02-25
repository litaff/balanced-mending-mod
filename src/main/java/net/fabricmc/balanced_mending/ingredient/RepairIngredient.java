package net.fabricmc.balanced_mending.ingredient;

import net.fabricmc.balanced_mending.config.Config;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;

import java.util.Collections;
import java.util.List;

public class RepairIngredient {
    private final List<Item> items;

    public RepairIngredient(Item item) {
        this.items = Collections.singletonList(item);
    }

    public RepairIngredient(List<Item> items) {
        this.items = items;
    }

    public Item getItem() {
        return items.getFirst();
    }

    public List<Item> getItems() {
        return items;
    }

    public void removeFrom(PlayerInventory inventory, int amount) {
        inventory.removeStack(getSlotIn(inventory), amount);
    }

    public int getSlotIn(PlayerInventory inventory) {
        var containedItems = items.stream().filter(item -> inventory.contains(item.getDefaultStack()));
        var first = containedItems.findFirst();
        if (first.isEmpty()) return -1;
        return inventory.getSlotWithStack(first.get().getDefaultStack());
    }

    public int getMultiplier() {
        int bonus = Config.get().baseConsumeMultiplier;

        for (var tier : RepairIngredientTiers.Tiers) {
            if (tier.isIngredientOfTier(this)) return bonus;
            bonus *= 2;
        }

        return Config.get().baseConsumeMultiplier;
    }
}
