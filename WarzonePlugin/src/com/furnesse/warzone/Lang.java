package com.furnesse.warzone;

import org.bukkit.ChatColor;

public class Lang {

	static WarzonePlugin plugin = WarzonePlugin.instance;

	public static String chat(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	public static String PREFIX = chat("" + plugin.getConfigs().getLangConfig().getString("prefix"));
	public static String NO_PERMISSION = PREFIX
			+ chat("" + plugin.getConfigs().getLangConfig().getString("no-permission"));
	public static String AVAILABLE_ITEMS = chat("" + plugin.getConfigs().getLangConfig().getString("available-items"));
	public static String INVALID_ITEM = PREFIX
			+ chat("" + plugin.getConfigs().getLangConfig().getString("invalid-item"));
	public static String SUCCESSFULL_TRADE = PREFIX
			+ chat("" + plugin.getConfigs().getLangConfig().getString("successfull-trade"));
	public static String DROPPED_ITEM = PREFIX
			+ chat("" + plugin.getConfigs().getLangConfig().getString("dropped-item-on-ground"));
	public static String SUCCESSFULL_EXCHANGE = PREFIX
			+ chat("" + plugin.getConfigs().getLangConfig().getString("successfull-exchange"));
	public static String INVALID_AMOUNT = PREFIX
			+ chat("" + plugin.getConfigs().getLangConfig().getString("invalid-amount"));
	public static String NOT_ENOUGH_ITEMS = PREFIX
			+ chat("" + plugin.getConfigs().getLangConfig().getString("not-enough-items"));
	public static String INVALID_EXCHANGE_ITEM = PREFIX
			+ chat("" + plugin.getConfigs().getLangConfig().getString("invalid-exchange-item"));
	public static String RELOADED = PREFIX + chat("" + plugin.getConfigs().getLangConfig().getString("reloaded"));
}
