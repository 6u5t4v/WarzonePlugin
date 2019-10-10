package com.furnesse.warzone;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Configs {

	private FileConfiguration lang;
	private File langFile;
	private FileConfiguration cItems;
	private File cItemsFile;

	private WarzonePlugin plugin;

	public Configs(WarzonePlugin plugin) {
		this.plugin = plugin;
	}

	public void createCustomConfig() {
		File configFile = new File(plugin.getDataFolder(), "config.yml");
		langFile = new File(plugin.getDataFolder(), "lang.yml");
		cItemsFile = new File(plugin.getDataFolder(), "customitems.yml");

		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdirs();
		}

		if (!configFile.exists()) {
			plugin.saveDefaultConfig();
		}

		if (!langFile.exists()) {
			plugin.saveResource("lang.yml", false);
		}

		if (!cItemsFile.exists()) {
			plugin.saveResource("customitems.yml", false);
		}

		lang = new YamlConfiguration();
		cItems = new YamlConfiguration();
		try {
			lang.load(langFile);
			cItems.load(cItemsFile);
		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "Failed to load yml files!");
			plugin.getLogger().log(Level.SEVERE, "Error: " + e.getMessage());
		}
	}

	public void saveConfigs() {
		plugin.saveConfig();
		try {
			lang.save(langFile);
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not save lang.yml file!");
		}
		try {
			cItems.save(cItemsFile);
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not save customitems.yml file!");
		}
	}

	public void reloadConfigs(CommandSender sender) {
		try {
			lang.load(langFile);
			cItems.load(cItemsFile);
			sender.sendMessage(Lang.RELOADED);
		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "Failed to reload yml files!");
			plugin.getLogger().log(Level.SEVERE, "Error: " + e.getMessage());
		}
	}

	public FileConfiguration getLangConfig() {
		return lang;
	}

	public FileConfiguration getCItemsConfig() {
		return cItems;
	}
}
