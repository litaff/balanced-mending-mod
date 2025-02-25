package net.fabricmc.balanced_mending.ingredient;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.List;

public class RepairIngredientTier {
    private final List<Item> items;
    private final List<TagKey<Item>> tags;

    public RepairIngredientTier(List<Item> items, List<TagKey<Item>> tags) {
        this.items = items;
        this.tags = tags;
    }

    public boolean isIngredientOfTier(RepairIngredient ingredient) {
        var itemsCopy = new ArrayList<>(items);
        return tags.stream().anyMatch(itemTagKey -> isOfTag(ingredient.getItems(), itemTagKey)) ||
                itemsCopy.retainAll(ingredient.getItems());
    }

    private boolean isOfTag(List<Item> items, TagKey<Item> tag){
        RegistryEntryLookup<Item> registryEntryLookup = Registries.ITEM;
        return items.stream().anyMatch(item -> item.getDefaultStack().streamTags().anyMatch(itemTagKey ->
                itemTagKey == registryEntryLookup.getOrThrow(tag).getTag()));
    }
}
