package com.furnesse.warzone;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.furnesse.warzone.utils.Utils;

public class Listeners {

	static WarzonePlugin plugin = WarzonePlugin.instance;

	public static void exchangeGem(CommandSender sender, Player target, CustomItem itemToGet) {
		Inventory inv = target.getInventory();
		ExchangeRecipe recipe = itemToGet.getRecipe();

		if (recipe == null) {
			sender.sendMessage(Lang.INVALID_EXCHANGE_ITEM);
			return;
		}

		ItemStack from = recipe.getExchangeFrom().getItemStack();
		ItemStack into = recipe.getExchangeInto().getItemStack();

		if (inv.containsAtLeast(from, recipe.getFromAmount())) {
			Utils.takeItemFromInv(target, from, recipe.getFromAmount());
			Utils.addItemToInv(target, into, recipe.getIntoAmount());
			target.sendMessage(
					Lang.SUCCESSFULL_EXCHANGE.replaceAll("%from_amount%", String.valueOf(recipe.getFromAmount()))
							.replaceAll("%from_item%", recipe.getExchangeFrom().getFormat())
							.replaceAll("%into_amount%", String.valueOf(recipe.getIntoAmount()))
							.replaceAll("%into_item%", recipe.getExchangeInto().getFormat()));
		} else {
			target.sendMessage(Lang.NOT_ENOUGH_ITEMS.replaceAll("%from_item%", recipe.getExchangeFrom().getFormat())
					.replaceAll("%into_item%", recipe.getExchangeInto().getFormat()));
			return;
		}
	}

	public static void tradeIn(CommandSender sender, Player target, CustomItem cItem, int amount) {
		ItemStack item = cItem.getItemStack();
		Inventory inv = target.getInventory();
		
		if (inv.containsAtLeast(item, amount)) {
			Utils.takeItemFromInv(target, item, amount);

			WarzonePlugin.getEconomy().depositPlayer(target, (cItem.getPrice() * amount));

			target.sendMessage(Lang.SUCCESSFULL_TRADE.replaceAll("%amount%", String.valueOf(amount))
					.replaceAll("%item_format%", cItem.getFormat())
					.replaceAll("%price%", String.valueOf(cItem.getPrice())));
		}else {
			target.sendMessage(Lang.NOT_ENOUGH_ITEMS.replaceAll("%from_item%", cItem.getFormat())
					.replaceAll("%into_item%", cItem.getFormat()));
			return;
		}
	}
}
