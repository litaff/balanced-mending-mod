package net.fabricmc.example.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {

	// chance for each durability point healed
	private float consumeRepairIngredientChance = 0.1f;
	private int consumeRepairIngredientAmount = 1;
	// base bonus for wood, multiplied by 2 for each tier up
	// (wood/gold/fishing_rod/on_a_stick/flint_and_steel,
	// stone,
	// iron/shield/elytra/bow/crossbow/shears,
	// diamond,
	// netherite)
	private int consumeRepairIngredientBonus = 4;


	@Inject(at = @At("HEAD"), method = "repairPlayerGears", cancellable = true)
	private void repairPlayerGears(PlayerEntity player, int amount, CallbackInfoReturnable<Integer> cir) {
		List<ItemStack> itemStacks = getPlayerGear(player);

		for (int i = 0; i < itemStacks.size(); i++) {
			int repairAmount = Math.min(getMendingRepairAmount(amount), itemStacks.get(i).getDamage());
			// check for repair cost

			Ingredient repairIngredient = canRepairItemStack(player, itemStacks.get(i));

			// no repair ingredient -> go to next item
			if (repairIngredient == null) continue;

			amount -= repairItemStack(player, itemStacks.get(i), repairAmount, repairIngredient);

			if (amount <= 0) break;
		}

		cir.setReturnValue(amount);
	}

	private Ingredient canRepairItemStack(PlayerEntity player, ItemStack itemStack) {
		Item item = itemStack.getItem();
		Ingredient ingredient = null;

		// check material for tools
		if (item instanceof ToolItem) {
			ingredient = ((ToolItem) item).getMaterial().getRepairIngredient();
		}

		// check material for armor
		if (item instanceof ArmorItem) {
			ingredient = ((ArmorItem) item).getMaterial().getRepairIngredient();
		}

		// get ingredient for shields
		if (item instanceof ShieldItem) {
			ingredient = Ingredient.fromTag(ItemTags.PLANKS);
		}

		// get ingredient for elytra
		if (item instanceof ElytraItem) {
			ingredient = Ingredient.ofItems(Items.PHANTOM_MEMBRANE);
		}

		// get ingredient for shears
		if (item instanceof ShearsItem) {
			ingredient = Ingredient.ofItems(Items.IRON_INGOT);
		}

		// get ingredient for flint and steel
		if (item instanceof FlintAndSteelItem) {
			ingredient = Ingredient.ofItems(Items.FLINT);
		}

		if (item instanceof RangedWeaponItem || item instanceof FishingRodItem || item instanceof OnAStickItem) {
			ingredient = Ingredient.ofItems(Items.STRING);
		}

		// check if player has repair ingredient
		if (player.getInventory().containsAny(ingredient)) {
			return ingredient;
		}

		return null;
	}

	private int repairItemStack(PlayerEntity player, ItemStack itemStack, int repairAmount, Ingredient repairIngredient) {
		// getMatchingStacks()[0] might be bad
		repairIngredient.getMatchingStacks()[0].getRarity();
		if (Math.random() * 100 < consumeRepairIngredientChance * repairAmount) {
			player.getInventory().removeStack(
					player.getInventory().getSlotWithStack(repairIngredient.getMatchingStacks()[0]),
					consumeRepairIngredientAmount);
			repairAmount *= getRepairAmountBonusFromIngredient(repairIngredient);
		}
		repairAmount = Math.min(itemStack.getDamage(), repairAmount); // don't repair over limit
		itemStack.setDamage(itemStack.getDamage() - repairAmount);
		return getMendingRepairCost(repairAmount);
	}

	private int getRepairAmountBonusFromIngredient(Ingredient ingredient) {
		int bonus = consumeRepairIngredientBonus;

		if (ingredient == Ingredient.fromTag(ItemTags.PLANKS))
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
		bonus *= 2;
		if (ingredient == Ingredient.ofItems(Items.DIAMOND))
			return bonus;
		bonus *= 2;
		if (ingredient == Ingredient.ofItems(Items.NETHERITE_INGOT))
			return bonus;
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

	private boolean isItemStackRepairable(ItemStack itemStack) {
		return itemStack != null && EnchantmentHelper.getLevel(Enchantments.MENDING, itemStack) > 0 && itemStack.isDamaged();
	}
}
