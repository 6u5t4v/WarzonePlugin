package com.furnesse.warzone;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Listeners {

	static WarzonePlugin plugin = WarzonePlugin.instance;

	/*
	 * 1. Check if recipeResult is a valid recipe 2. Check if recipe returns cmd 3.
	 * Take the recipe.getFromAmount of type recipe.getFrom 4. Let console run all
	 * cmd from recipe.getCmds
	 */
	public static void exchange(CommandSender sender, Player target, String recipeResult) {
		ExchangeRecipe recipe = plugin.getExchangeItems().getItemRecipes(recipeResult);
		Inventory inv = target.getInventory();

		ItemStack item = recipe.getExchangeFrom();

		if (inv.containsAtLeast(item, recipe.getFromAmount())) {
			plugin.utils.takeItemFromInv(target, item, recipe.getFromAmount());
			if (recipe.getCmds() != null) {
				for (String cmd : recipe.getCmds()) {
					if (cmd != null) {
						Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replace("%player%", target.getName()));
					}
				}
			}

			if (recipe.getResult() != null) {
				plugin.utils.addItemToInv(target, recipe.getResult(), recipe.getResultAmount());
			}

			target.sendMessage(
					Lang.SUCCESSFULL_EXCHANGE
							.replace("%from_amount%", String.valueOf(recipe.getFromAmount()))
							.replace("%from_item%", recipe.getExchangeFrom().getItemMeta().getDisplayName().toLowerCase())
							.replace("%into_amount%", String.valueOf(recipe.getResultAmount()))
							.replace("%into_item%", recipe.getName()));
		}else {
			target.sendMessage(Lang.NOT_ENOUGH_ITEMS.replace("%from_item%", item.getItemMeta().getDisplayName().toLowerCase()));
		}
	}

	public static void tradeIn(CommandSender sender, Player target, CustomItem cItem, int amount) {
		ItemStack item = cItem.getItemStack();
		Inventory inv = target.getInventory();

		if (inv.containsAtLeast(item, amount)) {
			plugin.utils.takeItemFromInv(target, item, amount);

			WarzonePlugin.getEconomy().depositPlayer(target, (cItem.getPrice() * amount));

			target.sendMessage(Lang.SUCCESSFULL_TRADE.replaceAll("%amount%", String.valueOf(amount))
					.replaceAll("%item_format%", cItem.getFormat())
					.replaceAll("%price%", String.valueOf(cItem.getPrice())));
		} else {
			target.sendMessage(Lang.NOT_ENOUGH_ITEMS.replaceAll("%from_item%", cItem.getFormat())
					.replaceAll("%into_item%", cItem.getFormat()));
			return;
		}
	}
}
