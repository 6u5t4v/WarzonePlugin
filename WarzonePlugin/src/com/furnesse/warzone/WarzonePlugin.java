package com.furnesse.warzone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.furnesse.warzone.commands.WarzoneCMD;
import com.furnesse.warzone.events.BreakBlockEvent;
import com.furnesse.warzone.events.CustomItemCraftEvent;
import com.furnesse.warzone.utils.Utils;

import net.milkbowl.vault.economy.Economy;

public class WarzonePlugin extends JavaPlugin {

	public static WarzonePlugin instance;

	private static final Logger log = Logger.getLogger("Minecraft");
	private static Economy econ = null;

	private Configs configs = new Configs(this);
	private CustomItems cItems = new CustomItems(this);
	private ExchangeRecipes exchange = new ExchangeRecipes(this);
	public Utils utils = new Utils(this);

	public List<World> enabledWorlds = new ArrayList<World>();
	public List<String> availableOres = new ArrayList<String>();
	public Map<Material, Integer> matTimeout = new HashMap<Material, Integer>();
	public Material placeholder;
	
	public void onEnable() {
		instance = this;
		configs.createCustomConfig();
		configs.saveConfigs();
		if (!setupEconomy()) {
			log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		cItems.loadCustomItems();
		exchange.loadExchangeItems();
		serverOptions();
		registerCommands();
		registerListeners();

		this.getLogger().info("Has been enabled v" + this.getDescription().getVersion());
	}

	private void registerCommands() {
		getCommand("warzone").setExecutor(new WarzoneCMD());
	}

	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new BreakBlockEvent(), this);
		pm.registerEvents(new CustomItemCraftEvent(), this);
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public void onDisable() {
//		configs.saveConfigs();
		try {
			if (utils.getPlaceholders() != null) {
				if (!utils.getPlaceholders().isEmpty()) {
					utils.getPlaceholders().forEach((location, material) -> location.getBlock().setType(material));
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		this.getLogger().info("Has been disabled v" + this.getDescription().getVersion());
	}

	public void serverOptions() {
		ConfigurationSection ores = getConfig().getConfigurationSection("warzone-ores");
		matTimeout.clear();
		if(ores != null) {
			int amount = 0;
			for(String warzoneBlock : ores.getKeys(false)) {
				try {
					Material mat = Material.getMaterial(warzoneBlock);
					int timeout = getConfig().getInt("warzone-ores." + warzoneBlock + ".timeout");
					if(mat != null || !(timeout <= 0)) {
						matTimeout.put(mat, timeout);
					}
					availableOres.add(warzoneBlock);
					amount++;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
			this.getLogger().info("Loaded " + amount + " of warzone ores");
		}
		
		String placeholderBlock = getConfig().getString("placeholder-block");
		if (Material.getMaterial(placeholderBlock) != null) {
			placeholder = Material.getMaterial(placeholderBlock);
			this.getLogger().info("Loaded placeholder block: " + placeholderBlock);
		}else {
			placeholder = Material.BEDROCK;
			this.getLogger().severe("Couldn't load placeholder block " + placeholderBlock + " using default.");
		}
		
		
		new BukkitRunnable() {
			@Override
			public void run() {

				if (getConfig().getStringList("enabled-worlds") != null) {
					for (String enabledWorld : getConfig().getStringList("enabled-worlds")) {
						World world = Bukkit.getWorld(enabledWorld);
						if (world != null) {
							enabledWorlds.add(world);
							getLogger().info("Enabled world: " + world.getName());
						} else {
							getLogger().severe("World: " + enabledWorld + " Is not available");
						}
					}
				}
			}
		}.runTaskLater(this, 1);
	}

	public CustomItems getCustomItems() {
		return cItems;
	}

	public ExchangeRecipes getExchangeItems() {
		return exchange;
	}

	public Configs getConfigs() {
		return configs;
	}

	public static Economy getEconomy() {
		return econ;
	}
}
