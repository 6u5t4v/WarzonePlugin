package com.furnesse.warzone.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import com.furnesse.warzone.CustomItem;
import com.furnesse.warzone.WarzonePlugin;

public class CustomItemCraftEvent implements Listener {

	WarzonePlugin plugin = WarzonePlugin.instance;

	@EventHandler
	public void onCrafting(PrepareItemCraftEvent e) {

		ItemStack[] invSlots = e.getInventory().getMatrix();
		for (int i = 0; i < invSlots.length; i++) {
			if (invSlots[i] == null)
				return;

			if (invSlots[i].hasItemMeta()) {
				String itemName = invSlots[i].getItemMeta().getDisplayName();
				CustomItem cItem = plugin.getCustomItems().getItemFromDisplayname(itemName);

				if (cItem == null)
					return;

				if (cItem.getItemStack() == null)
					return;

				if (cItem.getItemStack().getItemMeta() == null)
					return;

				if (invSlots[i].getItemMeta().equals(cItem.getItemStack().getItemMeta())) {
					e.getInventory().setResult(null);
				}
			}

		}
	}
}
