package com.furnesse.warzone;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

public class ExchangeRecipes {

	WarzonePlugin plugin;

	public ExchangeRecipes(WarzonePlugin plugin) {
		this.plugin = plugin;
	}

	List<ExchangeRecipe> exchangeRecipes = new ArrayList<ExchangeRecipe>();

	public void loadExchangeItems() {
		ConfigurationSection availableExchangeItems = plugin.getConfig().getConfigurationSection("exchange-recipes");

		exchangeRecipes.clear();
		if (availableExchangeItems != null) {
			for (String exchangeItem : availableExchangeItems.getKeys(false)) {
				if (exchangeItem != null) {
					try {
						String from = plugin.getConfig()
								.getString("exchange-recipes." + exchangeItem + ".exchange-from");
						String into = plugin.getConfig()
								.getString("exchange-recipes." + exchangeItem + ".exchange-into");
						CustomItem exchangeFrom = plugin.getCustomItems().getCustomItem(from);
						CustomItem exchangeTo = plugin.getCustomItems().getCustomItem(into);
						int fromAmount = plugin.getConfig().getInt("exchange-recipes." + exchangeItem + ".from-amount");
						int intoAmount = plugin.getConfig().getInt("exchange-recipes." + exchangeItem + ".into-amount");

						ExchangeRecipe recipe = new ExchangeRecipe(exchangeFrom, exchangeTo, fromAmount, intoAmount);
						exchangeRecipes.add(recipe);

						if (recipe != null || exchangeTo != null) {
							if (exchangeTo.getName() == recipe.getExchangeInto().getName()) {
								exchangeTo.setRecipe(recipe);
							}
						}
						System.out.println("Loaded an exchange recipe: " + exchangeItem);

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		}
	}

	public List<ExchangeRecipe> getExchangeRecipes() {
		return exchangeRecipes;
	}

	public ExchangeRecipe getItemRecipes(CustomItem recipeItem) {
		for (ExchangeRecipe recipes : exchangeRecipes) {
			if (recipes.getExchangeInto().equals(recipeItem)) {
				return recipes;
			}
		}

		return null;
	}
}
