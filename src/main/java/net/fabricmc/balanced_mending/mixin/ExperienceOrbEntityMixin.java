package net.fabricmc.balanced_mending.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.RepairableComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.Console;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {

	// chance for each durability point healed
	private float consumeRepairIngredientChance = 0.1f;
	private int consumeRepairIngredientAmount = 1;
	// base bonus for wood, multiplied by 2 for each tier up
	// (wood/gold/fishing_rod/on_a_stick/flint_and_steel,
	// stone,
	// iron/shield/elytra/bow/crossbow/shears/trident,
	// diamond,
	// netherite)
	private int consumeRepairIngredientBonus = 4;


	@Inject(at = @At("HEAD"), method = "repairPlayerGears", cancellable = true)
	private void repairPlayerGears(ServerPlayerEntity player, int amount, CallbackInfoReturnable<Integer> cir) {
		List<ItemStack> itemStacks = getPlayerGear(player);

		for (int i = 0; i < itemStacks.size(); i++) {
			int repairAmount = Math.min(getMendingRepairAmount(amount), itemStacks.get(i).getDamage());
			// check for repair cost

			Ingredient repairIngredient = canRepairItemStack(player, itemStacks.get(i));

			// no repair items -> go to next item
			if (repairIngredient == null) continue;

			amount -= repairItemStack(player, itemStacks.get(i), repairAmount, repairIngredient);

			if (amount <= 0) break;
		}

		cir.setReturnValue(amount);
	}

	private Ingredient canRepairItemStack(PlayerEntity player, ItemStack itemStack) {
		Item item = itemStack.getItem();

		RepairableComponent repairable = item.getComponents().get(DataComponentTypes.REPAIRABLE);
		if (repairable == null) return null;

		RegistryEntryList<Item> items = repairable.items();
		if (items == null) return null;

		for (var itemEntry : items){
			System.out.println(itemEntry.getIdAsString());
		}
		// TODO: This is a good direction but some items are not repairable through the anvil. Meaning that it needs a special case.

		/*// check material for tools
		if (item instanceof MiningToolItem) {
			items = ((MiningToolItem) item).getComponents().get(DataComponentTypes.REPAIRABLE)..getRepairIngredient();
		}

		// check material for armor
		if (item instanceof ArmorItem) {
			items = ((ArmorItem) item).getMaterial().getRepairIngredient();
		}

		// get items for shields
		if (item instanceof ShieldItem) {
			items = Ingredient.fromTag(ItemTags.PLANKS);
		}

		// get items for elytra
		if (item instanceof ElytraItem) {
			items = Ingredient.ofItems(Items.PHANTOM_MEMBRANE);
		}

		// get items for shears
		if (item instanceof ShearsItem) {
			items = Ingredient.ofItems(Items.IRON_INGOT);
		}

		// get items for flint and steel
		if (item instanceof FlintAndSteelItem) {
			items = Ingredient.ofItems(Items.FLINT);
		}

		if (item instanceof RangedWeaponItem || item instanceof FishingRodItem || item instanceof OnAStickItem) {
			items = Ingredient.ofItems(Items.STRING);
		}

		if (item instanceof TridentItem) {
			items = Ingredient.ofItems(Items.PRISMARINE_SHARD);
		}

		// check if player has repair items
		if (player.getInventory().containsAny(items)) {
			return items;
		}*/

		return null;
	}

	private int repairItemStack(PlayerEntity player, ItemStack itemStack, int repairAmount, Ingredient repairIngredient) {
		/*// getMatchingStacks()[0] might be bad
		repairIngredient.getMatchingStacks()[0].getRarity();
		if (Math.random() * 100 < consumeRepairIngredientChance * repairAmount) {
			player.getInventory().removeStack(
					player.getInventory().getSlotWithStack(repairIngredient.getMatchingStacks()[0]),
					consumeRepairIngredientAmount);
			repairAmount *= getRepairAmountBonusFromIngredient(repairIngredient);
		}
		repairAmount = Math.min(itemStack.getDamage(), repairAmount); // don't repair over limit
		itemStack.setDamage(itemStack.getDamage() - repairAmount);*/
		return getMendingRepairCost(repairAmount);
	}

	private int getRepairAmountBonusFromIngredient(Ingredient ingredient) {
		int bonus = consumeRepairIngredientBonus;

		/*if (ingredient == Ingredient.fromTag(ItemTags.PLANKS))
			return bonus;
		if (ingredient == Ingredient.ofItems(Items.GOLD_INGOT))
			return bonus;
		if (ingredient == Ingredient.ofItems(Items.STRING))
			return bonus;
		if (ingredient == Ingredient.ofItems(Items.FLINT))
			return bonus;
		bonus *= 2;
		if (ingredient == Ingredient.ofItems(Items.GOLD_INGOT))
			return bonus;
		bonus *= 2;
		if (ingredient == Ingredient.fromTag(ItemTags.PLANKS))
			return bonus;
		if (ingredient == Ingredient.ofItems(Items.IRON_INGOT))
			return bonus;
		if (ingredient == Ingredient.ofItems(Items.STRING))
			return bonus;
		if (ingredient == Ingredient.ofItems(Items.PHANTOM_MEMBRANE))
			return bonus;
		if (ingredient == Ingredient.ofItems(Items.PRISMARINE_SHARD))
			return bonus;
		bonus *= 2;
		if (ingredient == Ingredient.ofItems(Items.DIAMOND))
			return bonus;
		bonus *= 2;
		if (ingredient == Ingredient.ofItems(Items.NETHERITE_INGOT))
			return bonus;*/
		return bonus;
	}

	private int getMendingRepairCost(int repairAmount) {
		return repairAmount / 2;
	}

	private int getMendingRepairAmount(int experienceAmount) {
		return experienceAmount * 2;
	}

	private List<ItemStack> getPlayerGear(PlayerEntity player) {
		List<ItemStack> itemStacks = new ArrayList<>();
		ItemStack itemStack;

		itemStack = player.getEquippedStack(EquipmentSlot.HEAD);
		if (isItemStackRepairable(itemStack)) itemStacks.add(itemStack);

		itemStack = player.getEquippedStack(EquipmentSlot.CHEST);
		if (isItemStackRepairable(itemStack)) itemStacks.add(itemStack);

		itemStack = player.getEquippedStack(EquipmentSlot.LEGS);
		if (isItemStackRepairable(itemStack)) itemStacks.add(itemStack);

		itemStack = player.getEquippedStack(EquipmentSlot.FEET);
		if (isItemStackRepairable(itemStack)) itemStacks.add(itemStack);

		itemStack = player.getEquippedStack(EquipmentSlot.MAINHAND);
		if (isItemStackRepairable(itemStack)) itemStacks.add(itemStack);

		itemStack = player.getEquippedStack(EquipmentSlot.OFFHAND);
		if (isItemStackRepairable(itemStack)) itemStacks.add(itemStack);

		return itemStacks;
	}

	private boolean isItemStackRepairable(ItemStack stack) {
		if (stack == null) return false;

		ItemEnchantmentsComponent itemEnchantmentsComponent = (ItemEnchantmentsComponent)stack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
		var entries = itemEnchantmentsComponent.getEnchantmentEntries();

		for (var entry : entries){
			if (entry.getKey().matchesKey(Enchantments.MENDING)) {
				return stack.isDamaged();
			}
		}
		return false;
	}
}
