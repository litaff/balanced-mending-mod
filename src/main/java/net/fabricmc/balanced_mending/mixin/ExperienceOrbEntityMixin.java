package net.fabricmc.balanced_mending.mixin;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.fabricmc.balanced_mending.config.Config;
import net.fabricmc.balanced_mending.ingredient.RepairIngredient;
import net.fabricmc.balanced_mending.RepairableItemStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {
	@Inject(at = @At("HEAD"), method = "repairPlayerGears", cancellable = true)
	private void repairPlayerGears(ServerPlayerEntity player, int amount, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(repairPlayerGears(player, amount));
	}

	@Unique
	private int repairPlayerGears(ServerPlayerEntity player, int amount) {
		Optional<RepairableItemStack> optional = chooseEquipment(player);
		if (optional.isEmpty()) return amount;

		var repairableItem = optional.get();
		var stack = repairableItem.getItemStack();
		var stackDamage = stack.getDamage();
		int i = EnchantmentHelper.getRepairWithExperience(player.getServerWorld(), stack, amount);
		int j = Math.min(i, stackDamage);
		int repair = j;
		// Not repairing to max.
		if (j == i) {
			repair = Math.min(
					tryConsumeRepairIngredient(player.getInventory(), i, repairableItem.getRepairIngredient()),
					stackDamage);
		}
		stack.setDamage(stackDamage - repair);
		if (j > 0) {
			int k = amount - j * amount / i;
			if (k > 0) {
				return this.repairPlayerGears(player, k);
			}
		}

		return 0;
	}

	@Unique
	private Optional<RepairableItemStack> chooseEquipment(ServerPlayerEntity player) {
		List<RepairableItemStack> list = new ArrayList();
		for (EquipmentSlot equipmentSlot : EquipmentSlot.VALUES) {
			ItemStack itemStack = player.getEquippedStack(equipmentSlot);

			if (itemStack.isDamaged()) {
				ItemEnchantmentsComponent itemEnchantmentsComponent = itemStack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);

				for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : itemEnchantmentsComponent.getEnchantmentEntries()) {
					RegistryEntry<Enchantment> registryEntry = entry.getKey();

					if (registryEntry.value().effects().contains(EnchantmentEffectComponentTypes.REPAIR_WITH_XP) &&
							registryEntry.value().slotMatches(equipmentSlot)) {
						var repairableItem = new RepairableItemStack(itemStack);

						if (!repairableItem.isRepairableBy(player)) continue;
						list.add(repairableItem);
					}
				}
			}
		}
		return Util.getRandomOrEmpty(list, player.getRandom());
	}

	@Unique
	private int tryConsumeRepairIngredient(PlayerInventory inventory, int repairAmount, RepairIngredient repairIngredient) {
		if (Math.random() * 100 <= Config.get().consumeChance) {

			repairIngredient.removeFrom(inventory, 1);
			repairAmount *= repairIngredient.getMultiplier();
		}
		return repairAmount;
	}
}