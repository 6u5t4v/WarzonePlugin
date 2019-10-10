package com.furnesse.warzone;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

public class CustomItems {

	WarzonePlugin plugin;

	public CustomItems(WarzonePlugin plugin) {
		this.plugin = plugin;
	}

	List<CustomItem> customItems = new ArrayList<CustomItem>();
	List<CustomItem> luckyDrops = new ArrayList<CustomItem>();

	public void loadCustomItems() {
		ConfigurationSection cItemsList = plugin.getConfigs().getCItemsConfig().getConfigurationSection("custom-items");

		customItems.clear();
		luckyDrops.clear();
		if (cItemsList != null) {
			for (String cItems : cItemsList.getKeys(false)) {
				try {
					String itemName = plugin.getConfigs().getCItemsConfig()
							.getString("custom-items." + cItems + ".name");
					Material itemMaterial = Material.getMaterial(
							plugin.getConfigs().getCItemsConfig().getString("custom-items." + cItems + ".material"));
					String itemFormat = plugin.getConfigs().getCItemsConfig()
							.getString("custom-items." + cItems + ".format");
					List<String> itemLore = plugin.getConfigs().getCItemsConfig()
							.getStringList("custom-items." + cItems + ".lore");
					boolean isGlowing = plugin.getConfigs().getCItemsConfig()
							.getBoolean("custom-items." + cItems + ".glowing");
					boolean isGem = plugin.getConfigs().getCItemsConfig()
							.getBoolean("custom-items." + cItems + ".gem-settings.gem-item");
					boolean isLuckyDrop = plugin.getConfigs().getCItemsConfig()
							.getBoolean("custom-items." + cItems + ".gem-settings.lucky-drop");

					int price = plugin.getConfigs().getCItemsConfig()
							.getInt("custom-items." + cItems + ".price");
					
					ExchangeRecipe recipe = null;
					
					CustomItem customItem = new CustomItem(itemName, itemFormat, itemLore, itemMaterial, isGlowing,
							isGem, isLuckyDrop, recipe, price);
					customItems.add(customItem);

					if (isLuckyDrop) {
						luckyDrops.add(customItem);
					}

					System.out.println("Successfully loaded: " + customItem.getName());

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}

	public List<CustomItem> getCustomItems() {
		return customItems;
	}

	public List<CustomItem> getLuckyDrops() {
		return luckyDrops;
	}

	public CustomItem getCustomItem(String name) {
		for (CustomItem cItem : customItems) {
			if (cItem.getName().equals(name))
				return cItem;
		}
		return null;
	}

	public CustomItem getItemFromDisplayname(String name) {
		for(CustomItem cItem : customItems) {
			if(cItem.getItemStack().getItemMeta().getDisplayName().equals(name)) {
				return cItem;
			}
		}
		return null;
	}
	
	public CustomItem getItemFromBlock(Block block) {
		for (String warzoneOre : plugin.getConfig().getConfigurationSection("warzone-ores").getKeys(false)) {
			Material oreMaterial = Material.getMaterial(warzoneOre);
			if (oreMaterial != null) {
				if (oreMaterial == block.getType()) {
					String itemName = plugin.getConfig().getString("warzone-ores." + warzoneOre + ".custom-drop");
					if (itemName != null) {
						return getCustomItem(itemName);
					}
				}
			}
		}
		return null;
	}
}
