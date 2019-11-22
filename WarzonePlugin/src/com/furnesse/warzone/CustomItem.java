package com.furnesse.warzone;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItem {

	private String name;
	private String format;
	private List<String> lore;
	private Material material;
	private boolean glowing;
	private boolean luckyDrop;
	private ExchangeRecipe recipe;
	private int price;

	public CustomItem(String name, String format, List<String> lore, Material material, boolean glowing,
			boolean luckyDrop, ExchangeRecipe recipe, int price) {
		this.name = name;
		this.format = format;
		this.lore = lore;
		this.material = material;
		this.glowing = glowing;
		this.luckyDrop = luckyDrop;
		this.recipe = recipe;
		this.price = price;
	}

	public ItemStack getItemStack() {
		ItemStack customItem = new ItemStack(getMaterial(), 1);
		ItemMeta meta = customItem.getItemMeta();
		if (isGlowing()) {
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		meta.setDisplayName(getFormat());
		meta.setLore(getLore());
		customItem.setItemMeta(meta);
		return customItem;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public ExchangeRecipe getRecipe() {
		return recipe;
	}

	public void setRecipe(ExchangeRecipe recipe) {
		this.recipe = recipe;
	}

	public boolean isLuckyDrop() {
		return luckyDrop;
	}

	public void setLuckyDrop(boolean luckyDrop) {
		this.luckyDrop = luckyDrop;
	}

	public boolean isGlowing() {
		return glowing;
	}

	public void setGlowing(boolean glowing) {
		this.glowing = glowing;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public List<String> getLore() {
		return lore;
	}

	public void setLore(List<String> lore) {
		this.lore = lore;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

}
