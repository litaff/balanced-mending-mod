package net.fabricmc.balanced_mending.debug;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class DebugCommands {

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(DebugCommands::registerString);
        CommandRegistrationCallback.EVENT.register(DebugCommands::registerWood);
        CommandRegistrationCallback.EVENT.register(DebugCommands::registerStone);
        CommandRegistrationCallback.EVENT.register(DebugCommands::registerLeather);
        CommandRegistrationCallback.EVENT.register(DebugCommands::registerIron);
        CommandRegistrationCallback.EVENT.register(DebugCommands::registerGold);
        CommandRegistrationCallback.EVENT.register(DebugCommands::registerMisc);
        CommandRegistrationCallback.EVENT.register(DebugCommands::registerDiamond);
        CommandRegistrationCallback.EVENT.register(DebugCommands::registerNetherite);
    }

    private static void registerString(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("balanced_mending")
                .executes(DebugCommands::executeHelp)
                .then(CommandManager.literal("string")
                        .requires(source -> source.hasPermissionLevel(1))
                        .executes(DebugCommands::executeString)));
    }

    private static int executeString(CommandContext<ServerCommandSource> context) {
        var player = context.getSource().getPlayer();
        if (player == null) return 0;

        player.getInventory().clear();
        giveFullStackOff(player, Items.EXPERIENCE_BOTTLE);
        giveFullStackOff(player, Items.STRING);
        giveMendable(player, Items.FISHING_ROD);
        giveMendable(player, Items.BOW);
        giveMendable(player, Items.CROSSBOW);
        giveMendable(player, Items.CARROT_ON_A_STICK);
        giveMendable(player, Items.WARPED_FUNGUS_ON_A_STICK);
        return 1;
    }

    private static void registerWood(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("balanced_mending")
                .executes(DebugCommands::executeHelp)
                .then(CommandManager.literal("wood")
                        .requires(source -> source.hasPermissionLevel(1))
                        .executes(DebugCommands::executeWood)));
    }

    private static int executeWood(CommandContext<ServerCommandSource> context) {
        var player = context.getSource().getPlayer();
        if (player == null) return 0;

        player.getInventory().clear();
        giveFullStackOff(player, Items.EXPERIENCE_BOTTLE);
        giveFullStackOff(player, Items.PALE_OAK_PLANKS);
        giveFullStackOff(player, Items.ACACIA_PLANKS);
        giveFullStackOff(player, Items.BAMBOO_PLANKS);
        giveFullStackOff(player, Items.BIRCH_PLANKS);
        giveFullStackOff(player, Items.CHERRY_PLANKS);
        giveFullStackOff(player, Items.CRIMSON_PLANKS);
        giveFullStackOff(player, Items.DARK_OAK_PLANKS);
        giveFullStackOff(player, Items.JUNGLE_PLANKS);
        giveFullStackOff(player, Items.MANGROVE_PLANKS);
        giveFullStackOff(player, Items.OAK_PLANKS);
        giveFullStackOff(player, Items.SPRUCE_PLANKS);
        giveFullStackOff(player, Items.WARPED_PLANKS);
        giveMendable(player, Items.WOODEN_SWORD);
        giveMendable(player, Items.WOODEN_PICKAXE);
        giveMendable(player, Items.WOODEN_AXE);
        giveMendable(player, Items.WOODEN_SHOVEL);
        giveMendable(player, Items.WOODEN_HOE);
        giveMendable(player, Items.SHIELD);
        return 1;
    }

    private static void registerStone(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("balanced_mending")
                .executes(DebugCommands::executeHelp)
                .then(CommandManager.literal("stone")
                        .requires(source -> source.hasPermissionLevel(1))
                        .executes(DebugCommands::executeStone)));
    }

    private static int executeStone(CommandContext<ServerCommandSource> context) {
        var player = context.getSource().getPlayer();
        if (player == null) return 0;

        player.getInventory().clear();
        giveFullStackOff(player, Items.EXPERIENCE_BOTTLE);
        giveFullStackOff(player, Items.BLACKSTONE);
        giveFullStackOff(player, Items.COBBLED_DEEPSLATE);
        giveFullStackOff(player, Items.COBBLESTONE);
        giveMendable(player, Items.STONE_SWORD);
        giveMendable(player, Items.STONE_PICKAXE);
        giveMendable(player, Items.STONE_AXE);
        giveMendable(player, Items.STONE_SHOVEL);
        giveMendable(player, Items.STONE_HOE);
        return 1;
    }

    private static void registerLeather(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("balanced_mending")
                .executes(DebugCommands::executeHelp)
                .then(CommandManager.literal("leather")
                        .requires(source -> source.hasPermissionLevel(1))
                        .executes(DebugCommands::executeLeather)));
    }

    private static int executeLeather(CommandContext<ServerCommandSource> context) {
        var player = context.getSource().getPlayer();
        if (player == null) return 0;

        player.getInventory().clear();
        giveFullStackOff(player, Items.EXPERIENCE_BOTTLE);
        giveFullStackOff(player, Items.LEATHER);
        giveMendable(player, Items.LEATHER_HELMET);
        giveMendable(player, Items.LEATHER_CHESTPLATE);
        giveMendable(player, Items.LEATHER_LEGGINGS);
        giveMendable(player, Items.LEATHER_BOOTS);
        return 1;
    }

    private static void registerIron(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("balanced_mending")
                .executes(DebugCommands::executeHelp)
                .then(CommandManager.literal("iron")
                        .requires(source -> source.hasPermissionLevel(1))
                        .executes(DebugCommands::executeIron)));
    }

    private static int executeIron(CommandContext<ServerCommandSource> context) {
        var player = context.getSource().getPlayer();
        if (player == null) return 0;

        player.getInventory().clear();
        giveFullStackOff(player, Items.EXPERIENCE_BOTTLE);
        giveFullStackOff(player, Items.IRON_INGOT);
        giveMendable(player, Items.IRON_HELMET);
        giveMendable(player, Items.IRON_CHESTPLATE);
        giveMendable(player, Items.IRON_LEGGINGS);
        giveMendable(player, Items.IRON_BOOTS);
        giveMendable(player, Items.IRON_SWORD);
        giveMendable(player, Items.IRON_PICKAXE);
        giveMendable(player, Items.IRON_AXE);
        giveMendable(player, Items.IRON_SHOVEL);
        giveMendable(player, Items.IRON_HOE);
        giveMendable(player, Items.CHAINMAIL_HELMET);
        giveMendable(player, Items.CHAINMAIL_CHESTPLATE);
        giveMendable(player, Items.CHAINMAIL_LEGGINGS);
        giveMendable(player, Items.CHAINMAIL_BOOTS);
        giveMendable(player, Items.SHEARS);
        return 1;
    }

    private static void registerGold(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("balanced_mending")
                .executes(DebugCommands::executeHelp)
                .then(CommandManager.literal("gold")
                        .requires(source -> source.hasPermissionLevel(1))
                        .executes(DebugCommands::executeGold)));
    }

    private static int executeGold(CommandContext<ServerCommandSource> context) {
        var player = context.getSource().getPlayer();
        if (player == null) return 0;

        player.getInventory().clear();
        giveFullStackOff(player, Items.EXPERIENCE_BOTTLE);
        giveFullStackOff(player, Items.GOLD_INGOT);
        giveMendable(player, Items.GOLDEN_HELMET);
        giveMendable(player, Items.GOLDEN_CHESTPLATE);
        giveMendable(player, Items.GOLDEN_LEGGINGS);
        giveMendable(player, Items.GOLDEN_BOOTS);
        giveMendable(player, Items.GOLDEN_SWORD);
        giveMendable(player, Items.GOLDEN_PICKAXE);
        giveMendable(player, Items.GOLDEN_AXE);
        giveMendable(player, Items.GOLDEN_SHOVEL);
        giveMendable(player, Items.GOLDEN_HOE);
        return 1;
    }

    private static void registerMisc(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("balanced_mending")
                .executes(DebugCommands::executeHelp)
                .then(CommandManager.literal("misc")
                        .requires(source -> source.hasPermissionLevel(1))
                        .executes(DebugCommands::executeMisc)));
    }

    private static int executeMisc(CommandContext<ServerCommandSource> context) {
        var player = context.getSource().getPlayer();
        if (player == null) return 0;

        player.getInventory().clear();
        giveFullStackOff(player, Items.EXPERIENCE_BOTTLE);
        giveFullStackOff(player, Items.TURTLE_SCUTE);
        giveMendable(player, Items.TURTLE_HELMET);
        giveFullStackOff(player, Items.ARMADILLO_SCUTE);
        giveMendable(player, Items.WOLF_ARMOR);
        giveFullStackOff(player, Items.BREEZE_ROD);
        giveMendable(player, Items.MACE);
        giveFullStackOff(player, Items.PHANTOM_MEMBRANE);
        giveMendable(player, Items.ELYTRA);
        giveFullStackOff(player, Items.PRISMARINE_SHARD);
        giveMendable(player, Items.TRIDENT);
        giveFullStackOff(player, Items.COPPER_INGOT);
        giveMendable(player, Items.BRUSH);
        giveFullStackOff(player, Items.FLINT);
        giveMendable(player, Items.FLINT_AND_STEEL);
        return 1;
    }

    private static void registerDiamond(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("balanced_mending")
                .executes(DebugCommands::executeHelp)
                .then(CommandManager.literal("diamond")
                        .requires(source -> source.hasPermissionLevel(1))
                        .executes(DebugCommands::executeDiamond)));
    }

    private static int executeDiamond(CommandContext<ServerCommandSource> context) {
        var player = context.getSource().getPlayer();
        if (player == null) return 0;

        player.getInventory().clear();
        giveFullStackOff(player, Items.EXPERIENCE_BOTTLE);
        giveFullStackOff(player, Items.DIAMOND);
        giveMendable(player, Items.DIAMOND_HELMET);
        giveMendable(player, Items.DIAMOND_CHESTPLATE);
        giveMendable(player, Items.DIAMOND_LEGGINGS);
        giveMendable(player, Items.DIAMOND_BOOTS);
        giveMendable(player, Items.DIAMOND_SWORD);
        giveMendable(player, Items.DIAMOND_PICKAXE);
        giveMendable(player, Items.DIAMOND_AXE);
        giveMendable(player, Items.DIAMOND_SHOVEL);
        giveMendable(player, Items.DIAMOND_HOE);
        return 1;
    }

    private static void registerNetherite(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("balanced_mending")
                .executes(DebugCommands::executeHelp)
                .then(CommandManager.literal("netherite")
                .requires(source -> source.hasPermissionLevel(1))
                .executes(DebugCommands::executeNetherite)));
    }

    private static int executeNetherite(CommandContext<ServerCommandSource> context) {
        var player = context.getSource().getPlayer();
        if (player == null) return 0;

        player.getInventory().clear();
        giveFullStackOff(player, Items.EXPERIENCE_BOTTLE);
        giveFullStackOff(player, Items.NETHERITE_INGOT);
        giveMendable(player, Items.NETHERITE_HELMET);
        giveMendable(player, Items.NETHERITE_CHESTPLATE);
        giveMendable(player, Items.NETHERITE_LEGGINGS);
        giveMendable(player, Items.NETHERITE_BOOTS);
        giveMendable(player, Items.NETHERITE_SWORD);
        giveMendable(player, Items.NETHERITE_PICKAXE);
        giveMendable(player, Items.NETHERITE_AXE);
        giveMendable(player, Items.NETHERITE_SHOVEL);
        giveMendable(player, Items.NETHERITE_HOE);
        return 1;
    }

    private static void giveMendable(PlayerEntity player, Item item){
        var stack = item.getDefaultStack();
        stack.setDamage(1000);
        var enchantment = player.getWorld()
                .getRegistryManager()
                .getOrThrow(RegistryKeys.ENCHANTMENT)
                .get(Enchantments.MENDING);
        var entry = player.getWorld()
                .getRegistryManager()
                .getOrThrow(RegistryKeys.ENCHANTMENT)
                .getEntry(enchantment);
        stack.addEnchantment(entry, 1);
        player.giveItemStack(stack);
    }

    private static void giveFullStackOff(PlayerEntity player, Item item){
        var itemStack = item.getDefaultStack();
        itemStack.setCount(64);
        player.giveItemStack(itemStack);
    }

    private static int executeHelp(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() -> Text.literal("WHAT DO YOU WANT???"), false);
        return 1;
    }
}
