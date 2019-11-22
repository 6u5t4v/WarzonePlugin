package com.furnesse.warzone;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ExchangeRecipes {

	WarzonePlugin plugin;

	public ExchangeRecipes(WarzonePlugin plugin) {
		this.plugin = plugin;
	}

	List<ExchangeRecipe> exchangeRecipes = new ArrayList<ExchangeRecipe>();

	public void loadExchangeItems() {
		ConfigurationSection availableExchangeItems = plugin.getConfig().getConfigurationSection("exchange-recipes");

		exchangeRecipes.clear();
		int amount = 0;
		if (availableExchangeItems != null) {
			for (String exchangeItem : availableExchangeItems.getKeys(false)) {
				if (exchangeItem != null) {
					try {
						String name = exchangeItem.toString();

						String from = plugin.getConfig()
								.getString("exchange-recipes." + exchangeItem + ".exchange-from");
						String result = plugin.getConfig()
								.getString("exchange-recipes." + exchangeItem + ".result.item");

						ItemStack exchangeFrom = plugin.getCustomItems().getCustomItem(from).getItemStack();

						int fromAmount = plugin.getConfig().getInt("exchange-recipes." + exchangeItem + ".from-amount");

						if (result != null) {
							ItemStack resultItem = plugin.getCustomItems().getCustomItem(result).getItemStack();
							int resultAmount = plugin.getConfig()
									.getInt("exchange-recipes." + exchangeItem + ".result.amount");
							exchangeRecipes.add(
									new ExchangeRecipe(name, exchangeFrom, resultItem, fromAmount, resultAmount, null));
							amount++;
							continue;
						}

						List<String> intoCmd = plugin.getConfig()
								.getStringList("exchange-recipes." + exchangeItem + ".commands");
						exchangeRecipes.add(new ExchangeRecipe(name, exchangeFrom, null, fromAmount, -1, intoCmd));

						amount++;
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
			plugin.getLogger().info("Loaded " + amount + " exchange recipes");
		}
	}

	public List<ExchangeRecipe> getExchangeRecipes() {
		return exchangeRecipes;
	}

	public ExchangeRecipe getItemRecipes(String recipeName) {
		for (ExchangeRecipe recipe : exchangeRecipes) {
			if (recipe.getName().equals(recipeName)) {
				return recipe;
			}
		}

		return null;
	}
}
