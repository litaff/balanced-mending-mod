package net.fabricmc.balanced_mending;

import net.fabricmc.balanced_mending.ingredient.RepairIngredient;
import net.fabricmc.balanced_mending.ingredient.RepairIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;


public class RepairableItemStack {
    private final ItemStack item;
    private final RepairIngredient repairIngredient;

    public RepairableItemStack(ItemStack item) {
        this.item = item;
        repairIngredient = RepairIngredients.getRepairIngredient(item.getItem());
    }

    public boolean isRepairableBy(ServerPlayerEntity player) {
        return isRepairable() && repairIngredient.getItems().stream().anyMatch(ingredient ->
                player.getInventory().contains(ingredient.getDefaultStack()));
    }

    public boolean isRepairable() {
        return repairIngredient != null;
    }

    public ItemStack getItemStack() {
        return item;
    }

    public RepairIngredient getRepairIngredient() {
        return repairIngredient;
    }
}
