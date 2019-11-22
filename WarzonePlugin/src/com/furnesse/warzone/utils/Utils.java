package com.furnesse.warzone.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.furnesse.warzone.CustomItem;
import com.furnesse.warzone.Lang;
import com.furnesse.warzone.WarzonePlugin;

public class Utils {

	WarzonePlugin plugin;

	public Utils(WarzonePlugin plugin) {
		this.plugin = plugin;
	}
	
	Map<Location, Material> placeholders = new HashMap<>();

	public Map<Location, Material> getPlaceholders() {
		return placeholders;
	}

	public boolean hasFreeSlot(Player player) {
		return (player.getInventory().firstEmpty() != -1);
	}

	// BUG > if player has full inventory (every slot full) while containing 64 item, it will return true
	
	public boolean hasAvailableSlot(Player player, ItemStack item) {
		Inventory inv = player.getInventory();
		
		if (hasFreeSlot(player)) {
			return true;
		} else if (inv.containsAtLeast(item, 1)) {
			return true;
		}
		return false;

	}

	public void addItemToInv(Player player, ItemStack item, int amount){
		Inventory inv = player.getInventory();
		
		for (int i = 0; i < amount; i++) {
			if (hasAvailableSlot(player, item)) {
				inv.addItem(item);
			} else {
				player.getWorld().dropItem(player.getLocation(), item);
				player.sendMessage(Lang.DROPPED_ITEM);
			}
		}
	}
	
	public void takeItemFromInv(Player player, ItemStack item, int amount) {
		Inventory inv = player.getInventory();
		CustomItem cItem = plugin.getCustomItems().getItemFromDisplayname(item.getItemMeta().getDisplayName());
		
		if (inv.containsAtLeast(item, amount)) {
			for (int i = 0; i < amount; i++) {
				inv.removeItem(item);
			}
		}else {
			player.sendMessage(Lang.NOT_ENOUGH_ITEMS.replaceAll("%from_item%", cItem.getFormat())
					.replaceAll("%into_item%", cItem.getFormat()));
			return;
		}
	}
	
	public void particleEffect(Player player, Block block) {
		Location loc = block.getLocation();
		
//		ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		
		int radius = 1;

		for (int i = 0; i < 360; i += radius) {
			Location flameloc = loc;
			flameloc.setZ(flameloc.getZ() + Math.cos(i) * radius);
			flameloc.setX(flameloc.getX() + Math.sin(i) * radius);
			loc.getWorld().playEffect(flameloc, Effect.MOBSPAWNER_FLAMES, 51);
		}
	}
}
