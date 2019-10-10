package com.furnesse.warzone.events;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.furnesse.warzone.Lang;
import com.furnesse.warzone.WarzonePlugin;
import com.furnesse.warzone.utils.Utils;

public class BreakBlockEvent implements Listener {

	WarzonePlugin plugin = WarzonePlugin.instance;
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Block block = e.getBlock();

		if (WarzonePlugin.enabledWorlds.contains(player.getWorld())) {
			if (isAvailableOre(block)) {
				e.setCancelled(true);
				e.setExpToDrop(0);
				e.setDropItems(false);
				
				int seconds = WarzonePlugin.matTimeout.get(block.getType());
				
				Utils.getPlaceholders().put(block.getLocation(), block.getType()); // ADDING TO PLACEHOLDERS
				
				giveDrops(player, block);

				Material original = block.getType();
				try {

					block.getLocation().getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
					
					Material placeholder = Material.getMaterial(plugin.getConfig().getString("placeholder-block"));

					if (placeholder != null) {
						block.setType(placeholder);
					}

				} catch (Exception e2) {
					e2.printStackTrace();
				}

				new BukkitRunnable() {
					@Override
					public void run() {
						Utils.getPlaceholders().remove(block.getLocation());
						block.setType(original);
					}
				}.runTaskLater(plugin, 20L * seconds);
				
//				Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
//					
//				}, seconds * 20L);
				
			}
		}

	}

	public Boolean isAvailableOre(Block block) {
		ConfigurationSection availableOres = plugin.getConfig().getConfigurationSection("warzone-ores");

		if (availableOres != null) {
			for (String materials : availableOres.getKeys(false)) {
				try {
					Material material = Material.getMaterial(materials);

					if (material != null) {
						if (material.equals(block.getType())) {
							return true;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public ItemStack luckyDrop(Block block) {
		if (isAvailableOre(block)) {
			Random rand = new Random();
			int choice = rand.nextInt(100) + 1;

			double chance = plugin.getConfig().getDouble("warzone-ores." + block.getType() + ".gem-chance");

			if (choice <= chance) {
				return plugin.getCustomItems().getLuckyDrops()
						.get(rand.nextInt(plugin.getCustomItems().getLuckyDrops().size())).getItemStack();
			}
		}
		return null;
	}

	public void giveDrops(Player player, Block block) {
		ItemStack cDrop = plugin.getCustomItems().getItemFromBlock(block).getItemStack();
		ItemStack luckyDrop = luckyDrop(block);

		if (Utils.hasAvailableSlot(player, cDrop)) {
			player.getInventory().addItem(cDrop);
		} else {
			player.getWorld().dropItem(player.getLocation(), cDrop);
			player.sendMessage(Lang.DROPPED_ITEM);
		}

		if (luckyDrop != null) {
			if (Utils.hasAvailableSlot(player, luckyDrop)) {
				player.getInventory().addItem(luckyDrop);
			} else {
				player.getWorld().dropItem(player.getLocation(), luckyDrop);
				player.sendMessage(Lang.DROPPED_ITEM);
			}
		}
	}
}
