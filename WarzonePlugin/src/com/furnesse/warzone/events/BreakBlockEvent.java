package com.furnesse.warzone.events;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.furnesse.warzone.Lang;
import com.furnesse.warzone.WarzonePlugin;

public class BreakBlockEvent implements Listener {

	WarzonePlugin plugin = WarzonePlugin.instance;

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Block block = e.getBlock();

		if (plugin.enabledWorlds.contains(player.getWorld())) {
			if (plugin.availableOres.contains(block.getType().toString())) {
				if (player.getGameMode().equals(GameMode.CREATIVE)) {
					if (player.isSneaking()) {
						return;
					}
				}
				e.setCancelled(true);
//				e.setExpToDrop(0);
				e.setDropItems(false);

				if (plugin.matTimeout.get(block.getType()) == null)
					return;
				int seconds = plugin.matTimeout.get(block.getType());

				plugin.utils.getPlaceholders().put(block.getLocation(), block.getType()); // ADDING TO PLACEHOLDERS

				giveDrops(player, block);

				Material original = block.getType();
				try {

					block.getLocation().getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);

					block.setType(plugin.placeholder);

				} catch (Exception e2) {
					e2.printStackTrace();
				}

				new BukkitRunnable() {
					@Override
					public void run() {
						plugin.utils.getPlaceholders().remove(block.getLocation());
						block.setType(original);
					}
				}.runTaskLater(plugin, 20L * seconds);
			}
		}

	}

	public Boolean isAvailableOre(Block block) {
		if (plugin.availableOres != null) {
			for (String materials : plugin.availableOres) {
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

		if (plugin.utils.hasAvailableSlot(player, cDrop)) {
			player.getInventory().addItem(cDrop);
		} else {
			player.getWorld().dropItem(player.getLocation(), cDrop);
			player.sendMessage(Lang.DROPPED_ITEM);
		}

		if (luckyDrop != null) {
			if (plugin.utils.hasAvailableSlot(player, luckyDrop)) {
				player.getInventory().addItem(luckyDrop);
			} else {
				player.getWorld().dropItem(player.getLocation(), luckyDrop);
				player.sendMessage(Lang.DROPPED_ITEM);
			}
		}
	}
}
